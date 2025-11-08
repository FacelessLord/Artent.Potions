package faceless.artent.potions.objects;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.recipes.DryingRecipe;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModRecipes {
  public static final RegistryKey<Registry<DryingRecipe>> DRYING_RECIPES_REGISTRY_KEY = RegistryKey.ofRegistry(
      Identifier.of("drying_recipes"));
  ;
}
