package faceless.artent.potions.block;

import com.mojang.serialization.MapCodec;
import faceless.artent.core.functions.Factory;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class FlowerCropBlock extends PlantBlock implements Fertilizable {
  public static final IntProperty AGE = IntProperty.of("age", 0, 3);
  public final MapCodec<FlowerCropBlock> CODEC;
  protected static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
      Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 2.0, 9.0), Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 6.0, 9.0),
      Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 10.0, 9.0), Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 14.0, 9.0),
  };
  private int maxAge;
  private final Block flower;
  private final Factory<Item> seedsFactory;

  public FlowerCropBlock(int maxAge, Block flower, Factory<Item> seedsFactory, Settings settings) {
    super(settings);
    this.maxAge = maxAge;
    this.flower = flower;
    this.seedsFactory = seedsFactory;
    CODEC = FlowerCropBlock.createCodec((settingsFromCodec) -> new FlowerCropBlock(
        maxAge,
        flower,
        seedsFactory,
        settingsFromCodec));

    this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
  }

  protected ItemConvertible getSeedsItem() {
    return seedsFactory.create();
  }

  @Override
  protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
    return new ItemStack(this.getSeedsItem());
  }

  @Override
  protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    if (!state.canPlaceAt(world, pos)) {
      world.breakBlock(pos, true);
    }
  }

  @Override
  protected BlockState getStateForNeighborUpdate(
      BlockState state,
      WorldView world,
      ScheduledTickView tickView,
      BlockPos pos,
      Direction direction,
      BlockPos neighborPos,
      BlockState neighborState,
      Random random) {
    if (!state.canPlaceAt(world, pos)) {
      tickView.scheduleBlockTick(pos, this, 1);
    }
    return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
  }

  @Override
  protected boolean hasRandomTicks(BlockState state) {
    return true;
  }

  public int getAge(BlockState state) {
    return state.get(AGE);
  }

  public BlockState withAge(int age) {
    return this.getDefaultState().with(AGE, age);
  }

  @Override
  protected MapCodec<? extends PlantBlock> getCodec() {
    return CODEC;
  }

  @Override
  public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
    return true;
  }

  @Override
  public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
    return true;
  }

  @Override
  public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
    applyGrowth(world, pos, state);
  }

  protected int getGrowthAmount(World world) {
    return MathHelper.nextInt(world.random, 1, 3);
  }

  public int getMaxAge() {
    return this.maxAge;
  }

  @Override
  protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
    if (world instanceof ServerWorld serverWorld) {
      if (entity instanceof RavagerEntity && serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
        serverWorld.breakBlock(pos, true, entity);
      }
    }
    super.onEntityCollision(state, world, pos, entity);
  }

  @Override
  protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    int i;
    if (hasEnoughLightAt(world, pos)
        && (i = this.getAge(state)) < this.getMaxAge()
        && random.nextInt((int) (10 / getAvailableMoisture(this, world, pos)) + 1) == 0) {
      updateAge(world, pos, i + 1);
    }
  }

  public void applyGrowth(World world, BlockPos pos, BlockState state) {
    int i = this.getAge(state) + this.getGrowthAmount(world);
    updateAge(world, pos, i);
  }

  private void updateAge(World world, BlockPos pos, int i) {
    if (i >= this.getMaxAge()) {
      world.setBlockState(pos, this.flower.getDefaultState(), Block.NOTIFY_LISTENERS);
    } else {
      world.setBlockState(pos, this.withAge(i), Block.NOTIFY_LISTENERS);
    }
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(AGE);
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return AGE_TO_SHAPE[this.getAge(state)];
  }

  protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
    float f = 1.0f;
    BlockPos blockPos = pos.down();
    for (int i = -1; i <= 1; ++i) {
      for (int j = -1; j <= 1; ++j) {
        float g = 0.0f;
        BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
        if (blockState.isOf(Blocks.FARMLAND)) {
          g = 1.0f;
          if (blockState.get(FarmlandBlock.MOISTURE) > 0) {
            g = 3.0f;
          }
        }
        if (i != 0 || j != 0) {
          g /= 4.0f;
        }
        f += g;
      }
    }
    BlockPos blockPos2 = pos.north();
    BlockPos blockPos3 = pos.south();
    BlockPos blockPos4 = pos.west();
    BlockPos blockPos5 = pos.east();
    boolean bl = world.getBlockState(blockPos4).isOf(block) || world.getBlockState(blockPos5).isOf(block);
    boolean bl2 = world.getBlockState(blockPos2).isOf(block) || world.getBlockState(blockPos3).isOf(block);
    if (bl && bl2) {
      f /= 2.0f;
    } else {
      boolean bl32 = world.getBlockState(blockPos4.north()).isOf(block) || world
          .getBlockState(blockPos5.north())
          .isOf(block) || world.getBlockState(blockPos5.south()).isOf(block) || world
                         .getBlockState(blockPos4.south())
                         .isOf(block);
      if (bl32) {
        f /= 2.0f;
      }
    }
    return f;
  }

  protected static boolean hasEnoughLightAt(WorldView world, BlockPos pos) {
    return world.getBaseLightLevel(pos, 0) >= 8;
  }
}
