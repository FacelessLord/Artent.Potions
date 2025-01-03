package faceless.artent_potions.registry;

import faceless.artent_potions.Artent;
import faceless.artent_potions.brewingApi.item.INamed;
import faceless.artent_potions.brewingApi.item.group.ArtentItemGroupBuilder;
import faceless.artent_potions.brewing.api.AlchemicalPotionUtil;
import faceless.artent_potions.brewing.ingridients.Ingredients;
import faceless.artent_potions.objects.ModItemGroups;
import faceless.artent_potions.objects.ModItems;
import faceless.artent_potions.sharpening.item.EnhancerItem;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ItemRegistry implements IRegistry {

    public void register() {
        // BREWING
        for (int i = 0; i < 5; i++) {
            register(Ingredients.GetBerryName(i), ModItems.berries[i], ModItemGroups.Potions);
        }
        register("crimson_leaf", ModItems.CrimsonLeaf, ModItemGroups.Potions);
        register("empty_phial", ModItems.EmptyPhial, ModItemGroups.Potions);
        register("empty_phial_explosive", ModItems.EmptyPhialExplosive, ModItemGroups.Potions);
        register("small_concentrate_phial", ModItems.SmallConcentratePhial, ModItemGroups.Potions);
        register("medium_concentrate_phial", ModItems.MediumConcentratePhial, ModItemGroups.Potions);
        register("big_concentrate_phial", ModItems.BigConcentratePhial, ModItemGroups.Potions);
        register("golden_bucket", ModItems.GoldenBucket, ModItemGroups.Potions);
        register("golden_bucket_filled", ModItems.GoldenBucketFilled);

        register(ModItems.PotionPhial);
        AlchemicalPotionUtil.appendPotionStacks(ModItems.PotionPhial, ModItemGroups.Potions);
        register(ModItems.PotionPhialExplosive);
        AlchemicalPotionUtil.appendPotionStacks(ModItems.PotionPhialExplosive, ModItemGroups.Potions);

        register("small_concentrate", ModItems.SmallConcentrate);
        AlchemicalPotionUtil.appendFermentedPotionStacks(ModItems.SmallConcentrate, -1, ModItemGroups.Potions);
        register("medium_concentrate", ModItems.MediumConcentrate);
        AlchemicalPotionUtil.appendFermentedPotionStacks(ModItems.MediumConcentrate, 3, ModItemGroups.Potions);
        register("big_concentrate", ModItems.BigConcentrate);
        AlchemicalPotionUtil.appendFermentedPotionStacks(ModItems.BigConcentrate, 9, ModItemGroups.Potions);
    }

    public void register(String itemId, Item item, ArtentItemGroupBuilder groupBuilder) {
        Registry.register(Registries.ITEM, new Identifier(Artent.MODID, itemId), item);
        groupBuilder.addItem(item);
    }

    public <T extends Item & INamed> void register(T item, ArtentItemGroupBuilder groupBuilder) {
        Registry.register(Registries.ITEM, new Identifier(Artent.MODID, item.getId()), item);
        groupBuilder.addItem(item);
    }

    public void register(String itemId, Item item) {
        Registry.register(Registries.ITEM, new Identifier(Artent.MODID, itemId), item);
    }

    public <T extends Item & INamed> void register(T item) {
        Registry.register(Registries.ITEM, new Identifier(Artent.MODID, item.getId()), item);
    }
}
