package faceless.artent.potions.objects;

import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.recipes.DryingRecipe;
import faceless.artent.potions.recipes.PotionEnhancementRecipe;
import faceless.artent.potions.recipes.PotionRecipe;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModRegistries {
  public static final RegistryKey<Registry<DryingRecipe>> DRYING_RECIPES_REGISTRY_KEY = RegistryKey.ofRegistry(
      Identifier.of("artent_drying_recipes"));

  public static final RegistryKey<Registry<BrewingIngredient>> POTION_INGREDIENT_REGISTRY_KEY = RegistryKey.ofRegistry(
      Identifier.of("artent_potion_ingredients"));
  public static final RegistryKey<Registry<AlchemicalPotion>> POTION_EFFECTS_REGISTRY_KEY = RegistryKey.ofRegistry(
      Identifier.of("artent_potion_effects"));
  public static final RegistryKey<Registry<PotionRecipe>> POTION_RECIPES_REGISTRY_KEY = RegistryKey.ofRegistry(
      Identifier.of("artent_potion_recipes"));
  public static final RegistryKey<Registry<PotionEnhancementRecipe>> POTION_ENHANCEMENT_RECIPE_REGISTRY_KEY = RegistryKey.ofRegistry(
      Identifier.of("artent_potion_enhancement_recipes"));

}
