package faceless.artent.potions.client.datagen;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
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

    var configuredFeature = configuredFeatureRegistry.getOrThrow(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, ModFeatures.CRIMSON_TREE_FEATURE_ID));
    var placedFeature = placedFeatureRegistry.getOrThrow(RegistryKey.of(RegistryKeys.PLACED_FEATURE, ModFeatures.CRIMSON_TREE_FEATURE_ID));
    entries.add(configuredFeature);
    entries.add(placedFeature);

    configuredFeature = configuredFeatureRegistry.getOrThrow(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, ModFeatures.CRIMSON_MEGA_TREE_FEATURE_ID));
    placedFeature = placedFeatureRegistry.getOrThrow(RegistryKey.of(RegistryKeys.PLACED_FEATURE, ModFeatures.CRIMSON_MEGA_TREE_FEATURE_ID));
    entries.add(configuredFeature);
    entries.add(placedFeature);

    configuredFeature = configuredFeatureRegistry.getOrThrow(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, ModFeatures.CRIMSON_TREES_FEATURE_ID));
    placedFeature = placedFeatureRegistry.getOrThrow(RegistryKey.of(RegistryKeys.PLACED_FEATURE, ModFeatures.CRIMSON_TREES_FEATURE_ID));
    entries.add(configuredFeature);
    entries.add(placedFeature);
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_worldgen";
  }
}
