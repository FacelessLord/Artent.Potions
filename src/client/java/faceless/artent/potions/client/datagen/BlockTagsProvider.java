package faceless.artent.potions.client.datagen;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagsProvider extends FabricTagProvider<Block> {
  public BlockTagsProvider(
      FabricDataOutput output,
      CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
  ) {
    super(output, RegistryKeys.BLOCK, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries) {
    getOrCreateTagBuilder(BlockTags.LOGS)
        .add(ModBlocks.CRIMSONWOOD_LOG.block());
    getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
        .add(ModBlocks.CRIMSONWOOD_LOG.block());

    getOrCreateTagBuilder(BlockTags.LOGS)
        .add(ModBlocks.CRIMSONWOOD_LOG_STRIPPED.block());
    getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
        .add(ModBlocks.CRIMSONWOOD_LOG_STRIPPED.block());
    getOrCreateTagBuilder(BlockTags.WOODEN_SLABS)

        .add(ModBlocks.CRIMSONWOOD_PLANKS_SLAB.block());
    getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS)
        .add(ModBlocks.CRIMSONWOOD_PLANKS_STAIRS.block());

    getOrCreateTagBuilder(BlockTags.LEAVES)
        .add(ModBlocks.CRIMSONWOOD_LEAVES.block());

    getOrCreateTagBuilder(BlockTags.SAPLINGS)
        .add(ModBlocks.CRIMSONWOOD_SAPLING.block());

    getOrCreateTagBuilder(BlockTags.PLANKS)
        .add(ModBlocks.CRIMSONWOOD_PLANKS.block());

    getOrCreateTagBuilder(BlockTags.SCULK_REPLACEABLE)
        .add(ModBlocks.CRIMSONWOOD_LOG.block())
        .add(ModBlocks.CRIMSONWOOD_LOG_STRIPPED.block())
        .add(ModBlocks.CRIMSONWOOD_PLANKS.block())
        .add(ModBlocks.CRIMSONWOOD_PLANKS_SLAB.block())
        .add(ModBlocks.CRIMSONWOOD_PLANKS_STAIRS.block())
        .add(ModBlocks.CRIMSONWOOD_SAPLING.block())
        .add(ModBlocks.CRIMSONWOOD_LEAVES.block());

    getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
        .add(ModBlocks.BREWING_CAULDRON.block())
        .add(ModBlocks.BREWING_CAULDRON_COPPER.block());
    getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
        .add(ModBlocks.BREWING_CAULDRON.block())
        .add(ModBlocks.BREWING_CAULDRON_COPPER.block());

    getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
        .add(ModBlocks.CRIMSONWOOD_LOG.block())
        .add(ModBlocks.CRIMSONWOOD_PLANKS.block())
        .add(ModBlocks.CRIMSONWOOD_PLANKS_SLAB.block())
        .add(ModBlocks.CRIMSONWOOD_PLANKS_STAIRS.block())
        .add(ModBlocks.CRIMSONWOOD_SAPLING.block())
        .add(ModBlocks.FROST_PUMPKIN_CARVED.block())
        .add(ModBlocks.FROST_PUMPKIN.block())
        .add(ModBlocks.FEMENTING_BARREL.block())
        .add(ModBlocks.DRYING_RACK.block())
        .add(ModBlocks.BERRY_BUSH[0].block())
        .add(ModBlocks.BERRY_BUSH[1].block())
        .add(ModBlocks.BERRY_BUSH[2].block())
        .add(ModBlocks.BERRY_BUSH[3].block());
    getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
        .add(ModBlocks.ICE_CRYSTAL_BLOCK.block())
        .add(ModBlocks.ICE_CRYSTAL_BUD_SMALL.block())
        .add(ModBlocks.ICE_CRYSTAL_BUD_MEDIUM.block())
        .add(ModBlocks.ICE_CRYSTAL_BUD_LARGE.block())
        .add(ModBlocks.ICE_CRYSTAL_CLUSTER.block())
        .add(ModBlocks.BREWING_CAULDRON.block())
        .add(ModBlocks.BREWING_CAULDRON_COPPER.block());
    getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
        .add(ModBlocks.CRIMSONWOOD_LEAVES.block());
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_block_tags";
  }
}
