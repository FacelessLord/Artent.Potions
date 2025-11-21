package faceless.artent.potions.client.datagen.potions;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.objects.ModRegistries;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PotionIngredientsAndEffectsProvider extends FabricDynamicRegistryProvider {
  public PotionIngredientsAndEffectsProvider(
      FabricDataOutput output,
      CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
    final RegistryWrapper.Impl<BrewingIngredient> potionIngredientRegistry = registries.getOrThrow(ModRegistries.POTION_INGREDIENT_REGISTRY_KEY);
    potionIngredientRegistry.streamKeys().forEach(key -> entries.add(potionIngredientRegistry, key));
    final RegistryWrapper.Impl<AlchemicalPotion> potionEffectsRegistry = registries.getOrThrow(ModRegistries.POTION_EFFECTS_REGISTRY_KEY);
    potionEffectsRegistry.streamKeys().forEach(key -> entries.add(potionEffectsRegistry, key));
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_potions_and_ingredients";
  }
}
