package faceless.artent.potions.registry;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.features.*;
import faceless.artent.potions.objects.ModBiomes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;

public class FeatureRegistry {
  public static final Identifier CRIMSON_TREE_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "crimson_tree");
  public static final RegistryKey<ConfiguredFeature<?, ?>> CRIMSON_TREE_CONFIGURED_KEY = configuredKeyOf(
      CRIMSON_TREE_FEATURE_ID);
  public static RegistryKey<PlacedFeature> CRIMSON_TREE_PLACED_KEY = placedKeyOf(CRIMSON_TREE_FEATURE_ID);

  public static final Identifier CRIMSON_MEGA_TREE_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "crimson_mega_tree");
  public static final RegistryKey<ConfiguredFeature<?, ?>> CRIMSON_MEGA_TREE_CONFIGURED_KEY = configuredKeyOf(
      CRIMSON_MEGA_TREE_FEATURE_ID);
  public static final RegistryKey<PlacedFeature> CRIMSON_MEGA_TREE_PLACED_KEY = placedKeyOf(CRIMSON_MEGA_TREE_FEATURE_ID);

  public static final Identifier CRIMSON_TREES_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "crimson_trees");
  public static final RegistryKey<ConfiguredFeature<?, ?>> CRIMSON_TREES_CONFIGURED_KEY = configuredKeyOf(
      CRIMSON_TREES_FEATURE_ID);
  public static final RegistryKey<PlacedFeature> CRIMSON_TREES_PLACED_KEY = placedKeyOf(CRIMSON_TREES_FEATURE_ID);

  public static final Identifier BERRY_BUSH_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "berry_bush");
  public static final RegistryKey<ConfiguredFeature<?, ?>> BERRY_BUSH_CONFIGURED_KEY = configuredKeyOf(
      BERRY_BUSH_FEATURE_ID);
  public static final RegistryKey<PlacedFeature> BERRY_BUSH_PLACED_KEY = placedKeyOf(BERRY_BUSH_FEATURE_ID);
  public static final Feature<BerryBushFeatureConfig> BERRY_BUSH_FEATURE = new BerryBushFeature(BerryBushFeatureConfig.CODEC);

  public static final Identifier VEGETATION_BLOCK_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "vegetation_bock");
  public static final Feature<VegetationBlockFeatureConfig> VEGETATION_BLOCK_FEATURE = new VegetationBlockFeature(
      VegetationBlockFeatureConfig.CODEC);

  public static final Identifier ARTENT_POTIONS_FLOWERS_FOREST_ID = Identifier.of(ArtentPotions.MODID, "flowers_forest");
  public static final RegistryKey<ConfiguredFeature<?, ?>> ARTENT_POTIONS_FLOWERS_FOREST_CONFIGURED_KEY = configuredKeyOf(
          ARTENT_POTIONS_FLOWERS_FOREST_ID);
  public static final RegistryKey<PlacedFeature> ARTENT_POTIONS_FLOWERS_FOREST_PLACED_KEY = placedKeyOf(ARTENT_POTIONS_FLOWERS_FOREST_ID);

  public static final Identifier ARTENT_POTIONS_FLOWERS_PLAINS_ID = Identifier.of(ArtentPotions.MODID, "flower_plains");
  public static final RegistryKey<ConfiguredFeature<?, ?>> ARTENT_POTIONS_FLOWERS_PLAINS_CONFIGURED_KEY = configuredKeyOf(
          ARTENT_POTIONS_FLOWERS_PLAINS_ID);
  public static final RegistryKey<PlacedFeature> ARTENT_POTIONS_FLOWERS_PLAINS_PLACED_KEY = placedKeyOf(ARTENT_POTIONS_FLOWERS_PLAINS_ID);

  public static final Identifier ARTENT_POTIONS_FLOWERS_MEADOW_ID = Identifier.of(ArtentPotions.MODID, "flower_meadow");
  public static final RegistryKey<ConfiguredFeature<?, ?>> ARTENT_POTIONS_FLOWERS_MEADOW_CONFIGURED_KEY = configuredKeyOf(
          ARTENT_POTIONS_FLOWERS_MEADOW_ID);
  public static final RegistryKey<PlacedFeature> ARTENT_POTIONS_FLOWERS_MEADOW_PLACED_KEY = placedKeyOf(ARTENT_POTIONS_FLOWERS_MEADOW_ID);

  public static final Identifier SLIME_BERRY_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "slime_berry");
  public static final RegistryKey<ConfiguredFeature<?, ?>> SLIME_BERRY_CONFIGURED_KEY = configuredKeyOf(
      SLIME_BERRY_FEATURE_ID);
  public static final RegistryKey<PlacedFeature> SLIME_BERRY_PLACED_KEY = placedKeyOf(SLIME_BERRY_FEATURE_ID);

  public static final Identifier SHROOM_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "shroom");
  public static final RegistryKey<ConfiguredFeature<?, ?>> SHROOM_CONFIGURED_KEY = configuredKeyOf(SHROOM_FEATURE_ID);
  public static final RegistryKey<PlacedFeature> SHROOM_PLACED_KEY = placedKeyOf(SHROOM_FEATURE_ID);

  public static final Identifier FROST_PUMPKIN_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "frost_pumpkin");
  public static final RegistryKey<ConfiguredFeature<?, ?>> FROST_PUMPKIN_CONFIGURED_KEY = configuredKeyOf(
      FROST_PUMPKIN_FEATURE_ID);
  public static final RegistryKey<PlacedFeature> FROST_PUMPKIN_PLACED_KEY = placedKeyOf(FROST_PUMPKIN_FEATURE_ID);

  public void register() {
    Registry.register(Registries.FEATURE, BERRY_BUSH_FEATURE_ID, BERRY_BUSH_FEATURE);
    Registry.register(Registries.FEATURE, VEGETATION_BLOCK_FEATURE_ID, VEGETATION_BLOCK_FEATURE);

    BiomeModifications.addFeature(
        BiomeSelectors.foundInOverworld(),
        GenerationStep.Feature.VEGETAL_DECORATION,
        BERRY_BUSH_PLACED_KEY);

    BiomeModifications.addFeature(
        BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.PALE_GARDEN, ModBiomes.CRIMSON_FOREST_BIOME_KEY),
        GenerationStep.Feature.VEGETAL_DECORATION,
        ARTENT_POTIONS_FLOWERS_FOREST_PLACED_KEY);
    BiomeModifications.addFeature(
        BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
        GenerationStep.Feature.VEGETAL_DECORATION,
        ARTENT_POTIONS_FLOWERS_PLAINS_PLACED_KEY);
    BiomeModifications.addFeature(
        BiomeSelectors.includeByKey(BiomeKeys.MEADOW),
        GenerationStep.Feature.VEGETAL_DECORATION,
        ARTENT_POTIONS_FLOWERS_MEADOW_PLACED_KEY);

    BiomeModifications.addFeature(
        BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP),
        GenerationStep.Feature.VEGETAL_DECORATION,
        SLIME_BERRY_PLACED_KEY);
    BiomeModifications.addFeature(
        BiomeSelectors.includeByKey(
            BiomeKeys.DARK_FOREST,
            BiomeKeys.FOREST,
            BiomeKeys.MUSHROOM_FIELDS,
            BiomeKeys.DRIPSTONE_CAVES,
            BiomeKeys.LUSH_CAVES,
            BiomeKeys.JUNGLE,
            BiomeKeys.TAIGA,
            BiomeKeys.SPARSE_JUNGLE), GenerationStep.Feature.VEGETAL_DECORATION, SHROOM_PLACED_KEY);
    BiomeModifications.addFeature(
        BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_TAIGA),
        GenerationStep.Feature.VEGETAL_DECORATION,
        FROST_PUMPKIN_PLACED_KEY);
  }

  private static RegistryKey<ConfiguredFeature<?, ?>> configuredKeyOf(Identifier id) {
    return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id);
  }

  private static RegistryKey<PlacedFeature> placedKeyOf(Identifier id) {
    return RegistryKey.of(RegistryKeys.PLACED_FEATURE, id);
  }
}
