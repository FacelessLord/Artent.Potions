package faceless.artent.potions.client.datagen;

import faceless.artent.potions.bootstrap.*;
import faceless.artent.potions.client.datagen.potions.PotionRecipesProvider;
import faceless.artent.potions.features.WorldGenContext;
import faceless.artent.potions.objects.ModFeatures;
import faceless.artent.potions.objects.ModRegistries;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Consumer;

public class ArtentPotionsDataGenerator implements DataGeneratorEntrypoint {
  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    pack.addProvider(DamageProvider::new);
    pack.addProvider(DryingRecipeProvider::new);
    pack.addProvider(PotionRecipesProvider::new);
    pack.addProvider(WorldGenProvider::new);
    pack.addProvider(BlockTagsProvider::new);
    pack.addProvider(DamageTagsProvider::new);
    pack.addProvider(ItemTagsProvider::new);
    pack.addProvider(BiomeTagsProvider::new);
    pack.addProvider(EntitiesLootTableProvider::new);
    pack.addProvider(BlockLootTableProvider::new);
    pack.addProvider(CraftingRecipeProvider::new);
  }

  @Override
  public void buildRegistry(RegistryBuilder registryBuilder) {
    registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, DamageSourceRegistry::bootstrap);
    registryBuilder.addRegistry(ModRegistries.DRYING_RECIPES_REGISTRY_KEY, DryingRecipeRegistry::bootstrap);
    registryBuilder.addRegistry(ModRegistries.POTION_INGREDIENT_REGISTRY_KEY, BrewingIngredientsBootstrap::bootstrap);
    registryBuilder.addRegistry(ModRegistries.POTION_EFFECTS_REGISTRY_KEY, PotionEffectsBootstrap::bootstrap);

    registryBuilder.addRegistry(ModRegistries.POTION_RECIPES_REGISTRY_KEY, PotionRecipeBootstrap::bootstrap);
    registryBuilder.addRegistry(
        ModRegistries.POTION_ENHANCEMENT_RECIPE_REGISTRY_KEY,
        PotionEnhancementRecipeBootstrap::bootstrap);

    registryBuilder.addRegistry(ModRegistries.POTION_FERMENTATION_RECIPE_REGISTRY_KEY, FermentationRecipeBootstrap::bootstrap);

    this.aggregateRegistries(registryBuilder, ModFeatures::bootstrap);
  }

  public void aggregateRegistries(RegistryBuilder registryBuilder, Consumer<WorldGenContext> consumer) {
    var acceptor = new RegistryAcceptor(consumer);

    registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, acceptor::acceptConfiguredFeatures);
    registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, acceptor::acceptPlacedFeatures);
    registryBuilder.addRegistry(RegistryKeys.BIOME, acceptor::acceptBiomes);
    registryBuilder.addRegistry(RegistryKeys.CONFIGURED_CARVER, acceptor::acceptCarvers);
  }
}
