package faceless.artent.potions.client.datagen;

import com.google.common.collect.Maps;
import faceless.artent.potions.objects.ModEntities;
import faceless.artent.potions.objects.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.AnyOfLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.function.FurnaceSmeltLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.EntityEquipmentPredicate;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.predicate.item.ItemSubPredicateTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ArtentPotionsEntitiesLootTableProvider extends SimpleFabricLootTableProvider {
  private final Map<RegistryKey<LootTable>, LootTable.Builder> lootTables = Maps.newHashMap();
  private final CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture;
  private RegistryWrapper.WrapperLookup registries;

  public ArtentPotionsEntitiesLootTableProvider(
      FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(output, registryLookup, LootContextTypes.ENTITY);
    this.registriesFuture = registryLookup;
  }

  @Override
  public CompletableFuture<?> run(DataWriter writer) {
    return this.registriesFuture.thenCompose((lookup) -> {
      this.registries = lookup;
      return super.run(writer);
    });
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

  private AnyOfLootCondition.Builder createSmeltLootCondition() {
    RegistryWrapper.Impl<Enchantment> impl = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);
    return AnyOfLootCondition.builder(
        EntityPropertiesLootCondition.builder(
            LootContext.EntityTarget.THIS,
            EntityPredicate.Builder
                .create()
                .flags(EntityFlagsPredicate.Builder.create().onFire(true))),
        EntityPropertiesLootCondition.builder(
            LootContext.EntityTarget.DIRECT_ATTACKER,
            EntityPredicate.Builder
                .create()
                .equipment(EntityEquipmentPredicate.Builder
                               .create()
                               .mainhand(ItemPredicate.Builder.create().subPredicate(
                                   ItemSubPredicateTypes.ENCHANTMENTS,
                                   EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(
                                       impl.getOrThrow(EnchantmentTags.SMELTS_LOOT),
                                       NumberRange.IntRange.ANY))))))));
  }
}
