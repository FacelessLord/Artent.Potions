package faceless.artent.potions.client.datagen;

import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataWriter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

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
    RegistryWrapper.Impl<Enchantment> impl = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);
    RegistryWrapper.Impl<Item> impl2 = this.registries.getOrThrow(RegistryKeys.ITEM);
    addDrop(ModBlocks.BrewingCauldron);
    addDrop(ModBlocks.BrewingCauldronCopper);
    addDrop(ModBlocks.CrimsonwoodLog);
    addDrop(ModBlocks.CrimsonwoodSapling);
    addDrop(ModBlocks.CrimsonwoodPlanks);
    addDrop(ModBlocks.FrostPumpkin);
    addDrop(ModBlocks.Shroom);
    addDrop(ModBlocks.Shadowveil);
    addDrop(ModBlocks.BlazingMarigold);
    addDrop(ModBlocks.SlimeBerry);
    addDrop(ModBlocks.FermentingBarrel);
    for (int i = 0; i < ModBlocks.berryBush.length; i++) {
      addDrop(ModBlocks.berryBush[i]);
    }

    addDrop(
        ModBlocks.CrimsonwoodLeaves, this
            .leavesDrops(
                ModBlocks.CrimsonwoodLeaves,
                ModBlocks.CrimsonwoodSapling,
                0.05F, 0.0625F, 0.083333336F, 0.1F)
            .pool(LootPool
                      .builder()
                      .rolls(ConstantLootNumberProvider.create(1.0F))
                      .conditionally(this.createWithoutShearsOrSilkTouchCondition())
                      .with(((LeafEntry.Builder<?>) this.addSurvivesExplosionCondition(
                          ModBlocks.CrimsonwoodLeaves, ItemEntry.builder(
                              ModItems.CrimsonLeaf))).conditionally(TableBonusLootCondition.builder(
                          impl.getOrThrow(Enchantments.FORTUNE),
                          0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F)))
                      .with(((LeafEntry.Builder<?>) this.addSurvivesExplosionCondition(
                          ModBlocks.CrimsonwoodLeaves, ItemEntry.builder(
                              ModItems.berries[4]))).conditionally(TableBonusLootCondition.builder(
                          impl.getOrThrow(Enchantments.FORTUNE),
                          0.0025F, 0.00255555557F, 0.003625F, 0.004333334F, 0.0125F)))));
    this.addDrop(
        ModBlocks.IceCrystalBud_Cluster, (Block block) -> this.dropsWithSilkTouch(
            block, ((LeafEntry.Builder) ItemEntry.builder(
                ModItems.IceCrystalShard).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4.0f))))
                .apply(
                    ApplyBonusLootFunction.oreDrops(((RegistryWrapper.Impl) impl).getOrThrow(Enchantments.FORTUNE)))
                .conditionally(
                    MatchToolLootCondition.builder(ItemPredicate.Builder
                                                       .create()
                                                       .tag(impl2, ItemTags.CLUSTER_MAX_HARVESTABLES)))
                .alternatively(
                    this.applyExplosionDecay(
                        block,
                        ItemEntry
                            .builder(ModItems.IceCrystalShard)
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)))))));
    this.addDropWithSilkTouch(ModBlocks.IceCrystalBud_Small);
    this.addDropWithSilkTouch(ModBlocks.IceCrystalBud_Medium);
    this.addDropWithSilkTouch(ModBlocks.IceCrystalBud_Large);
  }

  @Override
  public CompletableFuture<?> run(DataWriter writer) {
    return this.registriesFuture.thenCompose((lookup) -> {
      this.registries = lookup;
      return super.run(writer);
    });
  }
}
