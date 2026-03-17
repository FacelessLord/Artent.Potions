package faceless.artent.potions.objects;

import com.google.common.collect.ImmutableList;
import faceless.artent.potions.features.BerryBushFeatureConfig;
import faceless.artent.potions.features.WorldGenContext;
import faceless.artent.potions.registry.FeatureRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.dynamic.Range;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.DualNoiseBlockStateProvider;
import net.minecraft.world.gen.stateprovider.NoiseThresholdBlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

public class ModFeatures {

    private static final PlacementModifier NOT_IN_SURFACE_WATER_MODIFIER = SurfaceWaterDepthFilterPlacementModifier.of(0);

    private static ImmutableList<PlacementModifier> treeModifiers(Block block) {
        return ImmutableList.<PlacementModifier>builder().add(NOT_IN_SURFACE_WATER_MODIFIER).add(
                BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(
                        block.getDefaultState(),
                        BlockPos.ORIGIN
                ))).build();
    }

    private static final TreeFeatureConfig CRIMSON_TREE_CONFIG = new TreeFeatureConfig.Builder(
            BlockStateProvider.of(ModBlocks.CRIMSONWOOD_LOG.block()),
            new StraightTrunkPlacer(5, 2, 0),
            BlockStateProvider.of(ModBlocks.CRIMSONWOOD_LEAVES.block()),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
            new TwoLayersFeatureSize(1, 0, 1)
    ).build();
    public static final ConfiguredFeature<TreeFeatureConfig, Feature<TreeFeatureConfig>> CRIMSON_TREE = new ConfiguredFeature<>(
            TreeFeature.TREE,
            CRIMSON_TREE_CONFIG
    );
    public static final PlacedFeature CRIMSONWOOD_TREE_PLACED = new PlacedFeature(
            RegistryEntry.of(CRIMSON_TREE),
            treeModifiers(ModBlocks.CRIMSONWOOD_SAPLING.block())
    );

    private static final TreeFeatureConfig CRIMSON_MEGA_TREE_CONFIG = new TreeFeatureConfig.Builder(
            BlockStateProvider.of(ModBlocks.CRIMSONWOOD_LOG.block()),
            new LargeOakTrunkPlacer(3, 11, 0),
            BlockStateProvider.of(ModBlocks.CRIMSONWOOD_LEAVES.block()),
            new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
    ).ignoreVines().build();
    public static final ConfiguredFeature<TreeFeatureConfig, Feature<TreeFeatureConfig>> CRIMSON_MEGA_TREE = new ConfiguredFeature<>(
            TreeFeature.TREE,
            CRIMSON_MEGA_TREE_CONFIG
    );
    public static final PlacedFeature CRIMSONWOOD_MEGA_TREE_PLACED = new PlacedFeature(
            RegistryEntry.of(CRIMSON_MEGA_TREE), treeModifiers(ModBlocks.CRIMSONWOOD_SAPLING.block()));

    public static ConfiguredFeature<RandomFeatureConfig, Feature<RandomFeatureConfig>> CRIMSON_TREES;
    public static PlacedFeature CRIMSON_TREES_PLACED;

    public static final BerryBushFeatureConfig BERRY_BUSH_FEATURE_CONFIG = new BerryBushFeatureConfig(
            ModBlocks.BERRY_BUSH.length,
            3
    );

    public static final ConfiguredFeature<BerryBushFeatureConfig, Feature<BerryBushFeatureConfig>> BERRY_BUSH = new ConfiguredFeature<>(
            FeatureRegistry.BERRY_BUSH_FEATURE,
            BERRY_BUSH_FEATURE_CONFIG
    );

    private static final List<PlacementModifier> BushPlacementModifiers;

    static {
        var modifiers = new ArrayList<>(VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                PlacedFeatures.createCountExtraModifier(
                        3,
                        0.1F,
                        1
                ), ModBlocks.BERRY_BUSH[0].block()
        ));
        modifiers.add(SquarePlacementModifier.of());
        BushPlacementModifiers = modifiers;
    }

    public static ConfiguredFeature<SimpleRandomFeatureConfig, Feature<SimpleRandomFeatureConfig>> createForestFlowerFeature(Block... flowerBlock) {
        var features = Arrays.stream(flowerBlock).map(block -> PlacedFeatures.createEntry(
                Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(block.getDefaultState()))
                )
        )).toList();

        var config = new SimpleRandomFeatureConfig(RegistryEntryList.of(features));
        return new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, config);
    }

    public static ConfiguredFeature<RandomPatchFeatureConfig, Feature<RandomPatchFeatureConfig>> createPlainsFlowerFeature(Block[] highFlowerBlock, Block[] lowFlowerBlock) {
        var config = new RandomPatchFeatureConfig(
                64, 6, 2, PlacedFeatures.createEntry(
                Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new NoiseThresholdBlockStateProvider(
                        2345L,
                        new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0, new double[0]),
                        0.005f,
                        -0.8f,
                        0.33333334f,
                        Blocks.AIR.getDefaultState(),
                        Arrays.stream(highFlowerBlock).map(Block::getDefaultState).toList(),
                        Arrays.stream(lowFlowerBlock).map(Block::getDefaultState).toList()
                ))
        )
        );
        return new ConfiguredFeature<>(Feature.FLOWER, config);
    }

    public static ConfiguredFeature<RandomPatchFeatureConfig, Feature<RandomPatchFeatureConfig>> createMeadowFlowerFeature(Block... flowerBlocks) {
        var config = new RandomPatchFeatureConfig(
                96, 6, 2, PlacedFeatures.createEntry(
                Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new DualNoiseBlockStateProvider(
                        new Range<Integer>(1, 3),
                        new DoublePerlinNoiseSampler.NoiseParameters(-10, 1.0, new double[0]),
                        1.0f,
                        2345L,
                        new DoublePerlinNoiseSampler.NoiseParameters(-3, 1.0, new double[0]),
                        1.0f,
                        Arrays.stream(flowerBlocks).map(Block::getDefaultState).toList()
                ))
        )
        );
        return new ConfiguredFeature<>(Feature.FLOWER, config);
    }

    public static ConfiguredFeature<RandomPatchFeatureConfig, Feature<RandomPatchFeatureConfig>> createPumpkinPatchFeature(BlockState patchBlockState, List<Block> predicateBlocks) {
        var config = ConfiguredFeatures.createRandomPatchFeatureConfig(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(patchBlockState)),
                predicateBlocks
        );
        return new ConfiguredFeature<>(Feature.RANDOM_PATCH, config);
    }

    public static ConfiguredFeature<RandomPatchFeatureConfig, Feature<RandomPatchFeatureConfig>> createShroomPatchFeature(BlockState patchBlockState) {
        var config = ConfiguredFeatures.createRandomPatchFeatureConfig(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(patchBlockState))
        );
        return new ConfiguredFeature<>(Feature.RANDOM_PATCH, config);
    }

    public static final ConfiguredFeature<SimpleRandomFeatureConfig, Feature<SimpleRandomFeatureConfig>> ARTENT_POTIONS_FLOWERS_FOREST = createForestFlowerFeature(
            ModBlocks.SHADOWVEIL.block(),
            ModBlocks.BLAZING_MARIGOLD.block()
    );
    public static final ConfiguredFeature<RandomPatchFeatureConfig, Feature<RandomPatchFeatureConfig>> ARTENT_POTIONS_FLOWERS_PLAINS = createPlainsFlowerFeature(
            new Block[]{ModBlocks.SHADOWVEIL.block()},
            new Block[]{ModBlocks.BLAZING_MARIGOLD.block()}
    );
    public static final ConfiguredFeature<RandomPatchFeatureConfig, Feature<RandomPatchFeatureConfig>> ARTENT_POTIONS_FLOWERS_MEADOW = createMeadowFlowerFeature(
            ModBlocks.SHADOWVEIL.block(),
            ModBlocks.BLAZING_MARIGOLD.block()
    );

    public static final ConfiguredFeature<RandomPatchFeatureConfig, Feature<RandomPatchFeatureConfig>> SLIME_BERRY = new ConfiguredFeature<>(
            Feature.FLOWER, new RandomPatchFeatureConfig(
            64,
            6,
            2,
            PlacedFeatures.createEntry(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(BlockStateProvider.of(
                            ModBlocks.SLIME_BERRY.block()))
            )
    )
    );
    public static final ConfiguredFeature<RandomPatchFeatureConfig, Feature<RandomPatchFeatureConfig>> SHROOM = createShroomPatchFeature(
            ModBlocks.SHROOM.block().getDefaultState());
    public static final ConfiguredFeature<RandomPatchFeatureConfig, Feature<RandomPatchFeatureConfig>> FROST_PUMPKIN = createPumpkinPatchFeature(
            ModBlocks.FROST_PUMPKIN.block().getDefaultState(),
            List.of(Blocks.GRASS_BLOCK)
    );

    public static void bootstrap(WorldGenContext ctx) {
        ctx.configuredFeatures().register(FeatureRegistry.CRIMSON_TREE_CONFIGURED_KEY, CRIMSON_TREE);
        ctx.configuredFeatures().register(FeatureRegistry.CRIMSON_MEGA_TREE_CONFIGURED_KEY, CRIMSON_MEGA_TREE);
        ctx.configuredFeatures().register(FeatureRegistry.BERRY_BUSH_CONFIGURED_KEY, BERRY_BUSH);
        ctx.configuredFeatures().register(
                FeatureRegistry.ARTENT_POTIONS_FLOWERS_FOREST_CONFIGURED_KEY,
                ARTENT_POTIONS_FLOWERS_FOREST
        );
        ctx.configuredFeatures().register(
                FeatureRegistry.ARTENT_POTIONS_FLOWERS_PLAINS_CONFIGURED_KEY,
                ARTENT_POTIONS_FLOWERS_PLAINS
        );
        ctx.configuredFeatures().register(
                FeatureRegistry.ARTENT_POTIONS_FLOWERS_MEADOW_CONFIGURED_KEY,
                ARTENT_POTIONS_FLOWERS_MEADOW
        );
        ctx.configuredFeatures().register(FeatureRegistry.SLIME_BERRY_CONFIGURED_KEY, SLIME_BERRY);
        ctx.configuredFeatures().register(FeatureRegistry.SHROOM_CONFIGURED_KEY, SHROOM);
        ctx.configuredFeatures().register(FeatureRegistry.FROST_PUMPKIN_CONFIGURED_KEY, FROST_PUMPKIN);

        var placedCrimsonTree = ctx.placedFeatures().register(
                FeatureRegistry.CRIMSON_TREE_PLACED_KEY,
                CRIMSONWOOD_TREE_PLACED
        );
        var placedCrimsonMegaTree = ctx.placedFeatures().register(
                FeatureRegistry.CRIMSON_MEGA_TREE_PLACED_KEY,
                CRIMSONWOOD_MEGA_TREE_PLACED
        );

        PlacedFeatures.register(
                ctx.placedFeatures(),
                FeatureRegistry.BERRY_BUSH_PLACED_KEY,
                RegistryEntry.of(BERRY_BUSH),
                BushPlacementModifiers
        );
        PlacedFeatures.register(
                ctx.placedFeatures(),
                FeatureRegistry.ARTENT_POTIONS_FLOWERS_FOREST_PLACED_KEY,
                RegistryEntry.of(ARTENT_POTIONS_FLOWERS_FOREST),
                RarityFilterPlacementModifier.of(7),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                CountPlacementModifier.of(ClampedIntProvider.create(UniformIntProvider.create(-3, 1), 0, 1)),
                BiomePlacementModifier.of()
        );
        PlacedFeatures.register(
                ctx.placedFeatures(),
                FeatureRegistry.ARTENT_POTIONS_FLOWERS_PLAINS_PLACED_KEY,
                RegistryEntry.of(ARTENT_POTIONS_FLOWERS_PLAINS),
                NoiseThresholdCountPlacementModifier.of(-0.8, 15, 4),
                RarityFilterPlacementModifier.of(32),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
        );
        PlacedFeatures.register(
                ctx.placedFeatures(),
                FeatureRegistry.ARTENT_POTIONS_FLOWERS_MEADOW_PLACED_KEY,
                RegistryEntry.of(ARTENT_POTIONS_FLOWERS_MEADOW),
                SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()
        );
        PlacedFeatures.register(
                ctx.placedFeatures(),
                FeatureRegistry.SLIME_BERRY_PLACED_KEY,
                RegistryEntry.of(SLIME_BERRY),
                RarityFilterPlacementModifier.of(32),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
        );
        PlacedFeatures.register(
                ctx.placedFeatures(),
                FeatureRegistry.SHROOM_PLACED_KEY,
                RegistryEntry.of(SHROOM),
                RarityFilterPlacementModifier.of(32),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_NO_LEAVES_HEIGHTMAP,
                BiomePlacementModifier.of()
        );
        PlacedFeatures.register(
                ctx.placedFeatures(),
                FeatureRegistry.FROST_PUMPKIN_PLACED_KEY,
                RegistryEntry.of(FROST_PUMPKIN),
                RarityFilterPlacementModifier.of(300), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()
        );

        CRIMSON_TREES = new ConfiguredFeature<>(
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedCrimsonMegaTree, 0.1F)), placedCrimsonTree)
        );
        var crimsonTreesEntry = ctx.configuredFeatures().register(
                FeatureRegistry.CRIMSON_TREES_CONFIGURED_KEY,
                CRIMSON_TREES
        );
        CRIMSON_TREES_PLACED = new PlacedFeature(
                crimsonTreesEntry,
                VegetationPlacedFeatures.treeModifiers(PlacedFeatures.createCountExtraModifier(
                        6,
                        0.1F,
                        1
                ))
        );
        ctx.placedFeatures().register(FeatureRegistry.CRIMSON_TREES_PLACED_KEY, CRIMSON_TREES_PLACED);

        var crimsonForest = ModBiomes.createCrimsonForest(
                ctx.placedFeatures().getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                ctx.carvers().getRegistryLookup(RegistryKeys.CONFIGURED_CARVER)
        );
        ctx.biomes().register(ModBiomes.CRIMSON_FOREST_BIOME_KEY, crimsonForest);
    }
}
