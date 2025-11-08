package faceless.artent.potions.block;

import com.mojang.serialization.MapCodec;
import faceless.artent.core.item.INamed;
import faceless.artent.potions.blockEntities.DryingRackBlockEntity;
import faceless.artent.potions.objects.ModBlockEntities;
import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModEntities;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DryingRack extends BlockWithEntity implements INamed {
  public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
  public static final MapCodec<FermentingBarrel> CODEC = FermentingBarrel.createCodec(FermentingBarrel::new);

  public DryingRack(Settings settings) {
    super(settings);
    this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
  }

  @Override
  protected MapCodec<? extends BlockWithEntity> getCodec() {
    return CODEC;
  }

  @Override
  public String getId() {
    return "drying_rack";
  }

  @Override
  public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new DryingRackBlockEntity(pos, state);
  }
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
      World world,
      BlockState state,
      BlockEntityType<T> type) {
    return validateTicker(
        type,
        ModBlockEntities.DryingRack,
        (world1, pos, state1, be) -> be.tick(world1, pos, state1));
  }


  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING);
  }

  protected static final VoxelShape SHAPE_NORTH = Block.createCuboidShape(0, 0, 13, 16, 16, 16);
  protected static final VoxelShape SHAPE_SOUTH = Block.createCuboidShape(0, 0, 0, 16, 16, 3);
  protected static final VoxelShape SHAPE_WEST = Block.createCuboidShape(13, 0, 0, 16, 16, 16);
  protected static final VoxelShape SHAPE_EAST = Block.createCuboidShape(0, 0, 0, 3, 16, 16);

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    var facing = state.get(FACING);
    return switch (facing) {
      case NORTH -> SHAPE_NORTH;
      case SOUTH -> SHAPE_SOUTH;
      case WEST -> SHAPE_WEST;
      case EAST -> SHAPE_EAST;
      default -> null;
    };
  }

}
