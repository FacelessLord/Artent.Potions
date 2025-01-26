package faceless.artent.potions.registry;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.features.BerryBushFeature;
import faceless.artent.potions.features.BerryBushFeatureConfig;
import faceless.artent.potions.features.VegetationBlockFeature;
import faceless.artent.potions.features.VegetationBlockFeatureConfig;
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
  public static RegistryKey<ConfiguredFeature<?, ?>> CRIMSON_TREE_CONFIGURED_KEY = configuredKeyOf(CRIMSON_TREE_FEATURE_ID);
  public static RegistryKey<PlacedFeature> CRIMSON_TREE_PLACED_KEY = placedKeyOf(CRIMSON_TREE_FEATURE_ID);

  public static final Identifier CRIMSON_MEGA_TREE_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "crimson_mega_tree");
  public static RegistryKey<ConfiguredFeature<?, ?>> CRIMSON_MEGA_TREE_CONFIGURED_KEY = configuredKeyOf(CRIMSON_MEGA_TREE_FEATURE_ID);
  public static RegistryKey<PlacedFeature> CRIMSON_MEGA_TREE_PLACED_KEY = placedKeyOf(CRIMSON_MEGA_TREE_FEATURE_ID);

  public static final Identifier CRIMSON_TREES_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "crimson_trees");
  public static RegistryKey<ConfiguredFeature<?, ?>> CRIMSON_TREES_CONFIGURED_KEY = configuredKeyOf(CRIMSON_TREES_FEATURE_ID);
  public static RegistryKey<PlacedFeature> CRIMSON_TREES_PLACED_KEY = placedKeyOf(CRIMSON_TREES_FEATURE_ID);

  public static final Identifier BERRY_BUSH_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "berry_bush");
  public static RegistryKey<ConfiguredFeature<?, ?>> BERRY_BUSH_CONFIGURED_KEY = configuredKeyOf(BERRY_BUSH_FEATURE_ID);
  public static RegistryKey<PlacedFeature> BERRY_BUSH_PLACED_KEY = placedKeyOf(BERRY_BUSH_FEATURE_ID);
  public static final Feature<BerryBushFeatureConfig> BERRY_BUSH_FEATURE = new BerryBushFeature(BerryBushFeatureConfig.CODEC);

  public static final Identifier VEGETATION_BLOCK_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "vegetation_bock");
  public static final Feature<VegetationBlockFeatureConfig> VEGETATION_BLOCK_FEATURE = new VegetationBlockFeature(VegetationBlockFeatureConfig.CODEC);

  public static final Identifier SHADOWVEIL_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "demonveil");
  public static RegistryKey<ConfiguredFeature<?, ?>> SHADOWVEIL_CONFIGURED_KEY = configuredKeyOf(SHADOWVEIL_FEATURE_ID);
  public static RegistryKey<PlacedFeature> SHADOWVEIL_PLACED_KEY = placedKeyOf(SHADOWVEIL_FEATURE_ID);

  public static final Identifier SHROOM_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "shroom");
  public static RegistryKey<ConfiguredFeature<?, ?>> SHROOM_CONFIGURED_KEY = configuredKeyOf(SHROOM_FEATURE_ID);
  public static RegistryKey<PlacedFeature> SHROOM_PLACED_KEY = placedKeyOf(SHROOM_FEATURE_ID);

  public static final Identifier FROST_PUMPKIN_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "frost_pumpkin");
  public static RegistryKey<ConfiguredFeature<?, ?>> FROST_PUMPKIN_CONFIGURED_KEY = configuredKeyOf(FROST_PUMPKIN_FEATURE_ID);
  public static RegistryKey<PlacedFeature> FROST_PUMPKIN_PLACED_KEY = placedKeyOf(FROST_PUMPKIN_FEATURE_ID);

  public void register() {
    Registry.register(Registries.FEATURE, BERRY_BUSH_FEATURE_ID, BERRY_BUSH_FEATURE);
    Registry.register(Registries.FEATURE, VEGETATION_BLOCK_FEATURE_ID, VEGETATION_BLOCK_FEATURE);

    BiomeModifications.addFeature(
        BiomeSelectors.foundInOverworld(),
        GenerationStep.Feature.VEGETAL_DECORATION,
        BERRY_BUSH_PLACED_KEY);

    BiomeModifications.addFeature(
        BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.DARK_FOREST, BiomeKeys.PALE_GARDEN),
        GenerationStep.Feature.VEGETAL_DECORATION,
        SHADOWVEIL_PLACED_KEY);
    BiomeModifications.addFeature(
        BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST),
        GenerationStep.Feature.VEGETAL_DECORATION,
        SHROOM_PLACED_KEY);
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
