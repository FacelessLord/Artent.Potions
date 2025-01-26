package faceless.artent.potions.client.datagen;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.AnyOfLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
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
import net.minecraft.util.context.ContextType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class ArtentPotionsBaseLootTableProvider extends SimpleFabricLootTableProvider {
  private final CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture;
  protected RegistryWrapper.WrapperLookup registries;

  public ArtentPotionsBaseLootTableProvider(
      FabricDataOutput output,
      CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup,
      ContextType contextType) {
    super(output, registryLookup, contextType);
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
  public abstract void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer);

  protected AnyOfLootCondition.Builder createSmeltLootCondition() {
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
