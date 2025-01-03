package faceless.artent_potions.objects;

import faceless.artent_core.item.ArtentItem;
import faceless.artent_potions.item.*;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;

public final class ModItems {
    public static Item EmptyPhial = new Item(new Item.Settings().maxCount(64));
    public static Item EmptyPhialExplosive = new Item(new Item.Settings().maxCount(64));
    public static PotionPhial PotionPhial = new PotionPhial(new Item.Settings().maxCount(64));
    public static PotionPhialExplosive PotionPhialExplosive = new PotionPhialExplosive(new Item.Settings().maxCount(64));
    public static final FoodComponent Berry = new FoodComponent(2, 0.1f, false);
    public static Item CrimsonLeaf = new Item(new Item.Settings().maxCount(64));
    public static Item GoldenBucket = new Item(new Item.Settings().maxCount(1));
    public static Item GoldenBucketFilled = new FilledGoldenBucket(new Item.Settings().maxCount(1));
    public static Item SmallConcentratePhial = new Item(new Item.Settings().maxCount(64));
    public static Item SmallConcentrate = new SmallConcentrate(new Item.Settings().maxCount(64));
    public static Item MediumConcentratePhial = new Item(new Item.Settings().maxCount(64));
    public static Item MediumConcentrate = new MediumConcentrate(new Item.Settings().maxCount(1));
    public static Item BigConcentratePhial = new Item(new Item.Settings().maxCount(64));
    public static Item BigConcentrate = new BigConcentrate(new Item.Settings().maxCount(1));

    public static Item[] berries = new Item[]{
      new Item(new Item.Settings().maxCount(64).food(Berry)),
      new Item(new Item.Settings().maxCount(64).food(Berry)),
      new Item(new Item.Settings().maxCount(64).food(Berry)),
      new Item(new Item.Settings().maxCount(64).food(Berry)),
      new Item(new Item.Settings().maxCount(64).food(Berry)),
    };
}
