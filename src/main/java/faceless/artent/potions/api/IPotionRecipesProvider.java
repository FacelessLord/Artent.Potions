package faceless.artent.potions.api;

import faceless.artent.potions.BrewingAutomata;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public interface IPotionRecipesProvider {
  boolean artent$isIngredient(ItemStack stack);

  @Nullable
  BrewingIngredient artent$asIngredient(ItemStack stack);

  BrewingIngredient artent$ingredientFromIdentifier(Identifier identifier);

  BrewingAutomata artent$getBrewingAutomata();

  void artent$initRecipes();
}
