package faceless.artent.potions.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;

public class Shroom extends MushroomPlantBlock {
  public Shroom(
      Settings settings) {
    super(null, settings);
  }

  @Override
  public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
    var positions = new ArrayList<BlockPos>();
    for (int i = -3; i < 4; i++) {
      for (int j = -3; j < 4; j++) {
        for (int k = -3; k < 4; k++) {
          var newPos = pos.add(i, j, k);
          var newState = world.getBlockState(newPos);
          if (!newState.isAir() || !this.canPlaceAt(state, world, newPos)) continue;
          positions.add(newPos);
        }
      }
    }

    if (positions.isEmpty()) return;

    var newShroomPos = positions.get(random.nextInt(positions.size()));
    world.setBlockState(newShroomPos, state);
  }
}
