package faceless.artent.potions.client.datagen;

import faceless.artent.potions.client.registry.ConfiguredFeatureRegistry;
import faceless.artent.potions.client.registry.PlacedFeatureRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class ArtentPotionsDataGenerator implements DataGeneratorEntrypoint {
  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    pack.addProvider((FabricDataOutput output) -> new WorldgenProvider(output, fabricDataGenerator.getRegistries()));
  }

  @Override
  public void buildRegistry(RegistryBuilder registryBuilder) {
    registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ConfiguredFeatureRegistry::bootstrap);
    registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, PlacedFeatureRegistry::bootstrap);
  }
}
