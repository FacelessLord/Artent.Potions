package faceless.artent.potions.objects;

import faceless.artent.potions.ArtentPotions;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModFeatures {
  public static final Identifier CRIMSON_TREE_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "crimson_tree");
  public static RegistryKey<ConfiguredFeature<?, ?>> CRIMSON_TREE_CONFIGURED_KEY = configuredKeyOf(CRIMSON_TREE_FEATURE_ID);
  public static RegistryKey<PlacedFeature> CRIMSON_TREE_PLACED_KEY = placedKeyOf(CRIMSON_TREE_FEATURE_ID);

  public static final Identifier CRIMSON_MEGA_TREE_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "crimson_mega_tree");
  public static RegistryKey<ConfiguredFeature<?, ?>> CRIMSON_MEGA_TREE_CONFIGURED_KEY = configuredKeyOf(CRIMSON_MEGA_TREE_FEATURE_ID);
  public static RegistryKey<PlacedFeature> CRIMSON_MEGA_TREE_PLACED_KEY = placedKeyOf(CRIMSON_MEGA_TREE_FEATURE_ID);

  public static final Identifier CRIMSON_TREES_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "crimson_trees");
  public static RegistryKey<ConfiguredFeature<?, ?>> CRIMSON_TREES_CONFIGURED_KEY = configuredKeyOf(CRIMSON_TREES_FEATURE_ID);
  public static RegistryKey<PlacedFeature> CRIMSON_TREES_PLACED_KEY = placedKeyOf(CRIMSON_TREES_FEATURE_ID);

  public void register() {
    // add it to overworld biomes using FAPI
    BiomeModifications.addFeature(
        BiomeSelectors.foundInOverworld(),
        // the feature is to be added while flowers and trees are being generated
        GenerationStep.Feature.VEGETAL_DECORATION,
        CRIMSON_TREE_PLACED_KEY);
    BiomeModifications.addFeature(
        BiomeSelectors.foundInOverworld(),
        // the feature is to be added while flowers and trees are being generated
        GenerationStep.Feature.VEGETAL_DECORATION,
        CRIMSON_MEGA_TREE_PLACED_KEY);
  }

  private static RegistryKey<ConfiguredFeature<?, ?>> configuredKeyOf(Identifier id) {
    return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id);
  }

  private static RegistryKey<PlacedFeature> placedKeyOf(Identifier id) {
    return RegistryKey.of(RegistryKeys.PLACED_FEATURE, id);
  }
}
