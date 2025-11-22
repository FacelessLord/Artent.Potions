package faceless.artent.potions.client.datagen.potions;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.objects.ModRegistries;
import faceless.artent.potions.recipes.FermentationRecipe;
import faceless.artent.potions.recipes.PotionEnhancementRecipe;
import faceless.artent.potions.recipes.PotionRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PotionRecipesProvider extends FabricDynamicRegistryProvider {
  public PotionRecipesProvider(
      FabricDataOutput output,
      CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
    final RegistryWrapper.Impl<BrewingIngredient> dryingRecipeRegistry = registries.getOrThrow(ModRegistries.POTION_INGREDIENT_REGISTRY_KEY);
    dryingRecipeRegistry.streamKeys().forEach(key -> entries.add(dryingRecipeRegistry, key));

    final RegistryWrapper.Impl<AlchemicalPotion> potionEffectsRegistry = registries.getOrThrow(ModRegistries.POTION_EFFECTS_REGISTRY_KEY);
    potionEffectsRegistry.streamKeys().forEach(key -> entries.add(potionEffectsRegistry, key));

    final RegistryWrapper.Impl<PotionRecipe> potionRecipeRegistry = registries.getOrThrow(ModRegistries.POTION_RECIPES_REGISTRY_KEY);
    potionRecipeRegistry.streamKeys().forEach(key -> entries.add(potionRecipeRegistry, key));

    final RegistryWrapper.Impl<PotionEnhancementRecipe> potionEnhancementRegistry = registries.getOrThrow(ModRegistries.POTION_ENHANCEMENT_RECIPE_REGISTRY_KEY);
    potionEnhancementRegistry.streamKeys().forEach(key -> entries.add(potionEnhancementRegistry, key));

    final RegistryWrapper.Impl<FermentationRecipe> potionFermentationRegistry = registries.getOrThrow(ModRegistries.POTION_FERMENTATION_RECIPE_REGISTRY_KEY);
    potionFermentationRegistry.streamKeys().forEach(key -> entries.add(potionFermentationRegistry, key));
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_potion_recipes";
  }
}
