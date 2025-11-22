package faceless.artent.potions.mixin.world;

import faceless.artent.potions.BrewingAutomata;
import faceless.artent.potions.api.IPotionRecipesProvider;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.objects.ModRegistries;
import faceless.artent.potions.recipes.FermentationRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Hashtable;

@Mixin(World.class)
public abstract class PotionRegistriesWorldMixin implements IPotionRecipesProvider {

  @Shadow
  public abstract DynamicRegistryManager getRegistryManager();

  @Unique
  private boolean artent$recipesInited = false;
  @Unique
  private BrewingAutomata artent$brewingAutomata;
  @Unique
  public Hashtable<Item, BrewingIngredient> artent$ingredients = new Hashtable<>();
  @Unique
  public Hashtable<Identifier, BrewingIngredient> artent$ingredientsMap = new Hashtable<>();
  @Unique
  public Hashtable<String, AlchemicalPotion> artent$potionsMap = new Hashtable<>();
  @Unique
  public Hashtable<AlchemicalPotion, FermentationRecipe> artent$fermentationRecipes = new Hashtable<>();

  @Override
  public void artent$initRecipes() {
    if (artent$recipesInited) return;

    artent$brewingAutomata = new BrewingAutomata();

    var ingredientsRegistry = this.getRegistryManager().getOptional(ModRegistries.POTION_INGREDIENT_REGISTRY_KEY);
    if (ingredientsRegistry.isPresent()) {
      for (var ingredientEntry : ingredientsRegistry.get().getEntrySet()) {
        var ingredient = ingredientEntry.getValue();
        artent$ingredients.put(ingredient.item, ingredient);
        artent$ingredientsMap.put(ingredient.getIdentifier(), ingredient);
      }
    }
    var potionsRegistry = this.getRegistryManager().getOptional(ModRegistries.POTION_EFFECTS_REGISTRY_KEY);
    if (potionsRegistry.isPresent()) {
      for (var potionEntry : potionsRegistry.get().getEntrySet()) {
        var potion = potionEntry.getValue();
        artent$potionsMap.put(potion.getId(), potion);
      }
      AlchemicalPotionUtil.PotionsMap = artent$potionsMap;
    }
    var recipesRegistry = this.getRegistryManager().getOptional(ModRegistries.POTION_RECIPES_REGISTRY_KEY);

    if (recipesRegistry.isPresent()) {
      for (var recipeEntry : recipesRegistry.get().getEntrySet()) {
        var recipe = recipeEntry.getValue();
        artent$brewingAutomata.addRecipe(recipe.potion(), recipe.ingredients());
      }
    }
    var enhancementRecipesRegistry = this
        .getRegistryManager()
        .getOptional(ModRegistries.POTION_ENHANCEMENT_RECIPE_REGISTRY_KEY);

    if (enhancementRecipesRegistry.isPresent()) {
      for (var recipeEntry : enhancementRecipesRegistry.get().getEntrySet()) {
        var recipe = recipeEntry.getValue();
        artent$brewingAutomata.addEnhancement(recipe);
      }
    }

    var fermentationRecipesRegistry = this
        .getRegistryManager()
        .getOptional(ModRegistries.POTION_FERMENTATION_RECIPE_REGISTRY_KEY);
    if (fermentationRecipesRegistry.isPresent()) {
      for (var recipeEntry : fermentationRecipesRegistry.get().getEntrySet()) {
        var recipe = recipeEntry.getValue();
        artent$fermentationRecipes.put(recipe.source(), recipe);
      }
    }

    artent$recipesInited = true;
  }

  @Override
  public boolean artent$isIngredient(ItemStack stack) {
    return artent$ingredients.contains(stack.getItem());
  }

  @Override
  public @Nullable BrewingIngredient artent$asIngredient(ItemStack stack) {
    return artent$ingredients.getOrDefault(stack.getItem(), null);
  }

  @Override
  public AlchemicalPotion artent$potionFromIdentifier(String id) {
    return artent$potionsMap.getOrDefault(id, null);
  }

  @Override
  public BrewingIngredient artent$ingredientFromIdentifier(Identifier id) {
    return artent$ingredientsMap.getOrDefault(id, null);
  }

  @Override
  public BrewingAutomata artent$getBrewingAutomata() {
    return artent$brewingAutomata;
  }

  @Override
  public FermentationRecipe artent$getFermentationRecipe(AlchemicalPotion source) {
    return artent$fermentationRecipes.getOrDefault(source, null);
  }
}
