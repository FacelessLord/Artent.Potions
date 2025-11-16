package faceless.artent.potions.registry;

import faceless.artent.core.math.Color;
import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.BrewingAutomata;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.objects.BrewingIngredients;

import java.util.Hashtable;

import static faceless.artent.potions.objects.AlchemicalPotions.*;

public class BrewingRegistry implements IRegistry {
  public static final Hashtable<BrewingIngredient, Color> Ingredients = new Hashtable<>();

  @Override
  public void register() {
    registerIngredients();
    registerRecipes();
  }

  private void registerIngredients() {
    Ingredients.put(BrewingIngredients.Shroom, Color.Cobalt);
    Ingredients.put(BrewingIngredients.Shadowveil, Color.Purple.add(Color.Red));
    Ingredients.put(BrewingIngredients.CrimsonwoodBerry, Color.Red);
    Ingredients.put(BrewingIngredients.CrimsonLeaf, Color.Gray);
    Ingredients.put(BrewingIngredients.Blackberry, Color.Purple);
    Ingredients.put(BrewingIngredients.Blueberry, Color.Blue);
    Ingredients.put(BrewingIngredients.Cloudberry, Color.Gold);
    Ingredients.put(BrewingIngredients.Raspberry, Color.Red);
    Ingredients.put(BrewingIngredients.StoneScale, Color.Cobalt);
    Ingredients.put(BrewingIngredients.Acorn, Color.Brown.add(Color.White));
    Ingredients.put(BrewingIngredients.ManaFeather, Color.Purple);
    Ingredients.put(BrewingIngredients.FrostPumpkin, Color.LightBlue);

    Ingredients.put(BrewingIngredients.Bone, Color.White);
    Ingredients.put(BrewingIngredients.GlowstoneDust, Color.Orange);
    Ingredients.put(BrewingIngredients.RedstoneDust, Color.Red);
    Ingredients.put(BrewingIngredients.Apple, Color.Red);
    Ingredients.put(BrewingIngredients.BrownMushroom, Color.Brown);
    Ingredients.put(BrewingIngredients.RedMushroom, Color.Red);
    Ingredients.put(BrewingIngredients.ChorusPlant, Color.Purple);
    Ingredients.put(BrewingIngredients.BlazePowder, Color.Orange);
    Ingredients.put(BrewingIngredients.EnderPearl, Color.Cyan);
    Ingredients.put(BrewingIngredients.GlisteningMelon, Color.Gold.add(Color.Red));
    Ingredients.put(BrewingIngredients.Kelp, Color.Cyan);
    Ingredients.put(BrewingIngredients.Slime, Color.Green);
    Ingredients.put(BrewingIngredients.PufferFish, Color.Yellow);
    Ingredients.put(BrewingIngredients.DragonBreath, Color.Pink.add(Color.Purple));
    Ingredients.put(BrewingIngredients.NetherWart, Color.Red.add(Color.Purple));
    Ingredients.put(BrewingIngredients.SpiderEye, Color.Green);
    Ingredients.put(BrewingIngredients.FermentedSpiderEye, Color.Blue.add(Color.Green));
    Ingredients.put(BrewingIngredients.MagmaCream, Color.Red.add(Color.Gold));
    Ingredients.put(BrewingIngredients.PhantomMembrane, Color.Purple);
    Ingredients.put(BrewingIngredients.IronChestplate, Color.Gray);
    Ingredients.put(BrewingIngredients.GoldenCarrot, Color.Green);
    Ingredients.put(BrewingIngredients.RottenFlesh, Color.Brown);
    Ingredients.put(BrewingIngredients.Stick, Color.Green);
    Ingredients.put(BrewingIngredients.LapisLazuli, Color.Cobalt);
    Ingredients.put(BrewingIngredients.Diamond, Color.LightBlue);
    Ingredients.put(BrewingIngredients.RabbitFoot, Color.Green);
  }

  private void registerRecipes() {
    BrewingIngredients.RecipeAutomata = new BrewingAutomata();

    // BLUEBERRY
    BrewingIngredients.RecipeAutomata.addRecipe(
        WATER_BREATHING,
        BrewingIngredients.Blueberry,
        BrewingIngredients.RedMushroom,
        BrewingIngredients.Kelp);
    BrewingIngredients.RecipeAutomata.addRecipe(
        LIQUID_FLAME,
        BrewingIngredients.Blueberry,
        BrewingIngredients.Slime,
        BrewingIngredients.BlazePowder,
        BrewingIngredients.BlazePowder);
    BrewingIngredients.RecipeAutomata.addRecipe(
        FREEZING,
        BrewingIngredients.Blueberry,
        BrewingIngredients.GlisteningMelon,
        BrewingIngredients.NetherWart,
        BrewingIngredients.Shroom,
        BrewingIngredients.FrostPumpkin);
    BrewingIngredients.RecipeAutomata.addRecipe(
        HOLY_WATER,
        BrewingIngredients.Blueberry,
        BrewingIngredients.GlisteningMelon,
        BrewingIngredients.GoldenCarrot,
        BrewingIngredients.GlowstoneDust);
    BrewingIngredients.RecipeAutomata.addRecipe(
        POISON,
        BrewingIngredients.Blueberry,
        BrewingIngredients.RottenFlesh,
        BrewingIngredients.SpiderEye);

    // CLOUDBERRY
    BrewingIngredients.RecipeAutomata.addRecipe(
        SATURATION,
        BrewingIngredients.Cloudberry,
        BrewingIngredients.Bone,
        BrewingIngredients.Apple);
    BrewingIngredients.RecipeAutomata.addRecipe(
        FAST_SWIMMING,
        BrewingIngredients.Cloudberry,
        BrewingIngredients.BrownMushroom,
        BrewingIngredients.StoneScale);
    BrewingIngredients.RecipeAutomata.addRecipe(
        LUMBERJACK,
        BrewingIngredients.Cloudberry,
        BrewingIngredients.Stick,
        BrewingIngredients.Acorn);
    BrewingIngredients.RecipeAutomata.addRecipe(
        HASTE,
        BrewingIngredients.Cloudberry,
        BrewingIngredients.LapisLazuli,
        BrewingIngredients.RedstoneDust,
        BrewingIngredients.PufferFish);
    BrewingIngredients.RecipeAutomata.addRecipe(
        STRENGTH,
        BrewingIngredients.Cloudberry,
        BrewingIngredients.GlisteningMelon,
        BrewingIngredients.BlazePowder);
    BrewingIngredients.RecipeAutomata.addRecipe(
        JUMP_BOOST,
        BrewingIngredients.Cloudberry,
        BrewingIngredients.Shroom,
        BrewingIngredients.Slime);

    // BLACKBERRY
    BrewingIngredients.RecipeAutomata.addRecipe(
        NIGHT_VISION,
        BrewingIngredients.Blackberry,
        BrewingIngredients.SpiderEye,
        BrewingIngredients.GoldenCarrot);
//    BrewingRecipes.RecipeAutomata.addRecipe(
//        MANA,
//        BrewingRecipes.Blackberry,
//        BrewingRecipes.CrimsonLeaf,
//        BrewingRecipes.Shroom);
    BrewingIngredients.RecipeAutomata.addRecipe(
        FORTUNE,
        BrewingIngredients.Blackberry,
        BrewingIngredients.CrimsonLeaf,
        BrewingIngredients.NetherWart,
        BrewingIngredients.Diamond,
        BrewingIngredients.RabbitFoot);
    BrewingIngredients.RecipeAutomata.addRecipe(
        FLIGHT,
        BrewingIngredients.Blackberry,
        BrewingIngredients.NetherWart,
        BrewingIngredients.Shroom,
        BrewingIngredients.ManaFeather);
    BrewingIngredients.RecipeAutomata.addRecipe(
        LEVITATION,
        BrewingIngredients.Blackberry,
        BrewingIngredients.NetherWart,
        BrewingIngredients.EnderPearl,
        BrewingIngredients.PhantomMembrane);
    BrewingIngredients.RecipeAutomata.addRecipe(
        FEATHER_FALLING,
        BrewingIngredients.Blackberry,
        BrewingIngredients.NetherWart,
        BrewingIngredients.CrimsonLeaf);

    // RASPBERRY
    BrewingIngredients.RecipeAutomata.addRecipe(
        HEALING,
        BrewingIngredients.Raspberry,
        BrewingIngredients.RedMushroom,
        BrewingIngredients.GlisteningMelon);
    BrewingIngredients.RecipeAutomata.addRecipe(
        ANTIDOTE,
        BrewingIngredients.Raspberry,
        BrewingIngredients.RedMushroom,
        BrewingIngredients.FermentedSpiderEye);
    BrewingIngredients.RecipeAutomata.addRecipe(
        VAMPIRISM,
        BrewingIngredients.Raspberry,
        BrewingIngredients.Shroom,
        BrewingIngredients.CrimsonLeaf,
        BrewingIngredients.Shadowveil);
    BrewingIngredients.RecipeAutomata.addRecipe(
        BERSERK,
        BrewingIngredients.Raspberry,
        BrewingIngredients.Shroom,
        BrewingIngredients.NetherWart,
        BrewingIngredients.GlisteningMelon,
        BrewingIngredients.CrimsonwoodBerry);
    BrewingIngredients.RecipeAutomata.addRecipe(
        STONE_SKIN,
        BrewingIngredients.Raspberry,
        BrewingIngredients.Shroom,
        BrewingIngredients.Bone,
        BrewingIngredients.IronChestplate);
    BrewingIngredients.RecipeAutomata.addRecipe(
        FIRE_RESISTANCE,
        BrewingIngredients.Raspberry,
        BrewingIngredients.BrownMushroom,
        BrewingIngredients.MagmaCream);
  }
}
