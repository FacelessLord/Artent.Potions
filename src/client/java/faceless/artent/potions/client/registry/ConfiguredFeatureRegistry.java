package faceless.artent.potions.client.registry;

import faceless.artent.potions.features.ExampleFeature;
import faceless.artent.potions.features.ExampleFeatureConfig;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import static faceless.artent.potions.objects.ModFeatures.EXAMPLE_FEATURE;
import static faceless.artent.potions.objects.ModFeatures.EXAMPLE_FEATURE_ID;

public class ConfiguredFeatureRegistry {

  public static final ConfiguredFeature<ExampleFeatureConfig, ExampleFeature> EXAMPLE_FEATURE_CONFIGURED = new ConfiguredFeature<>(
      EXAMPLE_FEATURE,
      new ExampleFeatureConfig(
          10,
          Identifier.of(
              "minecraft",
              "netherite_block")));

  public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> biomeRegisterable) {
    biomeRegisterable.register(keyOf(EXAMPLE_FEATURE_ID), EXAMPLE_FEATURE_CONFIGURED);
  }

  private static RegistryKey<ConfiguredFeature<?, ?>> keyOf(Identifier id) {
    return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id);
  }
}
