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

import static faceless.artent.potions.ArtentPotions.LOGGER;

public class BrewingIngredientsBootstrap {

  public static BrewingIngredient Shroom = new BrewingIngredient(ModBlocks.SHROOM.item(), Color.Cobalt);
  public static BrewingIngredient Shadowveil = new BrewingIngredient(
      ModBlocks.SHADOWVEIL.item(),
      Color.Purple.add(Color.Red));
  public static BrewingIngredient CrimsonwoodBerry = new BrewingIngredient(ModItems.BERRIES[4], Color.Red);
  public static BrewingIngredient CrimsonLeaf = new BrewingIngredient(ModItems.CRIMSON_LEAF, Color.Gray);
  public static BrewingIngredient Blackberry = new BrewingIngredient(ModItems.BERRIES[0], Color.Purple);
  public static BrewingIngredient Blueberry = new BrewingIngredient(ModItems.BERRIES[1], Color.Blue);
  public static BrewingIngredient Cloudberry = new BrewingIngredient(ModItems.BERRIES[2], Color.Gold);
  public static BrewingIngredient Raspberry = new BrewingIngredient(ModItems.BERRIES[3], Color.Red);
  public static BrewingIngredient StoneScale = new BrewingIngredient(ModItems.STONE_SCALE, Color.Cobalt);
  public static BrewingIngredient Acorn = new BrewingIngredient(ModItems.ACORN, Color.Brown.add(Color.White));
  public static BrewingIngredient ManaFeather = new BrewingIngredient(ModItems.MANA_FEATHER, Color.Purple);
  public static BrewingIngredient FrostPumpkin = new BrewingIngredient(ModBlocks.FROST_PUMPKIN.item(), Color.LightBlue);

  public static BrewingIngredient Bone = new BrewingIngredient(Items.BONE, Color.White);
  public static BrewingIngredient GlowstoneDust = new BrewingIngredient(Items.GLOWSTONE_DUST, Color.Orange);
  public static BrewingIngredient RedstoneDust = new BrewingIngredient(Items.REDSTONE, Color.Red);
  public static BrewingIngredient Apple = new BrewingIngredient(Items.APPLE, Color.Red);
  public static BrewingIngredient BrownMushroom = new BrewingIngredient(Items.BROWN_MUSHROOM, Color.Brown);
  public static BrewingIngredient RedMushroom = new BrewingIngredient(Items.RED_MUSHROOM, Color.Red);
  public static BrewingIngredient ChorusPlant = new BrewingIngredient(Items.CHORUS_FRUIT, Color.Purple);
  public static BrewingIngredient BlazePowder = new BrewingIngredient(Items.BLAZE_POWDER, Color.Orange);
  public static BrewingIngredient EnderPearl = new BrewingIngredient(Items.ENDER_PEARL, Color.Cyan);
  public static BrewingIngredient GlisteningMelon = new BrewingIngredient(
      Items.GLISTERING_MELON_SLICE,
      Color.Gold.add(Color.Red));
  public static BrewingIngredient Kelp = new BrewingIngredient(Items.KELP, Color.Cyan);
  public static BrewingIngredient Slime = new BrewingIngredient(Items.SLIME_BALL, Color.Green);
  public static BrewingIngredient PufferFish = new BrewingIngredient(Items.PUFFERFISH, Color.Yellow);
  public static BrewingIngredient DragonBreath = new BrewingIngredient(
      Items.DRAGON_BREATH,
      Color.Pink.add(Color.Purple));
  public static BrewingIngredient NetherWart = new BrewingIngredient(Items.NETHER_WART, Color.Red.add(Color.Purple));
  public static BrewingIngredient SpiderEye = new BrewingIngredient(Items.SPIDER_EYE, Color.Green);
  public static BrewingIngredient FermentedSpiderEye = new BrewingIngredient(
      Items.FERMENTED_SPIDER_EYE,
      Color.Blue.add(Color.Green));
  public static BrewingIngredient MagmaCream = new BrewingIngredient(Items.MAGMA_CREAM, Color.Red.add(Color.Gold));
  public static BrewingIngredient PhantomMembrane = new BrewingIngredient(Items.PHANTOM_MEMBRANE, Color.Purple);
  public static BrewingIngredient IronChestplate = new BrewingIngredient(Items.IRON_CHESTPLATE, Color.Gray);
  public static BrewingIngredient GoldenCarrot = new BrewingIngredient(Items.GOLDEN_CARROT, Color.Green);
  public static BrewingIngredient RottenFlesh = new BrewingIngredient(Items.ROTTEN_FLESH, Color.Brown);
  public static BrewingIngredient Stick = new BrewingIngredient(Items.STICK, Color.Green);
  public static BrewingIngredient LapisLazuli = new BrewingIngredient(Items.LAPIS_LAZULI, Color.Cobalt);
  public static BrewingIngredient Diamond = new BrewingIngredient(Items.DIAMOND, Color.LightBlue);
  public static BrewingIngredient RabbitFoot = new BrewingIngredient(Items.RABBIT_FOOT, Color.Green);

  public static void bootstrap(Registerable<BrewingIngredient> brewingRegistry) {
    LOGGER.info("BrewingIngredients bootstrap");
    register(Shroom, brewingRegistry, "shroom");
    register(Shadowveil, brewingRegistry, "shadowveil");
    register(CrimsonwoodBerry, brewingRegistry, "crimson_berry");
    register(CrimsonLeaf, brewingRegistry, "crimson_leaf");
    register(Blackberry, brewingRegistry, "blackberry");
    register(Blueberry, brewingRegistry, "blueberry");
    register(Cloudberry, brewingRegistry, "cloudberry");
    register(Raspberry, brewingRegistry, "raspberry");
    register(StoneScale, brewingRegistry, "stone_scale");
    register(Acorn, brewingRegistry, "acorn");
    register(ManaFeather, brewingRegistry, "mana_feather");
    register(FrostPumpkin, brewingRegistry, "frost_pumpkin");

    register(Bone, brewingRegistry, "bone");
    register(GlowstoneDust, brewingRegistry, "glowstone_dust");
    register(RedstoneDust, brewingRegistry, "redstone_dust");
    register(Apple, brewingRegistry, "apple");
    register(BrownMushroom, brewingRegistry, "brown_mushroom");
    register(RedMushroom, brewingRegistry, "red_mushroom");
    register(ChorusPlant, brewingRegistry, "chorus_plant");
    register(BlazePowder, brewingRegistry, "blaze_powder");
    register(EnderPearl, brewingRegistry, "ender_pearl");
    register(GlisteningMelon, brewingRegistry, "glistening_melon");
    register(Kelp, brewingRegistry, "kelp");
    register(Slime, brewingRegistry, "slime");
    register(PufferFish, brewingRegistry, "puffer_fish");
    register(DragonBreath, brewingRegistry, "dragon_breath");
    register(NetherWart, brewingRegistry, "nether_wart");
    register(SpiderEye, brewingRegistry, "spider_eye");
    register(FermentedSpiderEye, brewingRegistry, "fermented_spider_eye");
    register(MagmaCream, brewingRegistry, "magma_cream");
    register(PhantomMembrane, brewingRegistry, "phantom_membrane");
    register(IronChestplate, brewingRegistry, "iron_chestplate");
    register(GoldenCarrot, brewingRegistry, "golden_carrot");
    register(RottenFlesh, brewingRegistry, "rotten_flesh");
    register(Stick, brewingRegistry, "stick");
    register(LapisLazuli, brewingRegistry, "lapis_lazuly");
    register(Diamond, brewingRegistry, "diamond");
    register(RabbitFoot, brewingRegistry, "rabbit_foot");
  }

  public static BrewingIngredient register(
      BrewingIngredient ingredient,
      Registerable<BrewingIngredient> brewingRegistry,
      String key) {

    var registryKey = RegistryKey.of(
        ModRegistries.POTION_INGREDIENT_REGISTRY_KEY,
        Identifier.of(ArtentPotions.MODID, key));

    var entry = brewingRegistry.register(registryKey, ingredient);
    ingredient.setId(entry.getKey().get().getValue());

    return ingredient;
  }

  ;
}