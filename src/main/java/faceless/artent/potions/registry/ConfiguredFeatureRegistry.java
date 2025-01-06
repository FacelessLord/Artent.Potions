package faceless.artent.potions.registry;

import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModFeatures;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class ConfiguredFeatureRegistry {
  private static final TreeFeatureConfig CRIMSON_TREE_CONFIG = new TreeFeatureConfig.Builder(
      BlockStateProvider.of(ModBlocks.CrimsonwoodLog/* TODO .getDefaultState().with(AXIS, Direction.Axis.Y)*/),
      new StraightTrunkPlacer(4, 2, 0),
			BlockStateProvider.of(ModBlocks.CrimsonwoodLeaves),
      new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
      new TwoLayersFeatureSize(1, 0, 1)
		).build();;
  public static final ConfiguredFeature<TreeFeatureConfig, Feature<TreeFeatureConfig>> CRIMSON_TREE = new ConfiguredFeature<>(
      TreeFeature.TREE,
      CRIMSON_TREE_CONFIG);

  public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> configuredFeatureRegisterable) {
    configuredFeatureRegisterable.register(ModFeatures.CRIMSON_TREE_CONFIGURED_KEY, CRIMSON_TREE);
  }

  private static RegistryKey<ConfiguredFeature<?, ?>> keyOf(Identifier id) {
    return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id);
  }
}
