package faceless.artent.potions.registry;

import faceless.artent.core.math.Color;
import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.BrewingAutomata;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.objects.BrewingRecipes;

import java.util.Hashtable;

import static faceless.artent.potions.objects.AlchemicalPotions.*;

public class BrewingRegistry implements IRegistry {
  public static final Hashtable<BrewingIngredient, Color> Ingredients = new Hashtable<>();

  @Override
  public void register() {
    registerIngredients();
    registerRecipes();
    registerAdditionalEdges();
  }

  private void registerIngredients() {
    Ingredients.put(BrewingRecipes.Shroom, Color.Cobalt);
    Ingredients.put(BrewingRecipes.Shadowveil, Color.Purple.add(Color.Red));
    Ingredients.put(BrewingRecipes.CrimsonwoodBerry, Color.Red);
    Ingredients.put(BrewingRecipes.CrimsonLeaf, Color.Gray);
    Ingredients.put(BrewingRecipes.Blackberry, Color.Purple);
    Ingredients.put(BrewingRecipes.Blueberry, Color.Blue);
    Ingredients.put(BrewingRecipes.Cloudberry, Color.Gold);
    Ingredients.put(BrewingRecipes.Raspberry, Color.Red);
    Ingredients.put(BrewingRecipes.StoneScale, Color.Cobalt);
    Ingredients.put(BrewingRecipes.Acorn, Color.Brown.add(Color.White));
    Ingredients.put(BrewingRecipes.ManaFeather, Color.Purple);
    Ingredients.put(BrewingRecipes.FrostPumpkin, Color.LightBlue);

    Ingredients.put(BrewingRecipes.Bone, Color.White);
    Ingredients.put(BrewingRecipes.GlowstoneDust, Color.Orange);
    Ingredients.put(BrewingRecipes.RedstoneDust, Color.Red);
    Ingredients.put(BrewingRecipes.Apple, Color.Red);
    Ingredients.put(BrewingRecipes.BrownMushroom, Color.Brown);
    Ingredients.put(BrewingRecipes.RedMushroom, Color.Red);
    Ingredients.put(BrewingRecipes.ChorusPlant, Color.Purple);
    Ingredients.put(BrewingRecipes.BlazePowder, Color.Orange);
    Ingredients.put(BrewingRecipes.EnderPearl, Color.Cyan);
    Ingredients.put(BrewingRecipes.GlisteningMelon, Color.Gold.add(Color.Red));
    Ingredients.put(BrewingRecipes.Kelp, Color.Cyan);
    Ingredients.put(BrewingRecipes.Slime, Color.Green);
    Ingredients.put(BrewingRecipes.RabbitsFoot, Color.Brown);
    Ingredients.put(BrewingRecipes.PufferFish, Color.Yellow);
    Ingredients.put(BrewingRecipes.DragonBreath, Color.Pink.add(Color.Purple));
    Ingredients.put(BrewingRecipes.NetherWart, Color.Red.add(Color.Purple));
    Ingredients.put(BrewingRecipes.SpiderEye, Color.Green);
    Ingredients.put(BrewingRecipes.FermentedSpiderEye, Color.Blue.add(Color.Green));
    Ingredients.put(BrewingRecipes.MagmaCream, Color.Red.add(Color.Gold));
    Ingredients.put(BrewingRecipes.PhantomMembrane, Color.Purple);
    Ingredients.put(BrewingRecipes.IronChestplate, Color.Gray);
    Ingredients.put(BrewingRecipes.GoldenCarrot, Color.Green);
    Ingredients.put(BrewingRecipes.RottenFlesh, Color.Brown);
    Ingredients.put(BrewingRecipes.Stick, Color.Green);
    Ingredients.put(BrewingRecipes.LapisLazuli, Color.Cobalt);
    Ingredients.put(BrewingRecipes.Diamond, Color.LightBlue);
    Ingredients.put(BrewingRecipes.RabbitFoot, Color.Green);
  }

  private void registerRecipes() {
    BrewingRecipes.RecipeAutomata = new BrewingAutomata();

    // BLUEBERRY
    BrewingRecipes.RecipeAutomata.addRecipe(
        WATER_BREATHING,
        BrewingRecipes.Blueberry,
        BrewingRecipes.RedMushroom,
        BrewingRecipes.Kelp);
    BrewingRecipes.RecipeAutomata.addRecipe(
        LIQUID_FLAME,
        BrewingRecipes.Blueberry,
        BrewingRecipes.Slime,
        BrewingRecipes.BlazePowder,
        BrewingRecipes.BlazePowder);
    BrewingRecipes.RecipeAutomata.addRecipe(
        FREEZING,
        BrewingRecipes.Blueberry,
        BrewingRecipes.GlisteningMelon,
        BrewingRecipes.NetherWart,
        BrewingRecipes.Shroom,
        BrewingRecipes.FrostPumpkin);
    BrewingRecipes.RecipeAutomata.addRecipe(
        HOLY_WATER,
        BrewingRecipes.Blueberry,
        BrewingRecipes.GlisteningMelon,
        BrewingRecipes.GoldenCarrot,
        BrewingRecipes.GlowstoneDust);
    BrewingRecipes.RecipeAutomata.addRecipe(
        POISON,
        BrewingRecipes.Blueberry,
        BrewingRecipes.RottenFlesh,
        BrewingRecipes.SpiderEye);

    // CLOUDBERRY
    BrewingRecipes.RecipeAutomata.addRecipe(
        SATURATION,
        BrewingRecipes.Cloudberry,
        BrewingRecipes.Bone,
        BrewingRecipes.Apple);
    BrewingRecipes.RecipeAutomata.addRecipe(
        FAST_SWIMMING,
        BrewingRecipes.Cloudberry,
        BrewingRecipes.BrownMushroom,
        BrewingRecipes.StoneScale);
    BrewingRecipes.RecipeAutomata.addRecipe(
        LUMBERJACK,
        BrewingRecipes.Cloudberry,
        BrewingRecipes.Stick,
        BrewingRecipes.Acorn);
    BrewingRecipes.RecipeAutomata.addRecipe(
        HASTE,
        BrewingRecipes.Cloudberry,
        BrewingRecipes.LapisLazuli,
        BrewingRecipes.RedstoneDust,
        BrewingRecipes.PufferFish);
    BrewingRecipes.RecipeAutomata.addRecipe(
        STRENGTH,
        BrewingRecipes.Cloudberry,
        BrewingRecipes.GlisteningMelon,
        BrewingRecipes.BlazePowder);
    BrewingRecipes.RecipeAutomata.addRecipe(
        JUMP_BOOST,
        BrewingRecipes.Cloudberry,
        BrewingRecipes.Shroom,
        BrewingRecipes.Slime);

    // BLACKBERRY
    BrewingRecipes.RecipeAutomata.addRecipe(
        NIGHT_VISION,
        BrewingRecipes.Blackberry,
        BrewingRecipes.SpiderEye,
        BrewingRecipes.GoldenCarrot);
//    BrewingRecipes.RecipeAutomata.addRecipe(
//        MANA,
//        BrewingRecipes.Blackberry,
//        BrewingRecipes.CrimsonLeaf,
//        BrewingRecipes.Shroom);
    BrewingRecipes.RecipeAutomata.addRecipe(
        LUCK,
        BrewingRecipes.Blackberry,
        BrewingRecipes.CrimsonLeaf,
        BrewingRecipes.NetherWart,
        BrewingRecipes.Diamond,
        BrewingRecipes.RabbitFoot);
    BrewingRecipes.RecipeAutomata.addRecipe(
        FLIGHT,
        BrewingRecipes.Blackberry,
        BrewingRecipes.NetherWart,
        BrewingRecipes.Shroom,
        BrewingRecipes.ManaFeather);
    BrewingRecipes.RecipeAutomata.addRecipe(
        LEVITATION,
        BrewingRecipes.Blackberry,
        BrewingRecipes.NetherWart,
        BrewingRecipes.EnderPearl,
        BrewingRecipes.PhantomMembrane);
    BrewingRecipes.RecipeAutomata.addRecipe(
        FEATHER_FALLING,
        BrewingRecipes.Blackberry,
        BrewingRecipes.NetherWart,
        BrewingRecipes.CrimsonLeaf);

    // RASPBERRY
    BrewingRecipes.RecipeAutomata.addRecipe(
        HEALING,
        BrewingRecipes.Raspberry,
        BrewingRecipes.RedMushroom,
        BrewingRecipes.GlisteningMelon);
    BrewingRecipes.RecipeAutomata.addRecipe(
        ANTIDOTE,
        BrewingRecipes.Raspberry,
        BrewingRecipes.RedMushroom,
        BrewingRecipes.FermentedSpiderEye);
    BrewingRecipes.RecipeAutomata.addRecipe(
        VAMPIRISM,
        BrewingRecipes.Raspberry,
        BrewingRecipes.Shroom,
        BrewingRecipes.CrimsonLeaf,
        BrewingRecipes.Shadowveil);
    BrewingRecipes.RecipeAutomata.addRecipe(
        BERSERK,
        BrewingRecipes.Raspberry,
        BrewingRecipes.Shroom,
        BrewingRecipes.NetherWart,
        BrewingRecipes.GlisteningMelon,
        BrewingRecipes.CrimsonwoodBerry);
    BrewingRecipes.RecipeAutomata.addRecipe(
        STONE_SKIN,
        BrewingRecipes.Raspberry,
        BrewingRecipes.Shroom,
        BrewingRecipes.Bone,
        BrewingRecipes.IronChestplate);
    BrewingRecipes.RecipeAutomata.addRecipe(
        FIRE_RESISTANCE,
        BrewingRecipes.Raspberry,
        BrewingRecipes.BrownMushroom,
        BrewingRecipes.MagmaCream);
  }

  private void registerAdditionalEdges() {
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Blackberry,
//            BrewingRecipes.NetherWart,
//            BrewingRecipes.SpiderEye),
//        List.of(
//            BrewingRecipes.Raspberry,
//            BrewingRecipes.Apple,
//            BrewingRecipes.SpiderEye,
//            BrewingRecipes.Cloudberry),
//        BrewingRecipes.Shroom);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Blackberry,
//            BrewingRecipes.NetherWart,
//            BrewingRecipes.Shadowveil,
//            BrewingRecipes.Raspberry),
//        List.of(BrewingRecipes.Blackberry, BrewingRecipes.Redstone, BrewingRecipes.Shroom, BrewingRecipes.Blueberry),
//        BrewingRecipes.Redstone);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Blackberry,
//            BrewingRecipes.Redstone,
//            BrewingRecipes.Bone,
//            BrewingRecipes.Raspberry),
//        List.of(
//            BrewingRecipes.Blackberry,
//            BrewingRecipes.Redstone,
//            BrewingRecipes.Shroom),
//        BrewingRecipes.CrimsonwoodBerry);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Blackberry,
//            BrewingRecipes.Redstone,
//            BrewingRecipes.Bone),
//        List.of(BrewingRecipes.Raspberry),
//        BrewingRecipes.Kelp);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Blackberry,
//            BrewingRecipes.Redstone,
//            BrewingRecipes.Shadowveil),
//        List.of(BrewingRecipes.Raspberry),
//        BrewingRecipes.Raspberry);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Raspberry,
//            BrewingRecipes.CrimsonLeaf,
//            BrewingRecipes.Cloudberry),
//        List.of(
//            BrewingRecipes.Blackberry,
//            BrewingRecipes.Redstone,
//            BrewingRecipes.Shadowveil,
//            BrewingRecipes.Cloudberry),
//        BrewingRecipes.Allium);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Raspberry,
//            BrewingRecipes.CrimsonLeaf,
//            BrewingRecipes.Kelp),
//        List.of(
//            BrewingRecipes.Raspberry,
//            BrewingRecipes.CrimsonLeaf,
//            BrewingRecipes.Slime,
//            BrewingRecipes.GlowstoneDust),
//        BrewingRecipes.GlowstoneDust);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Raspberry,
//            BrewingRecipes.Apple,
//            BrewingRecipes.Shroom,
//            BrewingRecipes.Raspberry),
//        List.of(
//            BrewingRecipes.Raspberry,
//            BrewingRecipes.CrimsonLeaf,
//            BrewingRecipes.Cloudberry,
//            BrewingRecipes.Blackberry),
//        BrewingRecipes.Blackberry);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Raspberry,
//            BrewingRecipes.Apple,
//            BrewingRecipes.SpiderEye),
//        List.of(
//            BrewingRecipes.Raspberry,
//            BrewingRecipes.CrimsonLeaf,
//            BrewingRecipes.Shadowveil),
//        BrewingRecipes.Blueberry);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Raspberry,
//            BrewingRecipes.Apple,
//            BrewingRecipes.Shroom),
//        List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Shadowveil),
//        BrewingRecipes.Blackberry);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(BrewingRecipes.Raspberry, BrewingRecipes.CrimsonLeaf),
//        List.of(
//            BrewingRecipes.Cloudberry,
//            BrewingRecipes.Shadowveil,
//            BrewingRecipes.Blueberry),
//        BrewingRecipes.Blackberry);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Shadowveil, BrewingRecipes.Blueberry, BrewingRecipes.Kelp),
//        List.of(BrewingRecipes.Raspberry, BrewingRecipes.Apple, BrewingRecipes.SpiderEye),
//        BrewingRecipes.FermentedSpiderEye);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(BrewingRecipes.Cloudberry),
//        List.of(BrewingRecipes.Raspberry, BrewingRecipes.Apple),
//        BrewingRecipes.Apple);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Shadowveil, BrewingRecipes.Blueberry, BrewingRecipes.Kelp),
//        List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Shadowveil, BrewingRecipes.Blueberry, BrewingRecipes.Slime),
//        BrewingRecipes.Raspberry);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Cloudberry,
//            BrewingRecipes.Shadowveil,
//            BrewingRecipes.Blueberry,
//            BrewingRecipes.NetherWart),
//        List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Redstone),
//        BrewingRecipes.BlazePowder);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Shadowveil),
//        List.of(
//            BrewingRecipes.Cloudberry,
//            BrewingRecipes.Redstone,
//            BrewingRecipes.PufferFish),
//        BrewingRecipes.Bone);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Cloudberry,
//            BrewingRecipes.Redstone,
//            BrewingRecipes.PufferFish,
//            BrewingRecipes.BrownMushroom),
//        List.of(
//            BrewingRecipes.Cloudberry,
//            BrewingRecipes.Shadowveil,
//            BrewingRecipes.Blueberry),
//        BrewingRecipes.Allium);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(BrewingRecipes.Blueberry, BrewingRecipes.Shadowveil),
//        List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Redstone),
//        BrewingRecipes.Cloudberry);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(BrewingRecipes.Blueberry, BrewingRecipes.Shadowveil, BrewingRecipes.Shroom, BrewingRecipes.ChorusPlant),
//        List.of(BrewingRecipes.Cloudberry, BrewingRecipes.Redstone, BrewingRecipes.PufferFish),
//        BrewingRecipes.BrownMushroom);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Cloudberry,
//            BrewingRecipes.Redstone,
//            BrewingRecipes.PufferFish,
//            BrewingRecipes.NetherWart,
//            BrewingRecipes.CrimsonLeaf),
//        List.of(
//            BrewingRecipes.Blueberry,
//            BrewingRecipes.Shadowveil,
//            BrewingRecipes.CrimsonLeaf,
//            BrewingRecipes.BlazePowder,
//            BrewingRecipes.Slime),
//        BrewingRecipes.BlazePowder);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Blueberry,
//            BrewingRecipes.Shadowveil,
//            BrewingRecipes.Shroom,
//            BrewingRecipes.EnderPearl,
//            BrewingRecipes.NetherWart),
//        List.of(
//            BrewingRecipes.Blueberry,
//            BrewingRecipes.Shadowveil,
//            BrewingRecipes.Shroom,
//            BrewingRecipes.ChorusPlant,
//            BrewingRecipes.CrimsonwoodBerry),
//        BrewingRecipes.CrimsonwoodBerry);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Blueberry,
//            BrewingRecipes.Shadowveil,
//            BrewingRecipes.CrimsonLeaf,
//            BrewingRecipes.BlazePowder),
//        List.of(
//            BrewingRecipes.Blueberry,
//            BrewingRecipes.Shadowveil,
//            BrewingRecipes.Shroom,
//            BrewingRecipes.ChorusPlant),
//        BrewingRecipes.ChorusPlant);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Blueberry,
//            BrewingRecipes.Shadowveil,
//            BrewingRecipes.CrimsonLeaf,
//            BrewingRecipes.BlazePowder),
//        List.of(
//            BrewingRecipes.Blueberry,
//            BrewingRecipes.BrownMushroom,
//            BrewingRecipes.GlowstoneDust,
//            BrewingRecipes.Cloudberry),
//        BrewingRecipes.GlowstoneDust);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Blueberry,
//            BrewingRecipes.BrownMushroom,
//            BrewingRecipes.Kelp,
//            BrewingRecipes.Bone),
//        List.of(
//            BrewingRecipes.Blueberry,
//            BrewingRecipes.BrownMushroom,
//            BrewingRecipes.GlowstoneDust,
//            BrewingRecipes.Cloudberry,
//            BrewingRecipes.BlazePowder),
//        BrewingRecipes.GlowstoneDust);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Blueberry,
//            BrewingRecipes.BrownMushroom,
//            BrewingRecipes.Kelp),
//        List.of(BrewingRecipes.Blackberry, BrewingRecipes.NetherWart),
//        BrewingRecipes.GlowstoneDust);
//    BrewingRecipes.RecipeAutomata.addEdge(
//        List.of(
//            BrewingRecipes.Blackberry,
//            BrewingRecipes.NetherWart,
//            BrewingRecipes.SpiderEye),
//        List.of(
//            BrewingRecipes.Blueberry,
//            BrewingRecipes.NetherWart,
//            BrewingRecipes.Kelp,
//            BrewingRecipes.Bone),
//        BrewingRecipes.Shadowveil);
  }

}
