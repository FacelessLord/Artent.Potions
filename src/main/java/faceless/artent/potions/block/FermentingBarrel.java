package faceless.artent.potions.block;

import com.mojang.serialization.MapCodec;
import faceless.artent.core.api.ChatUtils;
import faceless.artent.core.api.MiscUtils;
import faceless.artent.core.item.INamed;
import faceless.artent.potions.api.IPotionContainerItem;
import faceless.artent.potions.api.PotionContainerUtil;
import faceless.artent.potions.blockEntities.FermentingBarrelBlockEntity;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.item.PotionBottleItem;
import faceless.artent.potions.objects.ModBlockEntities;
import faceless.artent.potions.objects.ModItems;
import faceless.artent.potions.registry.FermentationRegistry;
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

import java.util.Locale;
import java.util.stream.Stream;

public class FermentingBarrel extends BlockWithEntity implements INamed {
  public static final MapCodec<FermentingBarrel> CODEC = FermentingBarrel.createCodec(FermentingBarrel::new);
  public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
  public static final EnumProperty<BarrelType> BARREL_TYPE = EnumProperty.of(
      "barrel_type",
      BarrelType.class,
      BarrelType.Top,
      BarrelType.Bottom);

  @Override
  protected MapCodec<? extends BlockWithEntity> getCodec() {
    return CODEC;
  }

  public FermentingBarrel(Settings settings) {
    super(settings);
    this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    this.setDefaultState(getStateManager().getDefaultState().with(BARREL_TYPE, BarrelType.Top));
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
    if (!(world.getBlockEntity(pos) instanceof FermentingBarrelBlockEntity barrel))
      return ActionResult.FAIL;

    var stack = player.getEquippedStack(player.getActiveHand() == Hand.MAIN_HAND
                                            ? EquipmentSlot.MAINHAND
                                            : EquipmentSlot.OFFHAND);
    var item = stack.getItem();

    if (item instanceof IPotionContainerItem) {
      var barrelInterface = PotionContainerUtil.createInterface(barrel);
      var updatedBottle = stack.copyWithCount(1);
      var potionInterface = PotionContainerUtil.createInterface(updatedBottle);
      var transferResult = PotionContainerUtil.transferBetweenContainers(player, potionInterface, barrelInterface);
      player.setStackInHand(player.getActiveHand(), ItemUsage.exchangeStack(stack, player, updatedBottle));

      if (transferResult == PotionContainerUtil.TransferResult.DifferentPotions) {
        ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled.different");
      } else if (transferResult == PotionContainerUtil.TransferResult.BIsFull
                 || transferResult == PotionContainerUtil.TransferResult.BothIsFull) {
        ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled.already");
      } else if (transferResult == PotionContainerUtil.TransferResult.MovedToB) {
        ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled");
      } else if (transferResult == PotionContainerUtil.TransferResult.MovedToA) {
        ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.emptied");
      } else if (transferResult == PotionContainerUtil.TransferResult.BCantContain) {
        ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.infermentable");
      }
    }

    if (stack.isEmpty() && world.isClient()) {
      var effects = AlchemicalPotionUtil.getPotionEffects(stack);
      var potionNames = AlchemicalPotionUtil.getPotionNames(effects);
      if (barrel.isFermented()) {
        player.sendMessage(
            Text
                .translatable("text.artent_potions.barrel.fermented."
                              + barrel.potionAmount
                              + "/"
                              + 9
                              + ".of")
                .append(potionNames), false);
        return ActionResult.SUCCESS;
      } else {
        if (!barrel.potions.isEmpty()) player.sendMessage(
            Text
                .translatable("text.artent_potions.barrel.fermenting." + barrel.potionAmount + "/" + 9)
                .append(potionNames)
                .append(Text.translatable("text.artent_potions.fermentation.time.prefix"))
                .append(Text.translatable(String.valueOf((FermentingBarrelBlockEntity.FERMENTATION_TIME
                                                          - barrel.fermentedTime) / 20)))
                .append(Text.translatable("text.artent_potions.fermentation.time.suffix")), false);
        else player.sendMessage(Text.translatable("text.artent_potions.barrel.empty"), false);
        return ActionResult.SUCCESS;
      }
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
    var type = hasTopBarrel ? BarrelType.Bottom : BarrelType.Top;
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
      if (type == BarrelType.Bottom) {
        return SHAPE_X_BOTTOM;
      } else return SHAPE_X;
    } else {
      if (type == BarrelType.Bottom) {
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

  public enum BarrelType implements StringIdentifiable {
    Top, Bottom;

    @Override
    public String asString() {
      return this.name().toLowerCase(Locale.ROOT);
    }
  }
}