package faceless.artent.potions.client.datagen;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModBiomes;
import faceless.artent.potions.objects.ModFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.concurrent.CompletableFuture;

public class WorldgenProvider extends FabricDynamicRegistryProvider {
  public WorldgenProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
    final RegistryWrapper.Impl<ConfiguredFeature<?, ?>> configuredFeatureRegistry = registries.getOrThrow(RegistryKeys.CONFIGURED_FEATURE);
    final RegistryWrapper.Impl<PlacedFeature> placedFeatureRegistry = registries.getOrThrow(RegistryKeys.PLACED_FEATURE);
    final RegistryWrapper.Impl<Biome> biomeRegistry = registries.getOrThrow(RegistryKeys.BIOME);

    registerSimpleFeature(
        entries,
        configuredFeatureRegistry,
        placedFeatureRegistry,
        ModFeatures.CRIMSON_TREE_FEATURE_ID);
    registerSimpleFeature(
        entries,
        configuredFeatureRegistry,
        placedFeatureRegistry,
        ModFeatures.CRIMSON_MEGA_TREE_FEATURE_ID);
    registerSimpleFeature(
        entries,
        configuredFeatureRegistry,
        placedFeatureRegistry,
        ModFeatures.CRIMSON_TREES_FEATURE_ID);
    registerSimpleFeature(entries, configuredFeatureRegistry, placedFeatureRegistry, ModFeatures.BERRY_BUSH_FEATURE_ID);

    entries.add(biomeRegistry, ModBiomes.CRIMSON_FOREST_BIOME_KEY);
  }

  private void registerSimpleFeature(
      Entries entries,
      RegistryWrapper.Impl<ConfiguredFeature<?, ?>> configuredFeatureRegistry,
      RegistryWrapper.Impl<PlacedFeature> placedFeatureRegistry, Identifier featureId) {
    var configuredFeature = configuredFeatureRegistry.getOrThrow(RegistryKey.of(
        RegistryKeys.CONFIGURED_FEATURE,
        featureId));
    var placedFeature = placedFeatureRegistry.getOrThrow(RegistryKey.of(
        RegistryKeys.PLACED_FEATURE,
        featureId));
    entries.add(configuredFeature);
    entries.add(placedFeature);
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_worldgen";
  }
}
