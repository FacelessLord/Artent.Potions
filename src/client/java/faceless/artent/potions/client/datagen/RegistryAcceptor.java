package faceless.artent.potions.client.datagen;

import net.minecraft.registry.Registerable;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.function.BiConsumer;

public class RegistryAcceptor {
  private Registerable<ConfiguredFeature<?, ?>> configuredFeatureRegisterable;
  private Registerable<PlacedFeature> placedFeatureRegisterable;
  private BiConsumer<Registerable<ConfiguredFeature<?, ?>>, Registerable<PlacedFeature>> consumer;

  public RegistryAcceptor(
      BiConsumer<Registerable<ConfiguredFeature<?, ?>>, Registerable<PlacedFeature>> consumer) {
    this.consumer = consumer;
  }

  public void acceptConfiguredFeatureRegisterable(
      Registerable<ConfiguredFeature<?, ?>> configuredFeatureRegisterable) {
    this.configuredFeatureRegisterable = configuredFeatureRegisterable;
    runIfFilled(consumer);
  }

  public void acceptPlacedFeatureRegisterable(
      Registerable<PlacedFeature> placedFeatureRegisterable) {
    this.placedFeatureRegisterable = placedFeatureRegisterable;
    runIfFilled(consumer);
  }

  public void runIfFilled(
      BiConsumer<Registerable<ConfiguredFeature<?, ?>>, Registerable<PlacedFeature>> consumer) {
    if (configuredFeatureRegisterable == null || placedFeatureRegisterable == null) return;

    consumer.accept(this.configuredFeatureRegisterable, this.placedFeatureRegisterable);
  }
}
