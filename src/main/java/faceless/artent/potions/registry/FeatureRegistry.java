package faceless.artent.potions.registry;

import com.google.common.collect.ImmutableList;
import faceless.artent.potions.features.BerryBushFeatureConfig;
import faceless.artent.potions.features.WorldGenContext;
import faceless.artent.potions.objects.ModBiomes;
import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModFeatures;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.BlockFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SurfaceWaterDepthFilterPlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class FeatureRegistry {

  private static final PlacementModifier NOT_IN_SURFACE_WATER_MODIFIER = SurfaceWaterDepthFilterPlacementModifier.of(0);

  private static ImmutableList<PlacementModifier> treeModifiers(
      Block block) {
    return ImmutableList.<PlacementModifier>builder()
                        .add(NOT_IN_SURFACE_WATER_MODIFIER)
                        .add(BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(
                            block.getDefaultState(),
                            BlockPos.ORIGIN))).build();
  }

  private static final TreeFeatureConfig CRIMSON_TREE_CONFIG = new TreeFeatureConfig.Builder(
      BlockStateProvider.of(ModBlocks.CrimsonwoodLog),
      new StraightTrunkPlacer(5, 2, 0),
      BlockStateProvider.of(ModBlocks.CrimsonwoodLeaves),
      new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
      new TwoLayersFeatureSize(1, 0, 1)
  ).build();
  public static final ConfiguredFeature<TreeFeatureConfig, Feature<TreeFeatureConfig>> CRIMSON_TREE = new ConfiguredFeature<>(
      TreeFeature.TREE,
      CRIMSON_TREE_CONFIG);
  public static PlacedFeature CRIMSONWOOD_TREE_PLACED = new PlacedFeature(
      RegistryEntry.of(CRIMSON_TREE),
      treeModifiers(ModBlocks.CrimsonwoodSapling));

  private static final TreeFeatureConfig CRIMSON_MEGA_TREE_CONFIG = new TreeFeatureConfig.Builder(
      BlockStateProvider.of(ModBlocks.CrimsonwoodLog),
      new LargeOakTrunkPlacer(3, 11, 0),
      BlockStateProvider.of(ModBlocks.CrimsonwoodLeaves),
      new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4),
      new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
  )
      .ignoreVines()
      .build();
  public static final ConfiguredFeature<TreeFeatureConfig, Feature<TreeFeatureConfig>> CRIMSON_MEGA_TREE = new ConfiguredFeature<>(
      TreeFeature.TREE,
      CRIMSON_MEGA_TREE_CONFIG);
  public static PlacedFeature CRIMSONWOOD_MEGA_TREE_PLACED = new PlacedFeature(
      RegistryEntry.of(CRIMSON_MEGA_TREE),
      treeModifiers(ModBlocks.CrimsonwoodSapling));

  public static ConfiguredFeature<RandomFeatureConfig, Feature<RandomFeatureConfig>> CRIMSON_TREES;
  public static PlacedFeature CRIMSON_TREES_PLACED;

  public static final BerryBushFeatureConfig BERRY_BUSH_FEATURE_CONFIG = new BerryBushFeatureConfig(
      ModBlocks.berryBush.length,
      3);

  public static final ConfiguredFeature<BerryBushFeatureConfig, Feature<BerryBushFeatureConfig>> BERRY_BUSH = new ConfiguredFeature<>(
      ModFeatures.BERRY_BUSH_FEATURE,
      BERRY_BUSH_FEATURE_CONFIG);

  private static final List<PlacementModifier> BerryBushPlacementModifiers;

  static {
    var modifiers = new ArrayList<>(VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
        PlacedFeatures.createCountExtraModifier(
            3,
            0.1F,
            1),
        ModBlocks.berryBush[0]));
    modifiers.add(SquarePlacementModifier.of());
    BerryBushPlacementModifiers = modifiers;
  }

  public static PlacedFeature BERRY_BUSH_PLACED = new PlacedFeature(
      RegistryEntry.of(BERRY_BUSH),
      BerryBushPlacementModifiers
  );

  public static void bootstrap(WorldGenContext ctx) {
    ctx.configuredFeatures().register(ModFeatures.CRIMSON_TREE_CONFIGURED_KEY, CRIMSON_TREE);
    ctx.configuredFeatures().register(ModFeatures.CRIMSON_MEGA_TREE_CONFIGURED_KEY, CRIMSON_MEGA_TREE);
    ctx.configuredFeatures().register(ModFeatures.BERRY_BUSH_CONFIGURED_KEY, BERRY_BUSH);

    var placedCrimsonTree = ctx.placedFeatures().register(
        ModFeatures.CRIMSON_TREE_PLACED_KEY,
        CRIMSONWOOD_TREE_PLACED);
    var placedCrimsonMegaTree = ctx.placedFeatures().register(
        ModFeatures.CRIMSON_MEGA_TREE_PLACED_KEY,
        CRIMSONWOOD_MEGA_TREE_PLACED);

    ctx.placedFeatures().register(
        ModFeatures.BERRY_BUSH_PLACED_KEY,
        BERRY_BUSH_PLACED);

    CRIMSON_TREES = new ConfiguredFeature<>(
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedCrimsonMegaTree, 0.1F)), placedCrimsonTree));
    var crimsonTreesEntry = ctx.configuredFeatures().register(ModFeatures.CRIMSON_TREES_CONFIGURED_KEY, CRIMSON_TREES);
    CRIMSON_TREES_PLACED = new PlacedFeature(
        crimsonTreesEntry,
        VegetationPlacedFeatures.treeModifiers(PlacedFeatures.createCountExtraModifier(6, 0.1F, 1)));
    ctx.placedFeatures().register(ModFeatures.CRIMSON_TREES_PLACED_KEY, CRIMSON_TREES_PLACED);

    var crimsonForest = ModBiomes.createCrimsonForest(
        ctx.placedFeatures()
           .getRegistryLookup(RegistryKeys.PLACED_FEATURE),
        ctx.carvers()
           .getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
    ctx.biomes().register(ModBiomes.CRIMSON_FOREST_BIOME_KEY, crimsonForest);
  }
}
