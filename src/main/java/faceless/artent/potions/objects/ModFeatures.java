package faceless.artent.potions.objects;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.features.BerryBushFeature;
import faceless.artent.potions.features.BerryBushFeatureConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
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

  public static final Identifier BERRY_BUSH_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "berry_bush");
  public static RegistryKey<ConfiguredFeature<?, ?>> BERRY_BUSH_CONFIGURED_KEY = configuredKeyOf(BERRY_BUSH_FEATURE_ID);
  public static RegistryKey<PlacedFeature> BERRY_BUSH_PLACED_KEY = placedKeyOf(BERRY_BUSH_FEATURE_ID);
  public static final Feature<BerryBushFeatureConfig> BERRY_BUSH_FEATURE = new BerryBushFeature(BerryBushFeatureConfig.CODEC);

  public void register() {
    Registry.register(Registries.FEATURE, BERRY_BUSH_FEATURE_ID, BERRY_BUSH_FEATURE);

    BiomeModifications.addFeature(
        BiomeSelectors.foundInOverworld(),
        // the feature is to be added while flowers and trees are being generated
        GenerationStep.Feature.VEGETAL_DECORATION,
        BERRY_BUSH_PLACED_KEY);
  }

  private static RegistryKey<ConfiguredFeature<?, ?>> configuredKeyOf(Identifier id) {
    return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id);
  }

  private static RegistryKey<PlacedFeature> placedKeyOf(Identifier id) {
    return RegistryKey.of(RegistryKeys.PLACED_FEATURE, id);
  }
}
