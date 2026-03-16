package faceless.artent.potions.objects;

import faceless.artent.core.math.Color;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.registry.BrewingRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class BrewingIngredients {

  public static final BrewingIngredient Shadowveil = new BrewingIngredient(
      "shadowveil",
      ModBlocks.SHADOWVEIL.item(),
      Color.Purple.add(Color.Red));
  public static final BrewingIngredient CrimsonwoodBerry = new BrewingIngredient(
      "crimsonwood_berry",
      ModItems.BERRIES[4],
      Color.Red);
  public static final BrewingIngredient CrimsonLeaf = new BrewingIngredient("crimsonleaf", ModItems.CRIMSON_LEAF, Color.Gray);
  public static final BrewingIngredient Blackberry = new BrewingIngredient("backberry", ModItems.BERRIES[0], Color.Purple);
  public static final BrewingIngredient Blueberry = new BrewingIngredient("blueberry", ModItems.BERRIES[1], Color.Blue);
  public static final BrewingIngredient Cloudberry = new BrewingIngredient("cloudberry", ModItems.BERRIES[2], Color.Gold);
  public static final BrewingIngredient Raspberry = new BrewingIngredient("raspberry", ModItems.BERRIES[3], Color.Red);
  public static final BrewingIngredient StoneScale = new BrewingIngredient("stone_scale", ModItems.STONE_SCALE, Color.Cobalt);
  public static final BrewingIngredient Acorn = new BrewingIngredient(
      "acorn",
      ModItems.ACORN,
      Color.Brown.add(Color.White));
  public static final BrewingIngredient ManaFeather = new BrewingIngredient(
      "mana_feather",
      ModItems.MANA_FEATHER,
      Color.Purple);
  public static final BrewingIngredient FrostPumpkin = new BrewingIngredient(
      "frost_pumpkin",
      ModBlocks.FROST_PUMPKIN.item(),
      Color.LightBlue);

  public static final BrewingIngredient Bone = new BrewingIngredient("bone", Items.BONE, Color.White);
  public static final BrewingIngredient GlowstoneDust = new BrewingIngredient(
      "glowstone_dust",
      Items.GLOWSTONE_DUST,
      Color.Orange);
  public static final BrewingIngredient RedstoneDust = new BrewingIngredient("redstone", Items.REDSTONE, Color.Red);
  public static final BrewingIngredient Sugar = new BrewingIngredient("sugar", Items.SUGAR, Color.White);
  public static final BrewingIngredient Apple = new BrewingIngredient("apple", Items.APPLE, Color.Red);
  public static final BrewingIngredient DriedBrownMushroom = new BrewingIngredient(
      "dried_brown_mushroom",
      ModItems.DRIED_BROWN_MUSHROOM,
      Color.Brown);
  public static final BrewingIngredient DriedRedMushroom = new BrewingIngredient(
      "dried_red_mushroom",
      ModItems.DRIED_RED_MUSHROOM,
      Color.Red);
  public static final BrewingIngredient DriedShroom = new BrewingIngredient(
      "dried_shroom",
      ModItems.DRIED_SHROOM,
      Color.Cobalt);
  public static final BrewingIngredient ChorusPlant = new BrewingIngredient("chorus_plant", Items.CHORUS_FRUIT, Color.Purple);
  public static final BrewingIngredient BlazePowder = new BrewingIngredient("blaze_powder", Items.BLAZE_POWDER, Color.Orange);
  public static final BrewingIngredient CocoaBeans = new BrewingIngredient("cocoa_beans", Items.COCOA_BEANS, Color.Brown);
  public static final BrewingIngredient EnderPearl = new BrewingIngredient("ender_pearl", Items.ENDER_PEARL, Color.Cyan);
  public static final BrewingIngredient GlisteningMelon = new BrewingIngredient(
      "glistening_melon",
      Items.GLISTERING_MELON_SLICE,
      Color.Gold.add(Color.Red));
  public static final BrewingIngredient Kelp = new BrewingIngredient("kelp", Items.KELP, Color.Cyan);
  public static final BrewingIngredient Slime = new BrewingIngredient("slime", Items.SLIME_BALL, Color.Green);
  public static final BrewingIngredient PufferFish = new BrewingIngredient("pufferfish", Items.PUFFERFISH, Color.Yellow);
  public static final BrewingIngredient DragonBreath = new BrewingIngredient(
      "dragon_breath",
      Items.DRAGON_BREATH,
      Color.Pink.add(Color.Purple));
  public static final BrewingIngredient NetherWart = new BrewingIngredient(
      "nether_wart",
      Items.NETHER_WART,
      Color.Red.add(Color.Purple));
  public static final BrewingIngredient SpiderEye = new BrewingIngredient("spider_eye", Items.SPIDER_EYE, Color.Green);
  public static final BrewingIngredient FermentedSpiderEye = new BrewingIngredient(
      "fermented_spider_eye",
      Items.FERMENTED_SPIDER_EYE,
      Color.Blue.add(Color.Green));
  public static final BrewingIngredient MagmaCream = new BrewingIngredient(
      "magma_cream",
      Items.MAGMA_CREAM,
      Color.Red.add(Color.Gold));
  public static final BrewingIngredient PhantomMembrane = new BrewingIngredient(
      "phantom_membrane",
      Items.PHANTOM_MEMBRANE,
      Color.Purple);
  public static final BrewingIngredient IronChestplate = new BrewingIngredient(
      "iron_chestplate",
      Items.IRON_CHESTPLATE,
      Color.Gray);
  public static final BrewingIngredient GoldenCarrot = new BrewingIngredient("golden_carrot", Items.GOLDEN_CARROT, Color.Green);
  public static final BrewingIngredient RottenFlesh = new BrewingIngredient("rotten_flesh", Items.ROTTEN_FLESH, Color.Brown);
  public static final BrewingIngredient Stick = new BrewingIngredient("stick", Items.STICK, Color.Green);
  public static final BrewingIngredient LapisLazuli = new BrewingIngredient("lapis_lazuli", Items.LAPIS_LAZULI, Color.Cobalt);
  public static final BrewingIngredient Diamond = new BrewingIngredient("diamon", Items.DIAMOND, Color.LightBlue);
  public static final BrewingIngredient RabbitFoot = new BrewingIngredient("rabbit_foot", Items.RABBIT_FOOT, Color.Green);

  public static boolean IsIngredient(ItemStack stack) {
    return BrewingRegistry.Ingredients.values().stream().anyMatch(ing -> ing.item() == stack.getItem());
  }

  public static BrewingIngredient AsIngredient(ItemStack stack) {
    return BrewingRegistry.Ingredients
        .values()
        .stream()
        .filter(ing -> ing.item() == stack.getItem())
        .findFirst()
        .orElse(null);
  }
}