package faceless.artent.potions.client.datagen;

import faceless.artent.potions.features.WorldGenContext;
import net.minecraft.registry.Registerable;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.function.Consumer;

public class RegistryAcceptor {
  private Registerable<ConfiguredFeature<?, ?>> configuredFeatureRegisterable;
  private Registerable<PlacedFeature> placedFeatureRegisterable;
  private Registerable<Biome> biomeRegisterable;
  private Registerable<ConfiguredCarver<?>> carverRegisterable;

  private final Consumer<WorldGenContext> consumer;

  public RegistryAcceptor(
      Consumer<WorldGenContext> consumer) {
    this.consumer = consumer;
  }

  public void acceptConfiguredFeatures(
      Registerable<ConfiguredFeature<?, ?>> configuredFeatureRegisterable) {
    this.configuredFeatureRegisterable = configuredFeatureRegisterable;
    runIfFilled(consumer);
  }

  public void acceptPlacedFeatures(
      Registerable<PlacedFeature> placedFeatureRegisterable) {
    this.placedFeatureRegisterable = placedFeatureRegisterable;
    runIfFilled(consumer);
  }

  public void acceptBiomes(
      Registerable<Biome> biomeRegisterable) {
    this.biomeRegisterable = biomeRegisterable;
    runIfFilled(consumer);
  }

  public void acceptCarvers(
      Registerable<ConfiguredCarver<?>> carverRegisterable) {
    this.carverRegisterable = carverRegisterable;
    runIfFilled(consumer);
  }

  public void runIfFilled(
      Consumer<WorldGenContext> consumer) {
    if (configuredFeatureRegisterable == null
        || placedFeatureRegisterable == null
        || biomeRegisterable == null
        || carverRegisterable == null) return;

    var ctx = new WorldGenContext(
        configuredFeatureRegisterable,
        placedFeatureRegisterable,
        biomeRegisterable,
        carverRegisterable
    );
    consumer.accept(ctx);
  }
}
