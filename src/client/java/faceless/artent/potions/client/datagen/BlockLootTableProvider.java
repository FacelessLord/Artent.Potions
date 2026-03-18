package faceless.artent.potions.client.datagen;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.DataWriter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {
  private final CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture;
  protected RegistryWrapper.WrapperLookup registries;

  public BlockLootTableProvider(
      FabricDataOutput output,
      CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup
  ) {
    super(output, registryLookup);
    this.registriesFuture = registryLookup;
  }

  public void generate() {
    RegistryWrapper.Impl<Enchantment> impl = this.registries.getOrThrow(
        RegistryKeys.ENCHANTMENT);
    RegistryWrapper.Impl<Item> impl2 =
        this.registries.getOrThrow(RegistryKeys.ITEM);
    addDrop(ModBlocks.BREWING_CAULDRON.block());
    addDrop(ModBlocks.BREWING_CAULDRON_COPPER.block());
    addDrop(ModBlocks.CRIMSONWOOD_LOG.block());
    addDrop(ModBlocks.CRIMSONWOOD_LOG_STRIPPED.block());
    addDrop(ModBlocks.CRIMSONWOOD_SAPLING.block());
    addDrop(ModBlocks.CRIMSONWOOD_PLANKS.block());
    addDrop(ModBlocks.CRIMSONWOOD_PLANKS_STAIRS.block());
    addDrop(
        ModBlocks.CRIMSONWOOD_PLANKS_SLAB.block(),
        LootTable
            .builder()
            .pool(LootPool
                      .builder()
                      .with(ItemEntry
                                .builder(ModBlocks.CRIMSONWOOD_PLANKS_SLAB.item())
                                .apply(SetCountLootFunction
                                           .builder(
                                               ConstantLootNumberProvider.create(
                                                   2), false
                                           )
                                           .conditionally(
                                               BlockStatePropertyLootCondition
                                                   .builder(ModBlocks.CRIMSONWOOD_PLANKS_SLAB.block())
                                                   .properties(StatePredicate.Builder
                                                                   .create()
                                                                   .exactMatch(
                                                                       SlabBlock.TYPE,
                                                                       SlabType.DOUBLE
                                                                   ))))))
            .randomSequenceId(Identifier.of(
                ArtentPotions.MODID,
                "blocks/crimsonwood_slab"
            ))
    );


    addDrop(ModBlocks.FROST_PUMPKIN_CARVED.block());
    addDrop(ModBlocks.FROST_PUMPKIN.block());
    addDrop(
        ModBlocks.FROST_PUMPKIN_STEM.block(),
        (Block block) -> this.cropStemDrops(
            block,
            ModItems.FROST_PUMPKIN_SEEDS
        )
    );
    addDrop(
        ModBlocks.FROST_PUMPKIN_STEM_ATTACHED.block(),
        (Block block) -> this.attachedCropStemDrops(
            block,
            ModItems.FROST_PUMPKIN_SEEDS
        )
    );
    addDrop(ModBlocks.SHROOM.block());
    addDrop(ModBlocks.SHADOWVEIL.block());
    addDrop(ModBlocks.BLAZING_MARIGOLD.block());
    addDrop(ModBlocks.SLIME_BERRY.block());
    addDrop(ModBlocks.DRAGORA.block());
    addDrop(
        ModBlocks.SHADOWVEIL_CROPS.block(),
        (Block block) -> this.cropStemDrops(block, ModItems.SHADOWVEIL_SEEDS)
    );
    addDrop(
        ModBlocks.BLAZING_MARIGOLD_CROPS.block(),
        (Block block) -> this.cropStemDrops(
            block,
            ModItems.BLAZING_MARIGOLD_SEEDS
        )
    );
    addDrop(
        ModBlocks.SLIME_BERRY_CROPS.block(),
        (Block block) -> this.cropStemDrops(
            block,
            ModItems.SLIME_BERRY_SEEDS
        )
    );
    addDrop(ModBlocks.FEMENTING_BARREL.block());
    addDrop(ModBlocks.DRYING_RACK.block());

    for (int i = 0; i < ModBlocks.MUSHROOM_INFO.length; i++) {
      addDrop(
          ModBlocks.MUSHROOM_INFO[i].mycelium(),
          (Block block) -> this
                               .dropsWithSilkTouch(block)
                               .pool(LootPool
                                         .builder()
                                         .rolls(ConstantLootNumberProvider.create(
                                             1.0F))
                                         .with(ItemEntry
                                                   .builder(Blocks.DIRT)
                                                   .conditionally(this.createWithoutSilkTouchCondition()))

                               )
      );
    }
    for (int i = 0; i < ModBlocks.BERRY_BUSH.length; i++) {
      addDrop(ModBlocks.BERRY_BUSH[i].block());
    }

    addDrop(
        ModBlocks.CRIMSONWOOD_LEAVES.block(),
        this
            .leavesDrops(
                ModBlocks.CRIMSONWOOD_LEAVES.block(),
                ModBlocks.CRIMSONWOOD_SAPLING.block(),
                0.05F,
                0.0625F,
                0.083333336F,
                0.1F
            )
            .pool(LootPool
                      .builder()
                      .rolls(ConstantLootNumberProvider.create(1.0F))
                      .conditionally(this.createWithoutShearsOrSilkTouchCondition())
                      .with((
                                (LeafEntry.Builder<?>) this.addSurvivesExplosionCondition(
                                    ModBlocks.CRIMSONWOOD_LEAVES.block(),
                                    ItemEntry.builder(ModItems.CRIMSON_LEAF)
                                )
                            ).conditionally(TableBonusLootCondition.builder(
                          impl.getOrThrow(Enchantments.FORTUNE),
                          0.005F,
                          0.0055555557F,
                          0.00625F,
                          0.008333334F,
                          0.025F
                      )))
                      .with((
                                (LeafEntry.Builder<?>) this.addSurvivesExplosionCondition(
                                    ModBlocks.CRIMSONWOOD_LEAVES.block(),
                                    ItemEntry.builder(ModItems.BERRIES[4])
                                )
                            ).conditionally(TableBonusLootCondition.builder(
                          impl.getOrThrow(Enchantments.FORTUNE),
                          0.0025F,
                          0.00255555557F,
                          0.003625F,
                          0.004333334F,
                          0.0125F
                      ))))
    );
    this.addDrop(
        ModBlocks.ICE_CRYSTAL_CLUSTER.block(),
        (Block block) -> this.dropsWithSilkTouch(
            block, (
                ItemEntry
                    .builder(ModItems.ICE_CRYSTAL_SHARD)
                    .apply(SetCountLootFunction.builder(
                        ConstantLootNumberProvider.create(4.0f)))
            )
                       .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(
                           Enchantments.FORTUNE)))
                       .conditionally(MatchToolLootCondition.builder(
                           ItemPredicate.Builder
                               .create()
                               .tag(impl2, ItemTags.CLUSTER_MAX_HARVESTABLES)))
                       .alternatively(this.applyExplosionDecay(
                           block,
                           ItemEntry
                               .builder(ModItems.ICE_CRYSTAL_SHARD)
                               .apply(SetCountLootFunction.builder(
                                   ConstantLootNumberProvider.create(2.0f)))
                       ))
        )
    );
    this.addDropWithSilkTouch(ModBlocks.ICE_CRYSTAL_BUD_SMALL.block());
    this.addDropWithSilkTouch(ModBlocks.ICE_CRYSTAL_BUD_MEDIUM.block());
    this.addDropWithSilkTouch(ModBlocks.ICE_CRYSTAL_BUD_LARGE.block());
    this.addDrop(ModBlocks.ICE_CRYSTAL_BLOCK.block());
  }

  @Override
  public CompletableFuture<?> run(DataWriter writer) {
    return this.registriesFuture.thenCompose((lookup) -> {
      this.registries = lookup;
      return super.run(writer);
    });
  }
}
