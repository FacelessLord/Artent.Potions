package faceless.artent.potions.features;

import net.minecraft.registry.Registerable;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

public record WorldGenContext(
    Registerable<ConfiguredFeature<?, ?>> configuredFeatures,
    Registerable<PlacedFeature> placedFeatures,
    Registerable<Biome> biomes,
    Registerable<ConfiguredCarver<?>> carvers
) {
}
