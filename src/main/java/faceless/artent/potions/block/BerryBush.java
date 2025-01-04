package faceless.artent.potions.block;

import faceless.artent.core.item.INamed;
import faceless.artent.potions.ingridients.Ingredients;
import faceless.artent.potions.objects.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class BerryBush extends Block implements INamed, Fertilizable {
  public final int type;
  public static final IntProperty AGE = IntProperty.of("age", 0, 2);

  public BerryBush(int type, Settings settings) {
    super(settings);
    this.type = type;
    this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
  }

  @Override
  public String getId() {
    return Ingredients.GetBerryName(type) + "_bush";
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(AGE);
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
    return SHAPE;
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
}