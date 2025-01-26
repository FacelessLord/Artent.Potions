package faceless.artent.potions.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ArtentPotionsBlockLootTableProvider extends FabricBlockLootTableProvider {
  private final CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture;
  protected RegistryWrapper.WrapperLookup registries;

  public ArtentPotionsBlockLootTableProvider(
      FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(output, registryLookup);
    this.registriesFuture = registryLookup;
  }

  public void generate() {

  }

  @Override
  public CompletableFuture<?> run(DataWriter writer) {
    return this.registriesFuture.thenCompose((lookup) -> {
      this.registries = lookup;
      return super.run(writer);
    });
  }
}
