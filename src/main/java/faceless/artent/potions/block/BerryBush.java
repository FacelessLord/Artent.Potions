package faceless.artent.potions.block;

import faceless.artent.core.item.INamed;
import faceless.artent.potions.ingridients.Ingredients;
import faceless.artent.potions.objects.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class BerryBush extends Block implements INamed, Fertilizable {
  public final int type;
  public static final IntProperty AGE = IntProperty.of("age", 0, 2);
  public static final EnumProperty<BushType> BUSH_TYPE = EnumProperty.of(
      "bush_type",
      BushType.class,
      BushType.Single,
      BushType.Bottom,
      BushType.Middle,
      BushType.Top);

  public BerryBush(int type, Settings settings) {
    super(settings);
    this.type = type;
    this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    this.setDefaultState(this.stateManager.getDefaultState().with(BUSH_TYPE, BushType.Single));
  }

  @Override
  public String getId() {
    return Ingredients.GetBerryName(type) + "_bush";
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(AGE);
    builder.add(BUSH_TYPE);
  }

  @Override
  public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    super.randomTick(state, world, pos, random);
    if (canGrow(world, random, pos, state)) {
      grow(world, random, pos, state);
    }
  }

  @Override
  protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

    var stack = player.getEquippedStack(player.getActiveHand() == Hand.MAIN_HAND
                                            ? EquipmentSlot.MAINHAND
                                            : EquipmentSlot.OFFHAND);
    if (stack.getItem() == Items.BONE_MEAL) {
      if (!world.isClient()) {
        if (world.getBaseLightLevel(pos, 0) >= 9 && state.get(AGE) < 2 && Math.random() < 0.80) {
          world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1), Block.NOTIFY_LISTENERS);
        }
        var newStack = stack.copy();
        newStack.setCount(stack.getCount() - 1);
        player.setStackInHand(player.getActiveHand(), newStack);
      }

      return ActionResult.SUCCESS_SERVER;
    }

    if (state.get(AGE) == 2) {
      if (!world.isClient()) {
        player.giveItemStack(new ItemStack(ModItems.berries[type], world.random.nextInt(3) + 1));
        state = state.with(AGE, 0);
        world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
      }

      return ActionResult.SUCCESS_SERVER;
    }
    return ActionResult.FAIL;
  }

  protected static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 15, 14);

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return SHAPE; // TODO
  }

  @Override
  public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
    return true;
  }

  @Override
  public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
    return world.getBaseLightLevel(pos, 0) >= 9 && state.get(AGE) < 2;
  }

  @Override
  public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
    if (state.get(AGE) < 2)
      world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1), Block.NOTIFY_LISTENERS);
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
    var top = world.getBlockState(pos.up());
    var bottom = world.getBlockState(pos.down());
    var hasTopBush = top.getBlock() instanceof BerryBush;
    var hasBottomBush = bottom.getBlock() instanceof BerryBush;

    if (hasTopBush) {
      if (hasBottomBush)
        world.setBlockState(pos, state.with(BUSH_TYPE, BushType.Middle));
      else
        world.setBlockState(pos, state.with(BUSH_TYPE, BushType.Bottom));
    } else {
      if (hasBottomBush)
        world.setBlockState(pos, state.with(BUSH_TYPE, BushType.Top));
      else
        world.setBlockState(pos, state.with(BUSH_TYPE, BushType.Single));
    }
  }

  protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
    if (floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND))
      return true;
    if (floor.getBlock() instanceof BerryBush) {
      var count = 1;
      for (int i = 0; i < 3; i++) {
        if (world.getBlockState(pos.down(i + 1)).getBlock() instanceof BerryBush) {
          count++;
        } else break;
      }

      return count < 3;
    }
    return false;
  }

  @Override
  protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
    BlockPos blockPos = pos.down();
    return this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
  }

  @Override
  public void onPlaced(
      World world,
      BlockPos pos,
      BlockState state,
      @Nullable LivingEntity placer,
      ItemStack itemStack) {
    super.onPlaced(world, pos, state, placer, itemStack);
  }

  public static enum BushType implements StringIdentifiable {
    Single,
    Bottom,
    Middle,
    Top;

    @Override
    public String asString() {
      return this.name().toLowerCase(Locale.ROOT);
    }
  }
}