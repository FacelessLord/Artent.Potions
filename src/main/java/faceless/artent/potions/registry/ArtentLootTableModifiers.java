package faceless.artent.potions.registry;

import faceless.artent.potions.objects.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ExplosionDecayLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.predicate.item.ItemSubPredicateTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;

public class ArtentLootTableModifiers {

  public void modifyLootTables() {
    LootTableEvents.MODIFY.register((RegistryKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, RegistryWrapper.WrapperLookup registries) -> {
      var oakLeavesKey = Blocks.OAK_LEAVES.getLootTableKey().orElseThrow();
      var tropicalFish = EntityType.TROPICAL_FISH.getLootTableKey().orElseThrow();
      RegistryWrapper.Impl<Enchantment> impl = registries.getOrThrow(RegistryKeys.ENCHANTMENT);
      // Let's only modify built-in loot tables and leave data pack loot tables untouched by checking the source.
      // We also check that the loot table ID is equal to the ID we want.
      if (!source.isBuiltin())
        return;
      if (oakLeavesKey.equals(key)) {
        tableBuilder.pool(
            LootPool
                .builder()
                .rolls(ConstantLootNumberProvider.create(1.0F))
                .conditionally(
                    MatchToolLootCondition
                        .builder(ItemPredicate.Builder
                                     .create()
                                     .items(
                                         registries.getOrThrow(RegistryKeys.ITEM),
                                         Items.SHEARS))
                        .or(MatchToolLootCondition.builder(
                            ItemPredicate.Builder
                                .create()
                                .subPredicate(
                                    ItemSubPredicateTypes.ENCHANTMENTS,
                                    EnchantmentsPredicate.enchantments(
                                        List.of(new EnchantmentPredicate(
                                            impl.getOrThrow(
                                                Enchantments.SILK_TOUCH),
                                            NumberRange.IntRange.atLeast(
                                                1)))))))
                        .invert())
                .conditionally(TableBonusLootCondition.builder(
                    impl.getOrThrow(Enchantments.FORTUNE),
                    0.025F,
                    0.03125F,
                    0.041666668F,
                    0.05F))
                .with(ItemEntry
                          .builder(ModItems.Acorn)
                          .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                          .apply(ExplosionDecayLootFunction.builder())));
    }

      if (tropicalFish.equals(key)) {
        tableBuilder.pool(
            LootPool
                .builder()
                .rolls(ConstantLootNumberProvider.create(1.0F))
                .conditionally(TableBonusLootCondition.builder(
                    impl.getOrThrow(Enchantments.FORTUNE),
                    0.025F,
                    0.03125F,
                    0.041666668F,
                    0.05F))
                .with(ItemEntry
                          .builder(ModItems.StoneScale)
                          .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                          .apply(ExplosionDecayLootFunction.builder())));
      }
  });
}


}
