package faceless.artent.potions.block;

import faceless.artent.potions.api.MushroomType;
import faceless.artent.potions.objects.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.MyceliumBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class MushroomMycelium extends MyceliumBlock implements Fertilizable {
  private final MushroomType type;

  public MushroomMycelium(MushroomType type, Settings settings) {
    super(settings);
    this.type = type;
  }

  @Override
  protected boolean hasRandomTicks(BlockState state) {
    return true;
  }

  @Override
  protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    if (canGrow(world, random, pos, state)) grow(world, random, pos, state);
    super.randomTick(state, world, pos, random);
  }

  @Override
  public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
    return true;
  }

  @Override
  public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
    var mushroom = ModBlocks.MushroomInfo[this.type.ordinal()].growingMushroom();
    return world.isAir(pos.up()) && mushroom.canPlaceAt(state, world, pos.up());
  }

  @Override
  public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
    world.setBlockState(
        pos.up(),
        ModBlocks.MushroomInfo[this.type.ordinal()].growingMushroom().getDefaultState(),
        MyceliumBlock.NOTIFY_ALL);
  }
}
