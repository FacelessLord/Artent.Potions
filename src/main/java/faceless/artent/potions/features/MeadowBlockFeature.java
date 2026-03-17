package faceless.artent.potions.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.List;

public class MeadowBlockFeature extends Feature<MeadowBlockFeatureConfig> {
  private static final List<Direction> HorizontalDirections = Direction.Type.HORIZONTAL.stream().toList();

  public MeadowBlockFeature(Codec<MeadowBlockFeatureConfig> configCodec) {
    super(configCodec);
  }

  @Override
  public boolean generate(FeatureContext<MeadowBlockFeatureConfig> context) {
    StructureWorldAccess world = context.getWorld();
    // the origin is the place where the game starts trying to place the feature
    BlockPos origin = context.getOrigin();
    var config = context.getConfig();
    BlockState blockState = config.block();
    var attemptsCount = Math.ceil((world.getRandom().nextFloat() / 2f + 0.5f) * config.attemptsCount());

    for (int i = 0; i < attemptsCount; i++) {
      var radius = world.getRandom().nextDouble() * config.radius();
      var angle = (float) (world.getRandom().nextDouble() * Math.TAU);
      var offsetX = (int) (MathHelper.cos(angle) * radius);
      var offsetZ = (int) (MathHelper.sin(angle) * radius);

      var testPos = origin.add(offsetX, 0, offsetZ);

      if (blockState.contains(HorizontalFacingBlock.FACING)) {
        blockState = blockState.with(
            HorizontalFacingBlock.FACING,
            Util.getRandom(HorizontalDirections, context.getRandom()));
      }

      // find the surface of the world
      var topPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, testPos);
      if (world.getBlockState(topPos).isIn(BlockTags.DIRT)) {
        if (world.getBlockState(topPos.up()).isOf(Blocks.AIR)) {
          world.setBlockState(topPos.up(), blockState, 10);
        }
      }
    }

    return false;
  }
}
