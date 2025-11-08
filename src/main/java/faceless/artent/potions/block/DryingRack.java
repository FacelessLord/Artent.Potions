package faceless.artent.potions.block;

import com.mojang.serialization.MapCodec;
import faceless.artent.core.item.INamed;
import faceless.artent.potions.blockEntities.DryingRackBlockEntity;
import faceless.artent.potions.objects.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class DryingRack extends BlockWithEntity implements INamed {
  public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
  public static final MapCodec<FermentingBarrel> CODEC = FermentingBarrel.createCodec(FermentingBarrel::new);

  public DryingRack(Settings settings) {
    super(settings);
    this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
  }

  @Override
  protected ActionResult onUseWithItem(
      ItemStack stack,
      BlockState state,
      World world,
      BlockPos pos,
      PlayerEntity player,
      Hand hand,
      BlockHitResult hit) {
    if(world.isClient)
      return ActionResult.SUCCESS;

    var blockEntityOptional = world.getBlockEntity(pos, ModBlockEntities.DryingRack);
    if (blockEntityOptional.isEmpty())
      return ActionResult.FAIL;
    var blockEntity = blockEntityOptional.get();
    var facing = state.get(FACING);
    var facingVector = facing.getDoubleVector();
    var localLeftDir = facingVector.crossProduct(new Vec3d(0, 1, 0));

    var hitPos = hit.getPos();
    // remove y coordinate, then center
    var horizonalHit = hitPos.subtract(pos.getX(), pos.getY(), pos.getZ()).multiply(1, 0, 1).add(-0.5f, 0, -0.5f);
    var y = (hitPos.y - pos.getY());

    var horizontalIndex = localLeftDir.dotProduct(horizonalHit) > 0 ? 0 : 1;

    var yIndex = y > 0.5 ? 0 : 2;
    var index = horizontalIndex + yIndex;

    blockEntity.exchangeSlot(index, stack, player);

    player.sendMessage(
        Text.literal("%d %f %f %f".formatted(
            index,
            (hitPos.x - pos.getX()) * (1 - Math.abs(facing.getDoubleVector().x)),
            (hitPos.y - pos.getY()) * (1 - Math.abs(facing.getDoubleVector().y)),
            (hitPos.z - pos.getZ()) * (1 - Math.abs(facing.getDoubleVector().z)))), false);
    return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
  }

  @Override
  protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    return super.onUse(state, world, pos, player, hit);
  }

  @Override
  public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    var blockEntityOptional = world.getBlockEntity(pos, ModBlockEntities.DryingRack);
    if (blockEntityOptional.isPresent()) {
      var blockEntity = blockEntityOptional.get();
      var collector = new ArrayList<ItemStack>(8);
      for (int i = 0; i < blockEntity.getInventorySize(); i++) {
        collector.addAll(blockEntity.dropSlot(i));
      }
      collector.forEach(player::giveOrDropStack);
    }
    return super.onBreak(world, pos, state, player);
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
    return validateTicker(type, ModBlockEntities.DryingRack, (world1, pos, state1, be) -> be.tick(world1, pos, state1));
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
