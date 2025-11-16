package faceless.artent.potions.objects;

import faceless.artent.core.math.Color;
import faceless.artent.potions.BrewingAutomata;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.registry.BrewingRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

@Deprecated()
public class BrewingIngredients {

    public static final BrewingIngredient Shroom = new BrewingIngredient(ModBlocks.SHROOM.item(), -1, Color.Cobalt);
    public static final BrewingIngredient Shadowveil = new BrewingIngredient(ModBlocks.SHADOWVEIL.item(), -1, Color.Purple.add(Color.Red));
    public static final BrewingIngredient CrimsonwoodBerry = new BrewingIngredient(ModItems.BERRIES[4], -1, Color.Red);
    public static final BrewingIngredient CrimsonLeaf = new BrewingIngredient(ModItems.CRIMSON_LEAF, -1, Color.Gray);
    public static final BrewingIngredient Blackberry = new BrewingIngredient(ModItems.BERRIES[0], -1, Color.Purple);
    public static final BrewingIngredient Blueberry = new BrewingIngredient(ModItems.BERRIES[1], -1, Color.Blue);
    public static final BrewingIngredient Cloudberry = new BrewingIngredient(ModItems.BERRIES[2], -1, Color.Gold);
    public static final BrewingIngredient Raspberry = new BrewingIngredient(ModItems.BERRIES[3], -1, Color.Red);
    public static final BrewingIngredient StoneScale = new BrewingIngredient(ModItems.STONE_SCALE, -1, Color.Cobalt);
    public static final BrewingIngredient Acorn = new BrewingIngredient(ModItems.ACORN, -1, Color.Brown.add(Color.White));
    public static final BrewingIngredient ManaFeather = new BrewingIngredient(ModItems.MANA_FEATHER, -1, Color.Purple);
    public static final BrewingIngredient FrostPumpkin = new BrewingIngredient(ModBlocks.FROST_PUMPKIN.item(), -1, Color.LightBlue);

    public static final BrewingIngredient Bone = new BrewingIngredient(Items.BONE, -1, Color.White);
    public static final BrewingIngredient GlowstoneDust = new BrewingIngredient(Items.GLOWSTONE_DUST, -1, Color.Orange);
    public static final BrewingIngredient RedstoneDust = new BrewingIngredient(Items.REDSTONE, -1, Color.Red);
    public static final BrewingIngredient Apple = new BrewingIngredient(Items.APPLE, -1, Color.Red);
    public static final BrewingIngredient BrownMushroom = new BrewingIngredient(Items.BROWN_MUSHROOM, -1,  Color.Brown);
    public static final BrewingIngredient RedMushroom = new BrewingIngredient(Items.RED_MUSHROOM, -1, Color.Red);
    public static final BrewingIngredient ChorusPlant = new BrewingIngredient(Items.CHORUS_FRUIT, -1, Color.Purple);
    public static final BrewingIngredient BlazePowder = new BrewingIngredient(Items.BLAZE_POWDER, -1, Color.Orange);
    public static final BrewingIngredient EnderPearl = new BrewingIngredient(Items.ENDER_PEARL, -1, Color.Cyan);
    public static final BrewingIngredient GlisteningMelon = new BrewingIngredient(Items.GLISTERING_MELON_SLICE, -1, Color.Gold.add(Color.Red));
    public static final BrewingIngredient Kelp = new BrewingIngredient(Items.KELP, -1, Color.Cyan);
    public static final BrewingIngredient Slime = new BrewingIngredient(Items.SLIME_BALL, -1, Color.Green);
    public static final BrewingIngredient PufferFish = new BrewingIngredient(Items.PUFFERFISH, -1, Color.Yellow);
    public static final BrewingIngredient DragonBreath = new BrewingIngredient(Items.DRAGON_BREATH, -1, Color.Pink.add(Color.Purple));
    public static final BrewingIngredient NetherWart = new BrewingIngredient(Items.NETHER_WART, -1, Color.Red.add(Color.Purple));
    public static final BrewingIngredient SpiderEye = new BrewingIngredient(Items.SPIDER_EYE, -1, Color.Green);
    public static final BrewingIngredient FermentedSpiderEye = new BrewingIngredient(Items.FERMENTED_SPIDER_EYE, -1, Color.Blue.add(Color.Green));
    public static final BrewingIngredient MagmaCream = new BrewingIngredient(Items.MAGMA_CREAM, -1, Color.Red.add(Color.Gold));
    public static final BrewingIngredient PhantomMembrane = new BrewingIngredient(Items.PHANTOM_MEMBRANE, -1, Color.Purple);
    public static final BrewingIngredient IronChestplate = new BrewingIngredient(Items.IRON_CHESTPLATE, -1, Color.Gray);
    public static final BrewingIngredient GoldenCarrot = new BrewingIngredient(Items.GOLDEN_CARROT, -1, Color.Green);
    public static final BrewingIngredient RottenFlesh = new BrewingIngredient(Items.ROTTEN_FLESH, -1, Color.Brown);
    public static final BrewingIngredient Stick = new BrewingIngredient(Items.STICK, -1, Color.Green);
    public static final BrewingIngredient LapisLazuli = new BrewingIngredient(Items.LAPIS_LAZULI, -1, Color.Cobalt);
    public static final BrewingIngredient Diamond = new BrewingIngredient(Items.DIAMOND, -1, Color.LightBlue);
    public static final BrewingIngredient RabbitFoot = new BrewingIngredient(Items.RABBIT_FOOT, -1, Color.Green);


    // TODO move
    public static BrewingAutomata RecipeAutomata;

    public static boolean IsIngredient(ItemStack stack) {
        return BrewingRegistry.Ingredients.containsKey(new BrewingIngredient(stack.getItem(), -1));
    }

    public static BrewingIngredient AsIngredient(ItemStack stack) {
        var ignoreMetaIngredient = new BrewingIngredient(stack.getItem(), -1);
        if (BrewingRegistry.Ingredients.containsKey(ignoreMetaIngredient)) return ignoreMetaIngredient;
        return null;
    }
}