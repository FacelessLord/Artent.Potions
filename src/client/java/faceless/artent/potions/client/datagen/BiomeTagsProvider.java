package faceless.artent.potions.client.datagen;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModBiomes;
import faceless.artent.potions.objects.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class BiomeTagsProvider extends FabricTagProvider<Biome> {
  public BiomeTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, RegistryKeys.BIOME, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries) {
    getOrCreateTagBuilder(BiomeTags.IS_FOREST)
        .add(ModBiomes.CRIMSON_FOREST_BIOME_KEY);
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_biome_tags";
  }
}
