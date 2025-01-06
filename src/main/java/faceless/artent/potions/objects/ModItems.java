package faceless.artent.potions.objects;

import faceless.artent.core.item.group.ArtentItemGroupBuilder;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.ingridients.Ingredients;
import faceless.artent.potions.item.*;
import net.minecraft.block.ComposterBlock;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static faceless.artent.potions.registry.DataComponentRegistry.CONCENTRATE_AMOUNT;
import static faceless.artent.potions.registry.DataComponentRegistry.POTION_KEY;

public final class ModItems {
  public static Item EmptyPhial;
  public static Item EmptyPhialExplosive;
  public static Item PotionPhial;
  public static Item PotionPhialExplosive;

  public static FoodComponent Berry = new FoodComponent(2, 0.1f, false);

  public static Item CrimsonLeaf;
  public static Item GoldenBucket;
  public static Item GoldenBucketFilled;
  public static Item SmallConcentratePhial;
  public static Item SmallConcentrate;
  public static Item MediumConcentratePhial;
  public static Item MediumConcentrate;
  public static Item BigConcentratePhial;
  public static Item BigConcentrate;

  public static Item[] berries = new Item[5];

  public void register() {
    for (int i = 0; i < berries.length; i++) {
      berries[i] = register(
          Ingredients.GetBerryName(i),
          Item::new,
          new Item.Settings().maxCount(64).food(Berry),
          ModItemGroups.Potions);
    }

    CrimsonLeaf = register("crimson_leaf", Item::new, new Item.Settings().maxCount(64), ModItemGroups.Potions);
    GoldenBucket = register("golden_bucket", Item::new, new Item.Settings().maxCount(1), ModItemGroups.Potions);
    GoldenBucketFilled = register(
        "golden_bucket_filled",
        FilledGoldenBucket::new,
        new Item.Settings().maxCount(1),
        null);

    EmptyPhial = register("empty_phial", Item::new, new Item.Settings().maxCount(64), ModItemGroups.Potions);
    EmptyPhialExplosive = register(
        "empty_phial_explosive",
        Item::new,
        new Item.Settings().maxCount(64),
        ModItemGroups.Potions);
    PotionPhial = register(
        "potion_phial",
        PotionPhial::new,
        new Item.Settings().component(POTION_KEY, "").maxCount(64),
        null);

    PotionPhialExplosive = register(
        "potion_phial_explosive",
        PotionPhialExplosive::new,
        new Item.Settings().component(POTION_KEY, "").maxCount(64),
        null);

    AlchemicalPotionUtil.appendPotionStacks(ModItems.PotionPhial, ModItemGroups.Potions);
    AlchemicalPotionUtil.appendPotionStacks(ModItems.PotionPhialExplosive, ModItemGroups.Potions);

    SmallConcentratePhial = register(
        "small_concentrate_phial",
        Item::new,
        new Item.Settings().maxCount(64),
        ModItemGroups.Potions);
    SmallConcentrate = register(
        "small_concentrate",
        SmallConcentrate::new,
        new Item.Settings().component(POTION_KEY, "").maxCount(64),
        null);
    MediumConcentratePhial = register(
        "medium_concentrate_phial",
        Item::new,
        new Item.Settings().maxCount(1),
        ModItemGroups.Potions);
    MediumConcentrate = register(
        "medium_concentrate",
        MediumConcentrate::new,
        new Item.Settings()
            .component(POTION_KEY, "")
            .component(CONCENTRATE_AMOUNT, 3)
            .maxCount(1),
        null);
    BigConcentratePhial = register(
        "big_concentrate_phial",
        Item::new,
        new Item.Settings().maxCount(64),
        ModItemGroups.Potions);
    BigConcentrate = register(
        "big_concentrate",
        BigConcentrate::new,
        new Item.Settings()
            .component(POTION_KEY, "")
            .component(CONCENTRATE_AMOUNT, 9)
            .maxCount(1),
        null);

    AlchemicalPotionUtil.appendFermentedPotionStacks(ModItems.SmallConcentrate, -1, ModItemGroups.Potions);
    AlchemicalPotionUtil.appendFermentedPotionStacks(ModItems.MediumConcentrate, 3, ModItemGroups.Potions);
    AlchemicalPotionUtil.appendFermentedPotionStacks(ModItems.BigConcentrate, 9, ModItemGroups.Potions);

    ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.CrimsonwoodLeaves.asItem(), 0.5F);
    ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.CrimsonwoodSapling.asItem(), 0.5F);
  }

  public static Item register(
      String keyString,
      Function<Item.Settings, Item> factory,
      Item.Settings settings,
      ArtentItemGroupBuilder groupBuilder) {
    var key = keyOf(keyString);
    Item item = factory.apply(settings.registryKey(key));
    Registry.register(Registries.ITEM, key, item);
    if (groupBuilder != null) groupBuilder.addItem(item);
    return item;
  }

  private static RegistryKey<Item> keyOf(String id) {
    return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ArtentPotions.MODID, id));
  }
}
