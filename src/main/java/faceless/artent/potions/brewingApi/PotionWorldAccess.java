package faceless.artent.potions.brewingApi;

import faceless.artent.potions.BrewingAutomata;
import faceless.artent.potions.api.IPotionRecipesProvider;
import faceless.artent.potions.recipes.FermentationRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PotionWorldAccess {
  public static boolean isIngredient(World world, ItemStack stack) {
    var recipesProvider = getRecipesProvider(world);
    recipesProvider.artent$initRecipes();
    return recipesProvider.artent$isIngredient(stack);
  }

  @Nullable
  public static BrewingIngredient asIngredient(World world, ItemStack stack) {
    var recipesProvider = getRecipesProvider(world);
    recipesProvider.artent$initRecipes();
    return recipesProvider.artent$asIngredient(stack);
  }

  public static BrewingAutomata getBrewingAutomata(World world) {
    var recipesProvider = getRecipesProvider(world);
    recipesProvider.artent$initRecipes();
    return recipesProvider.artent$getBrewingAutomata();
  }

  public static BrewingIngredient ingredientFromIdentifier(World world, Identifier id) {
    var recipesProvider = getRecipesProvider(world);
    recipesProvider.artent$initRecipes();
    return recipesProvider.artent$ingredientFromIdentifier(id);
  }

  public static AlchemicalPotion potionFromIdentifier(World world, String id) {
    var recipesProvider = getRecipesProvider(world);
    recipesProvider.artent$initRecipes();
    return recipesProvider.artent$potionFromIdentifier(id);
  }

  public static FermentationRecipe getFermentationRecipe(World world, AlchemicalPotion source) {
    var recipesProvider = getRecipesProvider(world);
    recipesProvider.artent$initRecipes();
    return recipesProvider.artent$getFermentationRecipe(source);
  }

  private static IPotionRecipesProvider getRecipesProvider(World world) {
    return (IPotionRecipesProvider) world;
  }
}
