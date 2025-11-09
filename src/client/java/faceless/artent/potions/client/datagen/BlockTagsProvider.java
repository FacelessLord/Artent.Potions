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
        .add(ModBlocks.CrimsonwoodLog.block());
    getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
        .add(ModBlocks.CrimsonwoodLog.block());

    getOrCreateTagBuilder(BlockTags.LEAVES)
        .add(ModBlocks.CrimsonwoodLeaves.block());

    getOrCreateTagBuilder(BlockTags.SAPLINGS)
        .add(ModBlocks.CrimsonwoodSapling.block());

    getOrCreateTagBuilder(BlockTags.PLANKS)
        .add(ModBlocks.CrimsonwoodPlanks.block());

    getOrCreateTagBuilder(BlockTags.SCULK_REPLACEABLE)
        .add(ModBlocks.CrimsonwoodLog.block())
        .add(ModBlocks.CrimsonwoodPlanks.block())
        .add(ModBlocks.CrimsonwoodSapling.block())
        .add(ModBlocks.CrimsonwoodLeaves.block());

    getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
        .add(ModBlocks.BrewingCauldron.block())
        .add(ModBlocks.BrewingCauldronCopper.block());
    getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
        .add(ModBlocks.BrewingCauldron.block())
        .add(ModBlocks.BrewingCauldronCopper.block());

    getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
        .add(ModBlocks.CrimsonwoodLog.block())
        .add(ModBlocks.CrimsonwoodPlanks.block())
        .add(ModBlocks.CrimsonwoodSapling.block())
        .add(ModBlocks.FrostPumpkinCarved.block())
        .add(ModBlocks.FrostPumpkin.block())
        .add(ModBlocks.FermentingBarrel.block())
        .add(ModBlocks.berryBush[0].block())
        .add(ModBlocks.berryBush[1].block())
        .add(ModBlocks.berryBush[2].block())
        .add(ModBlocks.berryBush[3].block());
    getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
        .add(ModBlocks.CrimsonwoodLeaves.block());
  }

  @Override
  public String getName() {
    return ArtentPotions.MODID + "_block_tags";
  }
}
