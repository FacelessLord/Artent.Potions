package faceless.artent.potions.client.registry;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

import static faceless.artent.potions.client.registry.ConfiguredFeatureRegistry.EXAMPLE_FEATURE_CONFIGURED;
import static faceless.artent.potions.objects.ModFeatures.EXAMPLE_FEATURE_ID;

public class PlacedFeatureRegistry {
  public static PlacedFeature EXAMPLE_FEATURE_PLACED = new PlacedFeature(
      RegistryEntry.of(
          EXAMPLE_FEATURE_CONFIGURED
          //  the SquarePlacementModifier makes the feature generate a cluster of pillars each time
                      ), List.of(SquarePlacementModifier.of())
  );

  public static void bootstrap(Registerable<PlacedFeature> biomeRegisterable) {
    biomeRegisterable.register(keyOf(EXAMPLE_FEATURE_ID), EXAMPLE_FEATURE_PLACED);
  }


  private static RegistryKey<PlacedFeature> keyOf(Identifier id) {
    return RegistryKey.of(RegistryKeys.PLACED_FEATURE, id);
  }
}
