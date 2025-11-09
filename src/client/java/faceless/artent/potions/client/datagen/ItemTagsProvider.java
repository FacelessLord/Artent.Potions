package faceless.artent.potions.client.datagen;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagsProvider extends FabricTagProvider<Item> {
  public ItemTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, RegistryKeys.ITEM, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries) {
    getOrCreateTagBuilder(ItemTags.LOGS)
        .add(ModBlocks.CRIMSONWOOD_LOG.item());
    getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
        .add(ModBlocks.CRIMSONWOOD_LOG.item());

    getOrCreateTagBuilder(ItemTags.LEAVES)
        .add(ModBlocks.CRIMSONWOOD_LEAVES.item());

    getOrCreateTagBuilder(ItemTags.SAPLINGS)
        .add(ModBlocks.CRIMSONWOOD_SAPLING.item());

    getOrCreateTagBuilder(ItemTags.PLANKS)
        .add(ModBlocks.CRIMSONWOOD_PLANKS.item());
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_item_tags";
  }
}
