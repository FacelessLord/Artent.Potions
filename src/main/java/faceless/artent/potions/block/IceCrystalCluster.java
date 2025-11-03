package faceless.artent.potions.block;

import faceless.artent.potions.objects.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

public class IceCrystalCluster extends Block implements Waterloggable {
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  public static final EnumProperty<Direction> FACING = Properties.FACING;
  protected final VoxelShape northShape;
  protected final VoxelShape southShape;
  protected final VoxelShape eastShape;
  protected final VoxelShape westShape;
  protected final VoxelShape upShape;
  protected final VoxelShape downShape;

  public IceCrystalCluster(float height, float xzOffset, AbstractBlock.Settings settings) {
    super(settings);

    this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(
        FACING,
        Direction.UP));
    this.upShape = Block.createCuboidShape(xzOffset, 0.0, xzOffset, 16.0f - xzOffset, height, 16.0f - xzOffset);
    this.downShape = Block.createCuboidShape(
        xzOffset,
        16.0f - height,
        xzOffset,
        16.0f - xzOffset,
        16.0,
        16.0f - xzOffset);
    this.northShape = Block.createCuboidShape(
        xzOffset,
        xzOffset,
        16.0f - height,
        16.0f - xzOffset,
        16.0f - xzOffset,
        16.0);
    this.southShape = Block.createCuboidShape(xzOffset, xzOffset, 0.0, 16.0f - xzOffset, 16.0f - xzOffset, height);
    //noinspection SuspiciousNameCombination
    this.eastShape = Block.createCuboidShape(0.0, xzOffset, xzOffset, height, 16.0f - xzOffset, 16.0f - xzOffset);
    this.westShape = Block.createCuboidShape(
        16.0f - height,
        xzOffset,
        xzOffset,
        16.0,
        16.0f - xzOffset,
        16.0f - xzOffset);
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    Direction direction = state.get(FACING);
    return switch (direction) {
      case NORTH -> this.northShape;
      case SOUTH -> this.southShape;
      case EAST -> this.eastShape;
      case WEST -> this.westShape;
      case DOWN -> this.downShape;
      default -> this.upShape;
    };
  }

  @Override
  public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
    Direction direction = state.get(FACING);
    BlockPos blockPos = pos.offset(direction.getOpposite());
    return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
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
    if (state.get(WATERLOGGED)) {
      tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }
    if (direction == state.get(FACING).getOpposite() && !state.canPlaceAt(world, pos)) {
      return Blocks.AIR.getDefaultState();
    }
    return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
  }

  @Override
  public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    super.randomTick(state, world, pos, random);
    var coldModifier = world.getBiome(pos).value().canSetSnow(world, pos) ? 0 : 0.1;
    var lightModifier = Math.max(0, (world.getLightLevel(LightType.BLOCK, pos) - 7) / 45f);
    if (world.random.nextDouble() > 1 - lightModifier - coldModifier) {
      world.setBlockState(pos, createPrevStage(state));
    }
  }


  @Override
  @Nullable
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    World worldAccess = ctx.getWorld();
    BlockPos blockPos = ctx.getBlockPos();
    return this
        .getDefaultState()
        .with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER).with(FACING, ctx.getSide());
  }

  @Override
  protected BlockState rotate(BlockState state, BlockRotation rotation) {
    return state.with(FACING, rotation.rotate(state.get(FACING)));
  }

  @Override
  protected BlockState mirror(BlockState state, BlockMirror mirror) {
    return state.rotate(mirror.getRotation(state.get(FACING)));
  }

  @Override
  protected FluidState getFluidState(BlockState state) {
    if (state.get(WATERLOGGED)) {
      return Fluids.WATER.getStill(false);
    }
    return super.getFluidState(state);
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(WATERLOGGED, FACING);
  }

  private static Block getNextBlock(Block block) {
    if (block == ModBlocks.IceCrystalBud_Small) {
      return ModBlocks.IceCrystalBud_Medium;
    }
    if (block == ModBlocks.IceCrystalBud_Medium) {
      return ModBlocks.IceCrystalBud_Large;
    }
    return ModBlocks.IceCrystalBud_Cluster;
  }

  private static Block getPrevBlock(Block block) {
    if (block == ModBlocks.IceCrystalBud_Cluster) {
      return ModBlocks.IceCrystalBud_Large;
    }
    if (block == ModBlocks.IceCrystalBud_Large) {
      return ModBlocks.IceCrystalBud_Medium;
    }
    if (block == ModBlocks.IceCrystalBud_Medium) {
      return ModBlocks.IceCrystalBud_Small;
    }
    return Blocks.AIR;
  }

  public static BlockState createNextStage(BlockState state) {
    var nextBlock = getNextBlock(state.getBlock());
    return nextBlock
        .getDefaultState()
        .with(WATERLOGGED, state.get(WATERLOGGED))
        .with(FACING, state.get(FACING));
  }

  public static BlockState createPrevStage(BlockState state) {
    var prevBlock = getPrevBlock(state.getBlock());
    if (prevBlock == Blocks.AIR) {
      return state.get(WATERLOGGED)
          ? Blocks.WATER.getDefaultState()
          : Blocks.WATER.getDefaultState().with(FluidBlock.LEVEL, 5);
    }
    return prevBlock
        .getDefaultState()
        .with(WATERLOGGED, state.get(WATERLOGGED))
        .with(FACING, state.get(FACING));
  }
}
