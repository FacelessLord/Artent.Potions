package faceless.artent.potions.bootstrap;

import faceless.artent.core.math.Color;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModItems;
import faceless.artent.potions.objects.ModRegistries;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class BrewingIngredientsBootstrap {

  public static BrewingIngredient Shroom;
  public static BrewingIngredient Shadowveil;
  public static BrewingIngredient CrimsonwoodBerry;
  public static BrewingIngredient CrimsonLeaf;
  public static BrewingIngredient Blackberry;
  public static BrewingIngredient Blueberry;
  public static BrewingIngredient Cloudberry;
  public static BrewingIngredient Raspberry;
  public static BrewingIngredient StoneScale;
  public static BrewingIngredient Acorn;
  public static BrewingIngredient ManaFeather;
  public static BrewingIngredient FrostPumpkin;

  public static BrewingIngredient Bone;
  public static BrewingIngredient GlowstoneDust;
  public static BrewingIngredient RedstoneDust;
  public static BrewingIngredient Apple;
  public static BrewingIngredient BrownMushroom;
  public static BrewingIngredient RedMushroom;
  public static BrewingIngredient ChorusPlant;
  public static BrewingIngredient BlazePowder;
  public static BrewingIngredient EnderPearl;
  public static BrewingIngredient GlisteningMelon;
  public static BrewingIngredient Kelp;
  public static BrewingIngredient Slime;
  public static BrewingIngredient PufferFish;
  public static BrewingIngredient DragonBreath;
  public static BrewingIngredient NetherWart;
  public static BrewingIngredient SpiderEye;
  public static BrewingIngredient FermentedSpiderEye;
  public static BrewingIngredient MagmaCream;
  public static BrewingIngredient PhantomMembrane;
  public static BrewingIngredient IronChestplate;
  public static BrewingIngredient GoldenCarrot;
  public static BrewingIngredient RottenFlesh;
  public static BrewingIngredient Stick;
  public static BrewingIngredient LapisLazuli;
  public static BrewingIngredient Diamond;
  public static BrewingIngredient RabbitFoot;

  public static void bootstrap(Registerable<BrewingIngredient> brewingRegistry) {
    Shroom = register(brewingRegistry, "shroom", ModBlocks.SHROOM.item(), -1, Color.Cobalt);
    Shadowveil = register(brewingRegistry, "shadowveil", ModBlocks.SHADOWVEIL.item(), -1, Color.Purple.add(Color.Red));
    CrimsonwoodBerry = register(brewingRegistry, "crimson_berry", ModItems.BERRIES[4], -1, Color.Red);
    CrimsonLeaf = register(brewingRegistry, "crimson_leaf", ModItems.CRIMSON_LEAF, -1, Color.Gray);
    Blackberry = register(brewingRegistry, "blackberry", ModItems.BERRIES[0], -1, Color.Purple);
    Blueberry = register(brewingRegistry, "blueberry", ModItems.BERRIES[1], -1, Color.Blue);
    Cloudberry = register(brewingRegistry, "cloudberry", ModItems.BERRIES[2], -1, Color.Gold);
    Raspberry = register(brewingRegistry, "raspberry", ModItems.BERRIES[3], -1, Color.Red);
    StoneScale = register(brewingRegistry, "stone_scale", ModItems.STONE_SCALE, -1, Color.Cobalt);
    Acorn = register(brewingRegistry, "acorn", ModItems.ACORN, -1, Color.Brown.add(Color.White));
    ManaFeather = register(brewingRegistry, "mana_feather", ModItems.MANA_FEATHER, -1, Color.Purple);
    FrostPumpkin = register(brewingRegistry, "frost_pumpking", ModBlocks.FROST_PUMPKIN.item(), -1, Color.LightBlue);

    Bone = register(brewingRegistry, "bone", Items.BONE, -1, Color.White);
    GlowstoneDust = register(brewingRegistry, "glowstone_dust", Items.GLOWSTONE_DUST, -1, Color.Orange);
    RedstoneDust = register(brewingRegistry, "redstone_dust", Items.REDSTONE, -1, Color.Red);
    Apple = register(brewingRegistry, "apple", Items.APPLE, -1, Color.Red);
    BrownMushroom = register(brewingRegistry, "brown_mushroom", Items.BROWN_MUSHROOM, -1, Color.Brown);
    RedMushroom = register(brewingRegistry, "red_mushroom", Items.RED_MUSHROOM, -1, Color.Red);
    ChorusPlant = register(brewingRegistry, "chorus_plant", Items.CHORUS_FRUIT, -1, Color.Purple);
    BlazePowder = register(brewingRegistry, "blaze_powder", Items.BLAZE_POWDER, -1, Color.Orange);
    EnderPearl = register(brewingRegistry, "ender_pearl", Items.ENDER_PEARL, -1, Color.Cyan);
    GlisteningMelon = register(
        brewingRegistry,
        "glistening_melon",
        Items.GLISTERING_MELON_SLICE,
        -1,
        Color.Gold.add(Color.Red));
    Kelp = register(brewingRegistry, "kelp", Items.KELP, -1, Color.Cyan);
    Slime = register(brewingRegistry, "slime", Items.SLIME_BALL, -1, Color.Green);
    PufferFish = register(brewingRegistry, "puffer_fish", Items.PUFFERFISH, -1, Color.Yellow);
    DragonBreath = register(brewingRegistry, "dragon_breath", Items.DRAGON_BREATH, -1, Color.Pink.add(Color.Purple));
    NetherWart = register(brewingRegistry, "nether_wart", Items.NETHER_WART, -1, Color.Red.add(Color.Purple));
    SpiderEye = register(brewingRegistry, "spider_eye", Items.SPIDER_EYE, -1, Color.Green);
    FermentedSpiderEye = register(
        brewingRegistry,
        "fermented_spider_eye",
        Items.FERMENTED_SPIDER_EYE,
        -1,
        Color.Blue.add(Color.Green));
    MagmaCream = register(brewingRegistry, "magma_cream", Items.MAGMA_CREAM, -1, Color.Red.add(Color.Gold));
    PhantomMembrane = register(brewingRegistry, "phantom_membrane", Items.PHANTOM_MEMBRANE, -1, Color.Purple);
    IronChestplate = register(brewingRegistry, "iron_chestplate", Items.IRON_CHESTPLATE, -1, Color.Gray);
    GoldenCarrot = register(brewingRegistry, "golden_carrot", Items.GOLDEN_CARROT, -1, Color.Green);
    RottenFlesh = register(brewingRegistry, "rotten_flesh", Items.ROTTEN_FLESH, -1, Color.Brown);
    Stick = register(brewingRegistry, "stick", Items.STICK, -1, Color.Green);
    LapisLazuli = register(brewingRegistry, "lapis_lazuly", Items.LAPIS_LAZULI, -1, Color.Cobalt);
    Diamond = register(brewingRegistry, "diamond", Items.DIAMOND, -1, Color.LightBlue);
    RabbitFoot = register(brewingRegistry, "rabbit_foot", Items.RABBIT_FOOT, -1, Color.Green);
  }

  public static BrewingIngredient register(
      Registerable<BrewingIngredient> brewingRegistry,
      String key,
      Item item,
      int meta,
      Color color) {
    var ingredient = new BrewingIngredient(item, meta, color);

    var registryKey = RegistryKey.of(
        ModRegistries.POTION_INGREDIENT_REGISTRY_KEY,
        Identifier.of(ArtentPotions.MODID, key));

    var entry = brewingRegistry.register(registryKey, ingredient);
    ingredient.setRegistryEntry(entry);

    return ingredient;
  }

  ;
}