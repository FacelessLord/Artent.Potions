package faceless.artent.potions.block;

import com.mojang.serialization.MapCodec;
import faceless.artent.core.api.ChatUtils;
import faceless.artent.core.api.MiscUtils;
import faceless.artent.core.item.INamed;
import faceless.artent.potions.blockEntities.FermentingBarrelBlockEntity;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.item.ConcentrateItem;
import faceless.artent.potions.objects.ModBlockEntities;
import faceless.artent.potions.objects.ModItems;
import faceless.artent.potions.registry.AlchemicalPotionRegistry;
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
    var be = (FermentingBarrelBlockEntity) world.getBlockEntity(pos);
    if (be == null) return ActionResult.FAIL;
    var stack = player.getEquippedStack(player.getActiveHand() == Hand.MAIN_HAND
                                            ? EquipmentSlot.MAINHAND
                                            : EquipmentSlot.OFFHAND);

    if (stack.getItem() == ModItems.GoldenBucketFilled) {
      if (be.potionKey.isEmpty()) {
        var potion = AlchemicalPotionUtil.getPotion(stack);
        if (potion != null) {
          be.setPotionId(potion.id);
          player.setStackInHand(
              player.getActiveHand(),
              ItemUsage.exchangeStack(stack, player, new ItemStack(ModItems.GoldenBucket)));
          ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled");
          return ActionResult.SUCCESS;
        }
      } else ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled.already");
      return ActionResult.FAIL;
    } else if (stack.getItem() == ModItems.PotionPhial) {
      var potion = AlchemicalPotionUtil.getPotion(stack);
      if (potion == null || !AlchemicalPotionRegistry.fermentedPotionIsRegistered(potion.id)) {
        ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.potion.infermentable");
        return ActionResult.FAIL;
      }
      if (be.potionKey.isEmpty()) {
        be.setPotionId(potion.id, 3);
        if (!player.isCreative()) {
          stack.decrement(1);
          MiscUtils.giveOrDropItem(world, pos, player, new ItemStack(ModItems.EmptyPhial));
        }
        ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled");
        return ActionResult.SUCCESS;
      } else if (be.potionKey.equals(potion.id)) {
        if (be.portionsLeft <= 6) {
          be.setPotionId(potion.id, be.portionsLeft + 3);
          if (!player.isCreative()) {
            stack.decrement(1);
            MiscUtils.giveOrDropItem(world, pos, player, new ItemStack(ModItems.EmptyPhial));
          }
          ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled");
          return ActionResult.SUCCESS;
        } else ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled.already");
        return ActionResult.FAIL;
      } else ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled.different");
      return ActionResult.FAIL;
    } else if (be.isFermented()) {
      ActionResult result = fillConcentrate(world, pos, player, stack, be);
      if (result == ActionResult.FAIL) return result;
    }
    if (stack.isEmpty() && world.isClient()) {
      if (be.isFermented()) {
        player.sendMessage(
            Text
                .translatable("text.artent_potions.barrel.fermented."
                              + be.portionsLeft
                              + "/"
                              + 9
                              + ".of")
                .append(Text.translatable(be.potionKey)), false);
        return ActionResult.SUCCESS;
      } else {
        if (!be.potionKey.isEmpty()) player.sendMessage(
            Text
                .translatable("text.artent_potions.barrel.fermenting." + be.portionsLeft + "/" + 9)
                .append(Text.translatable(be.potionKey))
                .append(Text.translatable("text.artent_potions.fermentation.time.prefix"))
                .append(Text.translatable(String.valueOf((FermentingBarrelBlockEntity.FERMENTATION_TIME
                                                          - be.fermentedTime) / 20)))
                .append(Text.translatable("text.artent_potions.fermentation.time.suffix")), false);
        else player.sendMessage(Text.translatable("text.artent_potions.barrel.empty"), false);
        return ActionResult.SUCCESS;
      }
    }
    return ActionResult.PASS;
  }

  private static ActionResult fillConcentrate(
      World world,
      BlockPos pos,
      PlayerEntity player,
      ItemStack stack,
      FermentingBarrelBlockEntity be) {
    var barrelPotion = AlchemicalPotionRegistry.getFermentedPotions().getOrDefault(be.potionKey, null);
    if (barrelPotion == null) return ActionResult.FAIL;
    var potionKey = barrelPotion.id;
    var item = stack.getItem();

    if (item instanceof ConcentrateItem container) {
      var itemPotion = AlchemicalPotionUtil.getFermentedPotion(stack);
      var containerSize = container.getMaxSize(stack);

      if (itemPotion == null || itemPotion.id == null) {
        var amount = be.takePotionPortions(containerSize);
        if (amount == 0) return ActionResult.FAIL;
        if (!player.isCreative()) {
          stack.decrement(containerSize);
        }

        var containerStack = new ItemStack(container);
        container.setConcentrateAmount(containerStack, potionKey, amount);
        MiscUtils.giveOrDropItem(world, pos, player, containerStack);
      } else if (itemPotion.id.equals(potionKey)) {
        var concentrateAmount = container.getConcentrateAmount(stack);
        var amount = be.takePotionPortions(Math.min(containerSize - concentrateAmount, containerSize));
        if (amount == 0) return ActionResult.FAIL;
        container.setConcentrateAmount(stack, amount + concentrateAmount);
      }
    }
    return ActionResult.SUCCESS;
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