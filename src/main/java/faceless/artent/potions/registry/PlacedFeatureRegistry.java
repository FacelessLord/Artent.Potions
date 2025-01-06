package faceless.artent.potions.registry;

import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModFeatures;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;


public class PlacedFeatureRegistry {
  public static PlacedFeature CRIMSONWOOD_TREE_PLACED = new PlacedFeature(
      RegistryEntry.of(ConfiguredFeatureRegistry.CRIMSON_TREE
                       //  the SquarePlacementModifier makes the feature generate a cluster of pillars each time
                      ),
      VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
          PlacedFeatures.createCountExtraModifier(
              10,
              0.1F,
              1),
          ModBlocks.CrimsonwoodSapling));

  public static void bootstrap(Registerable<PlacedFeature> biomeRegisterable) {
    biomeRegisterable.register(ModFeatures.CRIMSON_TREE_PLACED_KEY, CRIMSONWOOD_TREE_PLACED);
  }


  private static RegistryKey<PlacedFeature> keyOf(Identifier id) {
    return RegistryKey.of(RegistryKeys.PLACED_FEATURE, id);
  }
}
