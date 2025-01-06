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
  public BlockTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, RegistryKeys.BLOCK, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries) {
    getOrCreateTagBuilder(BlockTags.LOGS)
        .add(ModBlocks.CrimsonwoodLog);
    getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
        .add(ModBlocks.CrimsonwoodLog);

    getOrCreateTagBuilder(BlockTags.LEAVES)
        .add(ModBlocks.CrimsonwoodLeaves);

    getOrCreateTagBuilder(BlockTags.SAPLINGS)
        .add(ModBlocks.CrimsonwoodSapling);

    getOrCreateTagBuilder(BlockTags.PLANKS)
        .add(ModBlocks.CrimsonwoodPlanks);

    getOrCreateTagBuilder(BlockTags.SCULK_REPLACEABLE)
        .add(ModBlocks.CrimsonwoodLog)
        .add(ModBlocks.CrimsonwoodPlanks)
        .add(ModBlocks.CrimsonwoodSapling)
        .add(ModBlocks.CrimsonwoodLeaves);
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_block_tags";
  }
}
