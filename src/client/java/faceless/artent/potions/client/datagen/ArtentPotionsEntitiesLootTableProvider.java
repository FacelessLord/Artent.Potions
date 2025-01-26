package faceless.artent.potions.client.datagen;

import faceless.artent.potions.objects.ModEntities;
import faceless.artent.potions.objects.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.function.FurnaceSmeltLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ArtentPotionsEntitiesLootTableProvider extends ArtentPotionsBaseLootTableProvider {

  public ArtentPotionsEntitiesLootTableProvider(
      FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(output, registryLookup, LootContextTypes.ENTITY);
  }

  @Override
  public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
    lootTableBiConsumer.accept(
        ModEntities.MANA_CHICKEN.getLootTableKey().orElseThrow(),
        LootTable
            .builder()
            .pool(LootPool
                      .builder()
                      .rolls(ConstantLootNumberProvider.create(1.0F))
                      .with(ItemEntry
                                .builder(ModItems.ManaFeather)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(
                                    0.0F,
                                    2.0F)))
                                .apply(EnchantedCountIncreaseLootFunction.builder(
                                    this.registries,
                                    UniformLootNumberProvider.create(
                                        0.0F,
                                        1.0F)))))
            .pool(LootPool
                      .builder()
                      .rolls(ConstantLootNumberProvider.create(1.0F))
                      .with(ItemEntry
                                .builder(Items.CHICKEN)
                                .apply(FurnaceSmeltLootFunction
                                           .builder()
                                           .conditionally(this.createSmeltLootCondition()))
                                .apply(EnchantedCountIncreaseLootFunction.builder(
                                    this.registries,
                                    UniformLootNumberProvider.create(
                                        0.0F,
                                        1.0F))))));
  }
}
