package faceless.artent.potions.client.datagen.potions;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.objects.ModRegistries;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PotionIngredientProvider extends FabricDynamicRegistryProvider {
  public PotionIngredientProvider(
      FabricDataOutput output,
      CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
    final RegistryWrapper.Impl<BrewingIngredient> dryingRecipeRegistry = registries.getOrThrow(ModRegistries.POTION_INGREDIENT_REGISTRY_KEY);
    dryingRecipeRegistry.streamKeys().forEach(key -> entries.add(dryingRecipeRegistry, key));
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_brewing_ingredients";
  }
}
