package faceless.artent.potions.client.datagen;

import faceless.artent.potions.features.WorldGenContext;
import faceless.artent.potions.objects.ModFeatures;
import faceless.artent.potions.registry.DamageSourceRegistry;
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
    pack.addProvider(WorldGenProvider::new);
    pack.addProvider(BlockTagsProvider::new);
    pack.addProvider(DamageTagsProvider::new);
    pack.addProvider(ItemTagsProvider::new);
    pack.addProvider(BiomeTagsProvider::new);
    pack.addProvider(ArtentPotionsEntitiesLootTableProvider::new);
    pack.addProvider(ArtentPotionsBlockLootTableProvider::new);
    pack.addProvider(ArtentPotionsRecipeProvider::new);
  }

  @Override
  public void buildRegistry(RegistryBuilder registryBuilder) {
    registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, DamageSourceRegistry::bootstrap);
    this.aggregateRegistries(registryBuilder, ModFeatures::bootstrap);
  }

  public void aggregateRegistries(
      RegistryBuilder registryBuilder, Consumer<WorldGenContext> consumer) {
    var acceptor = new RegistryAcceptor(consumer);

    registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, acceptor::acceptConfiguredFeatures);
    registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, acceptor::acceptPlacedFeatures);
    registryBuilder.addRegistry(RegistryKeys.BIOME, acceptor::acceptBiomes);
    registryBuilder.addRegistry(RegistryKeys.CONFIGURED_CARVER, acceptor::acceptCarvers);
  }
}
