package faceless.artent.potions.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.List;

public class VegetationBlockFeature extends Feature<VegetationBlockFeatureConfig> {
  private static final List<Direction> HorizontalDirections = Direction.Type.HORIZONTAL.stream().toList();

  public VegetationBlockFeature(Codec<VegetationBlockFeatureConfig> configCodec) {
    super(configCodec);
  }

  @Override
  public boolean generate(FeatureContext<VegetationBlockFeatureConfig> context) {
    StructureWorldAccess world = context.getWorld();
    // the origin is the place where the game starts trying to place the feature
    BlockPos origin = context.getOrigin();

    BlockState blockState = context.getConfig().block();
    if (blockState.contains(HorizontalFacingBlock.FACING)) {
      blockState = blockState.with(
          HorizontalFacingBlock.FACING,
          Util.getRandom(HorizontalDirections, context.getRandom()));
    }
    // find the surface of the world
    BlockPos testPos = new BlockPos(origin);
    for (int y = 0; y < world.getHeight(); y++) {
      testPos = testPos.up();
      // the tag name is dirt, but includes grass, mud, podzol, etc.
      if (world.getBlockState(testPos).isIn(BlockTags.DIRT)) {
        if (world.getBlockState(testPos.up()).isOf(Blocks.AIR)) {
          world.setBlockState(testPos.up(), blockState, 10);
          return true;
        }
      }
    }

    return false;
  }
}
