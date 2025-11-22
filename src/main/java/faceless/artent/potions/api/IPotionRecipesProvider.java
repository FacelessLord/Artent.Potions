package faceless.artent.potions.api;

import faceless.artent.potions.BrewingAutomata;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.recipes.FermentationRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public interface IPotionRecipesProvider {
  boolean artent$isIngredient(ItemStack stack);

  @Nullable
  BrewingIngredient artent$asIngredient(ItemStack stack);

  AlchemicalPotion artent$potionFromIdentifier(String identifier);

  BrewingIngredient artent$ingredientFromIdentifier(Identifier identifier);

  BrewingAutomata artent$getBrewingAutomata();

  FermentationRecipe artent$getFermentationRecipe(AlchemicalPotion source);

  void artent$initRecipes();
}
