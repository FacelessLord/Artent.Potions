package faceless.artent.potions.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.concurrent.CompletableFuture;

import static faceless.artent.potions.objects.ModFeatures.EXAMPLE_FEATURE_ID;

public class WorldgenProvider extends FabricDynamicRegistryProvider {
  public WorldgenProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
    final RegistryWrapper.Impl<ConfiguredFeature<?, ?>> configuredFeatureRegistry = registries.getOrThrow(RegistryKeys.CONFIGURED_FEATURE);
    final RegistryWrapper.Impl<PlacedFeature> placedFeatureRegistry = registries.getOrThrow(RegistryKeys.PLACED_FEATURE);

    var configuredFeature = configuredFeatureRegistry.getOrThrow(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, EXAMPLE_FEATURE_ID));
    var placedFeature = placedFeatureRegistry.getOrThrow(RegistryKey.of(RegistryKeys.PLACED_FEATURE, EXAMPLE_FEATURE_ID));
    entries.add(configuredFeature);
    entries.add(placedFeature);
  }

  @Override
  public String getName() {
    return "";
  }
}