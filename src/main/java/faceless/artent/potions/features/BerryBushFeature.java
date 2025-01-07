package faceless.artent.potions.features;

import com.mojang.serialization.Codec;
import faceless.artent.potions.block.BerryBush;
import faceless.artent.potions.objects.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BerryBushFeature extends Feature<BerryBushFeatureConfig> {
  public BerryBushFeature(Codec<BerryBushFeatureConfig> configCodec) {
    super(configCodec);
  }

  @Override
  public boolean generate(FeatureContext<BerryBushFeatureConfig> context) {
    StructureWorldAccess world = context.getWorld();
    // the origin is the place where the game starts trying to place the feature
    BlockPos origin = context.getOrigin();
    Random random = context.getRandom();
    BerryBushFeatureConfig config = context.getConfig();

    var berryType = random.nextInt(config.berryTypeCount());

    BlockState blockState = ModBlocks.berryBush[berryType].getDefaultState();
    var height = randomHeight(random, config.maxHeight());

    // find the surface of the world
    BlockPos testPos = new BlockPos(origin);

    for (int y = 0; y < world.getHeight(); y++) {
      testPos = testPos.up();
      // the tag name is dirt, but includes grass, mud, podzol, etc.
      if (world.getBlockState(testPos).isIn(BlockTags.DIRT)) {
        var canPlaceBush = true;

        for (int i = 0; i < height; i++) {
          if (!world.getBlockState(testPos.up(i + 1)).isOf(Blocks.AIR)) {
            canPlaceBush = false;
            break;
          }
        }

        if (canPlaceBush) {
          testPos = testPos.up();
          for (int i = 0; i < height; i++) {
            var bushType = BerryBush.BushType.Single;
            if(i == 0 && height > 1){
              bushType = BerryBush.BushType.Bottom;
            }
            if(i > 0) {
              if (i < height -1){
                bushType = BerryBush.BushType.Middle;
              } else bushType = BerryBush.BushType.Top;
            }
            // create a simple pillar of blocks
            world.setBlockState(testPos, blockState.with(BerryBush.BUSH_TYPE, bushType), 0x11);
            testPos = testPos.up();

            // ensure we don't try to place blocks outside the world
            if (testPos.getY() >= world.getTopYInclusive()) break;
          }
          return true;
        }
      }
    }
    // the game couldn't find a place to put the pillar
    return false;
  }

  private int randomHeight(Random random, int maxHeight) {
    var weightSum = (1 + 0.25 * (maxHeight - 1) + 1) * maxHeight / 2f;
    var value = random.nextDouble() * weightSum;
    for (int i = 0; i < maxHeight; i++) {
      var weight = (1 + 0.25 * (maxHeight - i - 1));
      if (value < weight) {
        return i + 1;
      }
      value -= weight;
    }
    return 1;
  }
}
