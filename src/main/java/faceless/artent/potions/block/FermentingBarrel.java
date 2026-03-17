package faceless.artent.potions.block;

import com.mojang.serialization.MapCodec;
import faceless.artent.core.api.ChatUtils;
import faceless.artent.core.api.debug.DebugInfoConsumer;
import faceless.artent.core.api.debug.IDebuggableBlock;
import faceless.artent.core.item.INamed;
import faceless.artent.core.text.TextUtils;
import faceless.artent.potions.api.IPotionContainerItem;
import faceless.artent.potions.api.PotionContainerUtil;
import faceless.artent.potions.api.TimeUtils;
import faceless.artent.potions.blockEntities.FermentingBarrelBlockEntity;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.objects.ModBlockEntities;
import faceless.artent.potions.objects.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class FermentingBarrel extends BlockWithEntity implements INamed, IDebuggableBlock {
  public static final MapCodec<FermentingBarrel> CODEC = FermentingBarrel.createCodec(FermentingBarrel::new);
  public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
  public static final EnumProperty<BarrelType> BARREL_TYPE = EnumProperty.of(
      "barrel_type",
      BarrelType.class,
      BarrelType.Single,
      BarrelType.Group);

  @Override
  protected MapCodec<? extends BlockWithEntity> getCodec() {
    return CODEC;
  }

  public FermentingBarrel(Settings settings) {
    super(settings);
    this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    this.setDefaultState(getStateManager().getDefaultState().with(BARREL_TYPE, BarrelType.Single));
  }

  @Override
  public String getId() {
    return "fermenting_barrel";
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING);
    builder.add(BARREL_TYPE);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    // With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
    return BlockRenderType.MODEL;
  }

  @Override
  protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    if (!(world.getBlockEntity(pos) instanceof FermentingBarrelBlockEntity barrel)) return ActionResult.FAIL;

    var stack = player.getEquippedStack(player.getActiveHand() == Hand.MAIN_HAND
                                            ? EquipmentSlot.MAINHAND
                                            : EquipmentSlot.OFFHAND);
    var item = stack.getItem();

//    if(item == ArtentCore.DebugBook){
//      return stack.getItem().useOnBlock(new ItemUsageContext(world, player, player.getActiveHand(), stack, hit));
//    }

    if (item instanceof IPotionContainerItem) {
      var barrelInterface = PotionContainerUtil.createInterface(barrel);
      var updatedBottle = stack.copyWithCount(1);
      var potionInterface = PotionContainerUtil.createInterface(updatedBottle);
      var transferResult = PotionContainerUtil.transferBetweenContainers(player, potionInterface, barrelInterface);
      player.setStackInHand(player.getActiveHand(), ItemUsage.exchangeStack(stack, player, updatedBottle));

      return switch (transferResult) {
        case PotionContainerUtil.TransferResult.DifferentPotions -> {
          ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled.different");
          yield ActionResult.FAIL;
        }
        case PotionContainerUtil.TransferResult.BIsFull, PotionContainerUtil.TransferResult.BothIsFull -> {
          ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled.already");
          yield ActionResult.FAIL;
        }
        case PotionContainerUtil.TransferResult.AIsEmpty, PotionContainerUtil.TransferResult.BIsEmpty -> {
          ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.empty");
          yield ActionResult.FAIL;
        }
        case PotionContainerUtil.TransferResult.MovedToB, PotionContainerUtil.TransferResult.MovedToA ->
            ActionResult.SUCCESS;
        default -> ActionResult.PASS;
      };
    }

    if (item == ModItems.SHROOM_SPORES && !barrel.fermentationStarted && !barrel.potions.isEmpty()) {
      barrel.startFermentation();
      var effects = AlchemicalPotionUtil.getPotionEffects(barrel.getPotions());
      var potionNames = AlchemicalPotionUtil.getPotionNames(effects);
      ChatUtils.sendMessageToPlayer(
          world,
          player,
          Text
              .translatable("text.artent_potions.barrel.fermentation.started")
              .append(Text.translatable("text.artent_potions.barrel.fermented.of"))
              .append(potionNames));
      var newStack = stack.copy();
      newStack.setCount(stack.getCount() - 1);
      player.setStackInHand(player.getActiveHand(), newStack);
      return ItemUsage.consumeHeldItem(world, player, player.getActiveHand());
    }

    if (stack.isEmpty() && world.isClient()) {
      var effects = AlchemicalPotionUtil.getPotionEffects(barrel.getPotions());
      var potionNames = AlchemicalPotionUtil.getPotionNames(effects);

      var barrelContentText = Text
          .translatable("text.artent_potions.barrel.contains")
          .append(Text.literal(barrel.potionAmount + ""))
          .append(Text.literal(" "))
          .append(Text.translatable("text.artent_potions.barrel.portions_" + barrel.potionAmount))
          .append(Text.translatable("text.artent_potions.barrel.fermented.of"))
          .append(potionNames);

      if (barrel.isFermented()) {
        player.sendMessage(barrelContentText, false);
      } else {
        if (!barrel.potions.isEmpty()) {
          if (!barrel.fermentationStarted) {
            player.sendMessage(
                barrelContentText
                    .append(Text.literal(". "))
                    .append(Text.translatable("text.artent_potions.barrel.fermentation.not_started")), false);
            return ActionResult.SUCCESS;
          }

          var timeLeftSecondsRaw = (int) (barrel.fermentedTime / world.getTickManager().getTickRate());
          var timeLeftMinutes = timeLeftSecondsRaw / 60;
          var timeLeftSeconds = timeLeftSecondsRaw % 60;
          var timeText = Text.literal("");
          if (timeLeftMinutes > 0) {
            timeText
                .append(Text.literal(timeLeftMinutes + ""))
                .append(Text.translatable("text.artent_potions.fermentation.time.minute_suffix"))
                .append(Text.literal(" "));
          }
          timeText
              .append(Text.literal(timeLeftSeconds + ""))
              .append(Text.translatable("text.artent_potions.fermentation.time.seconds_suffix"))
              .append(Text.translatable("text.artent_potions.fermentation.time.left_suffix"));

          ChatUtils.sendMessageToPlayer(
              world,
              player,
              Text
                  .translatable("text.artent_potions.barrel.fermenting")
                  .append(Text.literal(barrel.potionAmount + ""))
                  .append(Text.literal(" "))
                  .append(Text.translatable("text.artent_potions.barrel.portions_" + barrel.potionAmount))
                  .append(Text.translatable("text.artent_potions.barrel.fermented.of"))
                  .append(potionNames)
                  .append(Text.translatable("text.artent_potions.fermentation.time.prefix"))
                  .append(timeText));
        } else ChatUtils.sendMessageToPlayer(world, player, Text.translatable("text.artent_potions.barrel.empty"));
      }
      return ActionResult.SUCCESS;
    }
    return ActionResult.PASS;
  }

  @Override
  protected void neighborUpdate(
      BlockState state,
      World world,
      BlockPos pos,
      Block sourceBlock,
      @Nullable WireOrientation wireOrientation,
      boolean notify) {
    super.neighborUpdate(state, world, pos, sourceBlock, wireOrientation, notify);

    updateBarrelType(state, world, pos);
  }

  @Override
  public void onPlaced(
      World world,
      BlockPos pos,
      BlockState state,
      @Nullable LivingEntity placer,
      ItemStack itemStack) {
    super.onPlaced(world, pos, state, placer, itemStack);

    updateBarrelType(state, world, pos);
  }

  private static void updateBarrelType(BlockState state, World world, BlockPos pos) {
    var top = world.getBlockState(pos.up());
    var hasTopBarrel = top.getBlock() instanceof FermentingBarrel;
    var bottom = world.getBlockState(pos.down());
    var hasBottomBarrel = bottom.getBlock() instanceof FermentingBarrel;
    var type = hasTopBarrel || hasBottomBarrel ? BarrelType.Group : BarrelType.Single;
    world.setBlockState(pos, state.with(BARREL_TYPE, type));
  }

  protected static final VoxelShape SHAPE_Z = Block.createCuboidShape(2, 0, 0, 14, 14, 16);
  protected static final VoxelShape SHAPE_X = Block.createCuboidShape(0, 0, 2, 16, 14, 14);
  protected static final VoxelShape SHAPE_Z_BOTTOM = Block.createCuboidShape(2, 0, 0, 14, 16, 16);
  protected static final VoxelShape SHAPE_X_BOTTOM = Block.createCuboidShape(0, 0, 2, 16, 16, 14);

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    var type = state.get(BARREL_TYPE);
    var facing = state.get(FACING);
    if (facing.getAxis() == Direction.Axis.X) {
      if (type == BarrelType.Group) {
        return SHAPE_X_BOTTOM;
      } else return SHAPE_X;
    } else {
      if (type == BarrelType.Group) {
        return SHAPE_Z_BOTTOM;
      } else return SHAPE_Z;
    }
  }


  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
      World world,
      BlockState state,
      BlockEntityType<T> type) {
    return validateTicker(
        type,
        ModBlockEntities.FermentingBarrel,
        (world1, pos, state1, be) -> be.tick(world1, pos, state1));
  }

  @Nullable
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new FermentingBarrelBlockEntity(pos, state);
  }

  @Override
  public void fillDebugInfo(
      World world,
      BlockPos pos,
      BlockState state,
      DebugInfoConsumer debugInfo,
      boolean extended) {
    if (!(world.getBlockEntity(pos) instanceof FermentingBarrelBlockEntity barrel)) return;

    var tickRate = world.getTickManager().getTickRate();
    var fermentationTime = TimeUtils.ticksToTime(barrel.fermentedTime, (int) tickRate);
    var fermentationStatus = barrel.fermentationStarted
        ? barrel.isFermented()
        ? Text.literal("Finished")
        : Text.literal("Fermenting: ").append(TimeUtils.timeToText(fermentationTime)).append(" left")
        : Text.literal("Not started");
    debugInfo.add(Text.literal("Fermentation status: ").append(fermentationStatus));

    var commaText = Text.of(", ");
    var potionsListText = TextUtils.join(
        commaText,
        barrel.potions
            .stream()
            .flatMap((i) -> Arrays.stream(i.statusEffects))
            .map(sei -> Text.translatable(sei.getTranslationKey()))
            .collect(Collectors.toUnmodifiableList()));
    var potionText = Text
        .literal("Potions: ")
        .append(barrel.potions.isEmpty() ? Text.literal("Empty") : potionsListText);

    debugInfo.add(potionText);

    debugInfo.add("Potion amount: " + barrel.getPotionAmount() + "/" + barrel.getMaxPotionAmount());
  }

  public enum BarrelType implements StringIdentifiable {
    Single, Group;

    @Override
    public String asString() {
      return this.name().toLowerCase(Locale.ROOT);
    }
  }
}