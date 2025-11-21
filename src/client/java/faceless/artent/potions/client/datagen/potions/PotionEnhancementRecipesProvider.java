package faceless.artent.potions.client.datagen.potions;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModRegistries;
import faceless.artent.potions.recipes.PotionEnhancementRecipe;
import faceless.artent.potions.recipes.PotionRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PotionEnhancementRecipesProvider extends FabricDynamicRegistryProvider {
  public PotionEnhancementRecipesProvider(
      FabricDataOutput output,
      CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
    final RegistryWrapper.Impl<PotionEnhancementRecipe> dryingRecipeRegistry = registries.getOrThrow(ModRegistries.POTION_ENHANCEMENT_RECIPE_REGISTRY_KEY);
    dryingRecipeRegistry.streamKeys().forEach(key -> entries.add(dryingRecipeRegistry, key));
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_potion_enhancement_recipes";
  }
}
