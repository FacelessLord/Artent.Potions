package faceless.artent.potions.objects;

import com.google.common.collect.ImmutableList;
import faceless.artent.potions.features.BerryBushFeatureConfig;
import faceless.artent.potions.features.VegetationBlockFeatureConfig;
import faceless.artent.potions.features.WorldGenContext;
import faceless.artent.potions.registry.FeatureRegistry;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
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

public class ModFeatures {

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
  public static final PlacedFeature CRIMSONWOOD_TREE_PLACED = new PlacedFeature(
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
  public static final PlacedFeature CRIMSONWOOD_MEGA_TREE_PLACED = new PlacedFeature(
      RegistryEntry.of(CRIMSON_MEGA_TREE),
      treeModifiers(ModBlocks.CrimsonwoodSapling));

  public static ConfiguredFeature<RandomFeatureConfig, Feature<RandomFeatureConfig>> CRIMSON_TREES;
  public static PlacedFeature CRIMSON_TREES_PLACED;

  public static final BerryBushFeatureConfig BERRY_BUSH_FEATURE_CONFIG = new BerryBushFeatureConfig(
      ModBlocks.berryBush.length,
      3);

  public static final ConfiguredFeature<BerryBushFeatureConfig, Feature<BerryBushFeatureConfig>> BERRY_BUSH = new ConfiguredFeature<>(
      FeatureRegistry.BERRY_BUSH_FEATURE,
      BERRY_BUSH_FEATURE_CONFIG);

  private static final List<PlacementModifier> PlantPlacementModifiers;

  static {
    var modifiers = new ArrayList<>(VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
        PlacedFeatures.createCountExtraModifier(
            3,
            0.1F,
            1),
        ModBlocks.berryBush[0]));
    modifiers.add(SquarePlacementModifier.of());
    PlantPlacementModifiers = modifiers;
  }

  public static final PlacedFeature BERRY_BUSH_PLACED = new PlacedFeature(
      RegistryEntry.of(BERRY_BUSH),
      PlantPlacementModifiers
  );

  public static final VegetationBlockFeatureConfig SHADOWVEIL_FEATURE_CONFIG = new VegetationBlockFeatureConfig(
      ModBlocks.Shadowveil.getDefaultState(), BlockTags.DIRT);

  public static final ConfiguredFeature<VegetationBlockFeatureConfig, Feature<VegetationBlockFeatureConfig>> SHADOWVEIL = new ConfiguredFeature<>(
      FeatureRegistry.VEGETATION_BLOCK_FEATURE,
      SHADOWVEIL_FEATURE_CONFIG);

  public static final PlacedFeature SHADOWVEIL_PLACED = new PlacedFeature(
      RegistryEntry.of(SHADOWVEIL),
      PlantPlacementModifiers
  );

  public static final VegetationBlockFeatureConfig BLAZING_MARIGOLD_FEATURE_CONFIG = new VegetationBlockFeatureConfig(
      ModBlocks.BlazingMarigold.getDefaultState(), BlockTags.DIRT);

  public static final ConfiguredFeature<VegetationBlockFeatureConfig, Feature<VegetationBlockFeatureConfig>> BLAZING_MARIGOLD = new ConfiguredFeature<>(
      FeatureRegistry.VEGETATION_BLOCK_FEATURE,
      BLAZING_MARIGOLD_FEATURE_CONFIG);

  public static final PlacedFeature BLAZING_MARIGOLD_PLACED = new PlacedFeature(
      RegistryEntry.of(BLAZING_MARIGOLD),
      PlantPlacementModifiers
  );

  public static final VegetationBlockFeatureConfig SLIME_BERRY_FEATURE_CONFIG = new VegetationBlockFeatureConfig(
      ModBlocks.SlimeBerry.getDefaultState(), BlockTags.DIRT);

  public static final ConfiguredFeature<VegetationBlockFeatureConfig, Feature<VegetationBlockFeatureConfig>> SLIME_BERRY = new ConfiguredFeature<>(
      FeatureRegistry.VEGETATION_BLOCK_FEATURE,
      SLIME_BERRY_FEATURE_CONFIG);

  public static final PlacedFeature SLIME_BERRY_PLACED = new PlacedFeature(
      RegistryEntry.of(SLIME_BERRY),
      PlantPlacementModifiers
  );

  public static final VegetationBlockFeatureConfig SHROOM_FEATURE_CONFIG = new VegetationBlockFeatureConfig(
      ModBlocks.Shroom.getDefaultState(), BlockTags.DIRT);

  public static final ConfiguredFeature<VegetationBlockFeatureConfig, Feature<VegetationBlockFeatureConfig>> SHROOM = new ConfiguredFeature<>(
      FeatureRegistry.VEGETATION_BLOCK_FEATURE,
      SHROOM_FEATURE_CONFIG);

  public static final PlacedFeature SHROOM_PLACED = new PlacedFeature(
      RegistryEntry.of(SHROOM),
      PlantPlacementModifiers
  );


  public static final VegetationBlockFeatureConfig FROST_PUMPKIN_FEATURE_CONFIG = new VegetationBlockFeatureConfig(
      ModBlocks.FrostPumpkin.getDefaultState(), BlockTags.DIRT);

  public static final ConfiguredFeature<VegetationBlockFeatureConfig, Feature<VegetationBlockFeatureConfig>> FROST_PUMPKIN = new ConfiguredFeature<>(
      FeatureRegistry.VEGETATION_BLOCK_FEATURE,
      FROST_PUMPKIN_FEATURE_CONFIG);

  public static final PlacedFeature FROST_PUMPKIN_PLACED = new PlacedFeature(
      RegistryEntry.of(FROST_PUMPKIN),
      PlantPlacementModifiers
  );

  public static void bootstrap(WorldGenContext ctx) {
    ctx.configuredFeatures().register(FeatureRegistry.CRIMSON_TREE_CONFIGURED_KEY, CRIMSON_TREE);
    ctx.configuredFeatures().register(FeatureRegistry.CRIMSON_MEGA_TREE_CONFIGURED_KEY, CRIMSON_MEGA_TREE);
    ctx.configuredFeatures().register(FeatureRegistry.BERRY_BUSH_CONFIGURED_KEY, BERRY_BUSH);
    ctx.configuredFeatures().register(FeatureRegistry.SHADOWVEIL_CONFIGURED_KEY, SHADOWVEIL);
    ctx.configuredFeatures().register(FeatureRegistry.BLAZING_MARIGOLD_CONFIGURED_KEY, BLAZING_MARIGOLD);
    ctx.configuredFeatures().register(FeatureRegistry.SLIME_BERRY_CONFIGURED_KEY, SLIME_BERRY);
    ctx.configuredFeatures().register(FeatureRegistry.SHROOM_CONFIGURED_KEY, SHROOM);
    ctx.configuredFeatures().register(FeatureRegistry.FROST_PUMPKIN_CONFIGURED_KEY, FROST_PUMPKIN);

    var placedCrimsonTree = ctx.placedFeatures().register(
        FeatureRegistry.CRIMSON_TREE_PLACED_KEY,
        CRIMSONWOOD_TREE_PLACED);
    var placedCrimsonMegaTree = ctx.placedFeatures().register(
        FeatureRegistry.CRIMSON_MEGA_TREE_PLACED_KEY,
        CRIMSONWOOD_MEGA_TREE_PLACED);

    ctx.placedFeatures().register(
        FeatureRegistry.BERRY_BUSH_PLACED_KEY,
        BERRY_BUSH_PLACED);
    ctx.placedFeatures().register(
        FeatureRegistry.SHADOWVEIL_PLACED_KEY,
        SHADOWVEIL_PLACED);
    ctx.placedFeatures().register(
        FeatureRegistry.BLAZING_MARIGOLD_PLACED_KEY,
        BLAZING_MARIGOLD_PLACED);
    ctx.placedFeatures().register(
        FeatureRegistry.SLIME_BERRY_PLACED_KEY,
        SLIME_BERRY_PLACED);
    ctx.placedFeatures().register(
        FeatureRegistry.SHROOM_PLACED_KEY,
        SHROOM_PLACED);
    ctx.placedFeatures().register(
        FeatureRegistry.FROST_PUMPKIN_PLACED_KEY,
        FROST_PUMPKIN_PLACED);

    CRIMSON_TREES = new ConfiguredFeature<>(
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedCrimsonMegaTree, 0.1F)), placedCrimsonTree));
    var crimsonTreesEntry = ctx.configuredFeatures().register(FeatureRegistry.CRIMSON_TREES_CONFIGURED_KEY, CRIMSON_TREES);
    CRIMSON_TREES_PLACED = new PlacedFeature(
        crimsonTreesEntry,
        VegetationPlacedFeatures.treeModifiers(PlacedFeatures.createCountExtraModifier(6, 0.1F, 1)));
    ctx.placedFeatures().register(FeatureRegistry.CRIMSON_TREES_PLACED_KEY, CRIMSON_TREES_PLACED);

    var crimsonForest = ModBiomes.createCrimsonForest(
        ctx.placedFeatures()
           .getRegistryLookup(RegistryKeys.PLACED_FEATURE),
        ctx.carvers()
           .getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
    ctx.biomes().register(ModBiomes.CRIMSON_FOREST_BIOME_KEY, crimsonForest);
  }
}
