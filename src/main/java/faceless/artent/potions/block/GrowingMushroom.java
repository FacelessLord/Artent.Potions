package faceless.artent.potions.block;

import com.mojang.serialization.MapCodec;
import faceless.artent.core.functions.Factory;
import faceless.artent.potions.api.MushroomType;
import faceless.artent.potions.objects.ModBlocks;
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

public class GrowingMushroom extends PlantBlock implements Fertilizable {
  public static final IntProperty AGE = IntProperty.of("age", 0, 2);
  public final MapCodec<GrowingMushroom> CODEC;
  private final MushroomType type;
  private final Block mushroom;
  private final Factory<Item> seedProvider;
  protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);

  public GrowingMushroom(MushroomType type, Block mushroom, Factory<Item> seedProvider, Settings settings) {
    super(settings);
    this.type = type;
    this.mushroom = mushroom;
    this.seedProvider = seedProvider;
    this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));

    CODEC = GrowingMushroom.createCodec((settingsFromCodec) -> new GrowingMushroom(
        type,
        mushroom,
        seedProvider,
        settingsFromCodec));
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
  protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    int i;
    if (GrowingMushroom.hasEnoughLightAt(world, pos)
        && (i = this.getAge(state)) < this.getMaxAge()
        && random.nextInt((int) (10 / getAvailableMoisture(world, pos)) + 1) == 0) {
      updateAge(world, pos, i + 1);
    }
  }

  @Override
  protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
    return GrowingMushroom.hasEnoughLightAt(world, pos) && super.canPlaceAt(state, world, pos);
  }

  protected static boolean hasEnoughLightAt(WorldView world, BlockPos pos) {
    return world.getBaseLightLevel(pos, 0) < 13;
  }

  protected float getAvailableMoisture(BlockView world, BlockPos pos) {
    float f = 1.0f;
    BlockPos blockPos = pos.down();
    for (int i = -1; i <= 1; ++i) {
      for (int j = -1; j <= 1; ++j) {
        float g = 0.0f;
        BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
        if (blockState.isOf(ModBlocks.MushroomInfo[type.ordinal()].mycelium())) {
          g = 1.0f;
        }
        f += g;
      }
    }
    return f;
  }

  private void updateAge(ServerWorld world, BlockPos pos, int i) {
    if (i >= this.getMaxAge()) {
      world.setBlockState(pos, this.mushroom.getDefaultState(), Block.NOTIFY_LISTENERS);
    } else {
      world.setBlockState(pos, this.withAge(i + 1), Block.NOTIFY_LISTENERS);
    }
  }

  public void applyGrowth(World world, BlockPos pos, BlockState state) {
    int j;
    int i = this.getAge(state) + this.getGrowthAmount(world);
    if (i > (j = this.getMaxAge())) {
      i = j;
    }
    if (i == this.getMaxAge()) {
      world.setBlockState(pos, this.mushroom.getDefaultState(), Block.NOTIFY_LISTENERS);
    } else {
      world.setBlockState(pos, this.withAge(i), Block.NOTIFY_LISTENERS);
    }
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
  protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
    return floor.isOf(ModBlocks.MushroomInfo[type.ordinal()].mycelium());
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
    return 3;
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

  protected ItemConvertible getSeedsItem() {
    return this.seedProvider.create();
  }

  @Override
  protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
    return new ItemStack(this.getSeedsItem());
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(AGE);
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return SHAPE;
  }
}
