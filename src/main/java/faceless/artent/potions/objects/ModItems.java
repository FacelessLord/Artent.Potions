package faceless.artent.potions.objects;

import faceless.artent.core.item.group.ArtentItemGroupBuilder;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.api.MushroomType;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.ingridients.Ingredients;
import faceless.artent.potions.item.*;
import net.minecraft.block.ComposterBlock;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Function;

import static faceless.artent.potions.registry.DataComponentRegistry.CONCENTRATE_AMOUNT;
import static faceless.artent.potions.registry.DataComponentRegistry.POTION_KEY;

public final class ModItems {
  public static final FoodComponent Berry = new FoodComponent(2, 0.1f, false);

  public static Item CRIMSON_LEAF;
  public static Item GOLDEN_BUCKET;
  public static Item SMALL_BOTTLE_EXPLOSIVE;
  public static Item MEDIUM_BOTTLE_EXPLOSIVE;
  public static Item BIG_BOTTLE_EXPLOSIVE;
  public static Item SMALL_BOTTLE;
  public static Item MEDIUM_BOTTLE;
  public static Item BIG_BOTTLE;
  public static final Item[] BERRIES = new Item[5];

  public static Item MANA_CHICKEN_SPAWN_EGG;
  public static Item FROSTED_GOLEM_SPAWN_EGG;
  public static Item MANA_FEATHER;
  public static Item ACORN;
  public static Item STONE_SCALE;
  public static Item ICE_CRYSTAL_SHARD;
  public static Item DEBUG_BREWING_BOOK;
  public static Item FROST_PUMPKIN_SEEDS;
  public static Item BROWN_MUSHROOM_SPORES;
  public static Item RED_MUSHROOM_SPORES;
  public static Item SHROOM_SPORES;
  public static Item DRIED_BROWN_MUSHROOM;
  public static Item DRIED_RED_MUSHROOM;
  public static Item DRIED_SHROOM;

  public void register() {
    for (int i = 0; i < BERRIES.length; i++) {
      BERRIES[i] = register(
          Ingredients.GetBerryName(i),
          Item::new,
          new Item.Settings().maxCount(64).food(Berry),
          ModItemGroups.POTIONS);
    }

    CRIMSON_LEAF = register("crimson_leaf", Item::new, new Item.Settings().maxCount(64), ModItemGroups.POTIONS);
    GOLDEN_BUCKET = register(
        "golden_bucket",
        GoldenBucket::new,
        new Item.Settings().maxCount(1),
        ModItemGroups.POTIONS);

    SMALL_BOTTLE = register(
        "small_bottle",
        (settings) -> new PotionBottleItem(settings, "small", 1),
        new Item.Settings()
            .component(POTION_KEY, List.of())
            .component(CONCENTRATE_AMOUNT, 1)
            .maxCount(64),
        null);
    MEDIUM_BOTTLE = register(
        "medium_bottle",
        (settings) -> new PotionBottleItem(settings, "medium", 3),
        new Item.Settings()
            .component(POTION_KEY, List.of())
            .component(CONCENTRATE_AMOUNT, 3)
            .maxCount(1),
        null);
    BIG_BOTTLE = register(
        "big_bottle",
        (settings) -> new PotionBottleItem(settings, "big", 9),
        new Item.Settings()
            .component(POTION_KEY, List.of())
            .component(CONCENTRATE_AMOUNT, 9)
            .maxCount(1),
        null);

    SMALL_BOTTLE_EXPLOSIVE = register(
        "explosive_small_bottle",
        (settings) -> new ExplosivePotionBottleItem(settings, "small", 1),
        new Item.Settings()
            .component(POTION_KEY, List.of())
            .component(CONCENTRATE_AMOUNT, 1)
            .maxCount(64),
        null);
    MEDIUM_BOTTLE_EXPLOSIVE = register(
        "explosive_medium_bottle",
        (settings) -> new ExplosivePotionBottleItem(settings, "medium", 3),
        new Item.Settings()
            .component(POTION_KEY, List.of())
            .component(CONCENTRATE_AMOUNT, 3)
            .maxCount(1),
        null);
    BIG_BOTTLE_EXPLOSIVE = register(
        "explosive_big_bottle",
        (settings) -> new ExplosivePotionBottleItem(settings, "big", 9),
        new Item.Settings()
            .component(POTION_KEY, List.of())
            .component(CONCENTRATE_AMOUNT, 9)
            .maxCount(1),
        null);

    MANA_CHICKEN_SPAWN_EGG = register(
        "mana_chicken_spawn_egg",
        (Item.Settings settings) -> new SpawnEggItem(ModEntities.MANA_CHICKEN, settings),
        new Item.Settings(), ModItemGroups.POTIONS);
    FROSTED_GOLEM_SPAWN_EGG = register(
        "frosted_golem_spawn_egg",
        (Item.Settings settings) -> new SpawnEggItem(ModEntities.FROSTED_GOLEM, settings),
        new Item.Settings(), ModItemGroups.POTIONS);

    MANA_FEATHER = register(
        "mana_feather",
        Item::new,
        new Item.Settings().maxCount(64),
        ModItemGroups.POTIONS);
    ACORN = register(
        "acorn",
        Item::new,
        new Item.Settings().maxCount(64),
        ModItemGroups.POTIONS);
    STONE_SCALE = register(
        "stone_scale",
        Item::new,
        new Item.Settings().maxCount(64),
        ModItemGroups.POTIONS);
    ICE_CRYSTAL_SHARD = register(
        "ice_crystal_shard",
        Item::new,
        new Item.Settings().maxCount(64),
        ModItemGroups.POTIONS);
    DEBUG_BREWING_BOOK = register(
        "brew_book",
        DebugBrewBook::new,
        new Item.Settings().maxCount(64),
        ModItemGroups.POTIONS);

    FROST_PUMPKIN_SEEDS = register(
        ItemKeys.FROST_PUMPKIN_SEEDS.getValue().getPath(),
        (s) -> new BlockItem(ModBlocks.FROST_PUMPKIN_STEM.block(), s.registryKey(ItemKeys.FROST_PUMPKIN_SEEDS)),
        new Item.Settings().maxCount(64),
        ModItemGroups.POTIONS);

    BROWN_MUSHROOM_SPORES = register(
        "brown_mushroom_spores",
        s -> new MushroomSpores(MushroomType.Brown, s),
        new Item.Settings().maxCount(64),
        ModItemGroups.POTIONS);
    RED_MUSHROOM_SPORES = register(
        "red_mushroom_spores",
        s -> new MushroomSpores(MushroomType.Red, s),
        new Item.Settings().maxCount(64),
        ModItemGroups.POTIONS);
    SHROOM_SPORES = register(
        "shroom_spores",
        s -> new MushroomSpores(MushroomType.Shroom, s),
        new Item.Settings().maxCount(64),
        ModItemGroups.POTIONS);

    DRIED_BROWN_MUSHROOM = register(
        "dried_brown_mushroom",
        Item::new,
        new Item.Settings().maxCount(64),
        ModItemGroups.POTIONS);
    DRIED_RED_MUSHROOM = register(
        "dried_red_mushroom",
        Item::new,
        new Item.Settings().maxCount(64),
        ModItemGroups.POTIONS);
    DRIED_SHROOM = register(
        "dried_shroom",
        Item::new,
        new Item.Settings().maxCount(64),
        ModItemGroups.POTIONS);

    AlchemicalPotionUtil.appendPotionStacks(ModItems.SMALL_BOTTLE, 1, ModItemGroups.POTIONS);
    AlchemicalPotionUtil.appendPotionStacks(ModItems.MEDIUM_BOTTLE, 3, ModItemGroups.POTIONS);
    AlchemicalPotionUtil.appendPotionStacks(ModItems.BIG_BOTTLE, 9, ModItemGroups.POTIONS);

    AlchemicalPotionUtil.appendPotionStacks(ModItems.SMALL_BOTTLE_EXPLOSIVE, 1, ModItemGroups.POTIONS);
    AlchemicalPotionUtil.appendPotionStacks(ModItems.MEDIUM_BOTTLE_EXPLOSIVE, 3, ModItemGroups.POTIONS);
    AlchemicalPotionUtil.appendPotionStacks(ModItems.BIG_BOTTLE_EXPLOSIVE, 9, ModItemGroups.POTIONS);

    ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.CRIMSONWOOD_LEAVES.block().asItem(), 0.5F);
    ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModBlocks.CRIMSONWOOD_SAPLING.block().asItem(), 0.5F);
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
