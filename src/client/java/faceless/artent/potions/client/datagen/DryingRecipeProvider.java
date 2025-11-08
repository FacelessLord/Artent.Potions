package faceless.artent.potions.client.datagen;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModRecipes;
import faceless.artent.potions.recipes.DryingRecipe;
import faceless.artent.potions.registry.DamageSourceRegistry;
import faceless.artent.potions.registry.DryingRecipeRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class DryingRecipeProvider extends FabricDynamicRegistryProvider {
  public DryingRecipeProvider(
      FabricDataOutput output,
      CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
    final RegistryWrapper.Impl<DryingRecipe> dryingRecipeRegistry = registries.getOrThrow(ModRecipes.DRYING_RECIPES_REGISTRY_KEY);
    dryingRecipeRegistry.streamKeys().forEach(key -> entries.add(dryingRecipeRegistry, key));
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_drying_recipes";
  }
}
