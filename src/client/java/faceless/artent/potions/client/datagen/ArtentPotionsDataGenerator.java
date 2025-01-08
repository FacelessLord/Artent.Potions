package faceless.artent.potions.client.datagen;

import faceless.artent.potions.features.WorldGenContext;
import faceless.artent.potions.registry.FeatureRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Consumer;

public class ArtentPotionsDataGenerator implements DataGeneratorEntrypoint {
  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    pack.addProvider((FabricDataOutput output) -> new WorldgenProvider(output, fabricDataGenerator.getRegistries()));
    pack.addProvider((FabricDataOutput output) -> new BlockTagsProvider(output, fabricDataGenerator.getRegistries()));
    pack.addProvider((FabricDataOutput output) -> new ItemTagsProvider(output, fabricDataGenerator.getRegistries()));
    pack.addProvider((FabricDataOutput output) -> new BiomeTagsProvider(output, fabricDataGenerator.getRegistries()));
  }

  @Override
  public void buildRegistry(RegistryBuilder registryBuilder) {
    this.aggregateRegistries(registryBuilder, FeatureRegistry::bootstrap);
  }

  public void aggregateRegistries(
      RegistryBuilder registryBuilder,
      Consumer<WorldGenContext> consumer) {
    var acceptor = new RegistryAcceptor(consumer);

    registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, acceptor::acceptConfiguredFeatures);
    registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, acceptor::acceptPlacedFeatures);
    registryBuilder.addRegistry(RegistryKeys.BIOME, acceptor::acceptBiomes);
    registryBuilder.addRegistry(RegistryKeys.CONFIGURED_CARVER, acceptor::acceptCarvers);
  }
}
