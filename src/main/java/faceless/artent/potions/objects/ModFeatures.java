package faceless.artent.potions.objects;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.features.ExampleFeature;
import faceless.artent.potions.features.ExampleFeatureConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ModFeatures {
  public static final Identifier EXAMPLE_FEATURE_ID = Identifier.of(ArtentPotions.MODID, "example_feature");
  public static final ExampleFeature EXAMPLE_FEATURE = new ExampleFeature(ExampleFeatureConfig.CODEC);


  public void register() {
    Registry.register(Registries.FEATURE, EXAMPLE_FEATURE_ID, EXAMPLE_FEATURE);
//    var registryLookup = BuiltinRegistries.createWrapperLookup();
//    var configuredFeatureRegistry = BuiltinRegistries.createWrapperLookup().getOrThrow(RegistryKeys.CONFIGURED_FEATURE);
//
//    registryLookup.getOrThrow(RegistryKeys.PLACED_FEATURE);
//    Registry.register(configuredFeatureRegistry., EXAMPLE_FEATURE_ID, EXAMPLE_FEATURE_CONFIGURED);


//    var placedFeatureRegistry = dynamicRegistryManager.getOptional();
//    Registry.register(
//        placedFeatureRegistry.get(),
//        EXAMPLE_FEATURE_ID,
//        EXAMPLE_FEATURE_PLACED);

    // add it to overworld biomes using FAPI
    BiomeModifications.addFeature(
        BiomeSelectors.foundInOverworld(),
        // the feature is to be added while flowers and trees are being generated
        GenerationStep.Feature.VEGETAL_DECORATION,
        RegistryKey.of(RegistryKeys.PLACED_FEATURE, EXAMPLE_FEATURE_ID));
  }

  private static RegistryKey<ConfiguredFeature<?, ?>> keyOf(Identifier id) {
    return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id);
  }
}
