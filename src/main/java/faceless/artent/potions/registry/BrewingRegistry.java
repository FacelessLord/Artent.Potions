package faceless.artent.potions.registry;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.BrewingAutomata;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.objects.BrewingIngredients;

import java.util.Hashtable;

import static faceless.artent.potions.objects.AlchemicalPotions.*;

public class BrewingRegistry implements IRegistry {
  public static BrewingAutomata RecipeAutomata;
  public static final Hashtable<String, BrewingIngredient> Ingredients = new Hashtable<>();

  @Override
  public void register() {
    registerIngredients();
    registerRecipes();
  }

  private void registerIngredients() {
    registerIngredient(BrewingIngredients.DriedShroom);
    registerIngredient(BrewingIngredients.Shadowveil);
    registerIngredient(BrewingIngredients.CrimsonwoodBerry);
    registerIngredient(BrewingIngredients.CrimsonLeaf);
    registerIngredient(BrewingIngredients.Blackberry);
    registerIngredient(BrewingIngredients.Blueberry);
    registerIngredient(BrewingIngredients.Cloudberry);
    registerIngredient(BrewingIngredients.Raspberry);
    registerIngredient(BrewingIngredients.StoneScale);
    registerIngredient(BrewingIngredients.Acorn);
    registerIngredient(BrewingIngredients.ManaFeather);
    registerIngredient(BrewingIngredients.FrostPumpkin);

    registerIngredient(BrewingIngredients.Bone);
    registerIngredient(BrewingIngredients.GlowstoneDust);
    registerIngredient(BrewingIngredients.RedstoneDust);
    registerIngredient(BrewingIngredients.Sugar);
    registerIngredient(BrewingIngredients.Apple);
    registerIngredient(BrewingIngredients.DriedBrownMushroom);
    registerIngredient(BrewingIngredients.DriedRedMushroom);
    registerIngredient(BrewingIngredients.ChorusPlant);
    registerIngredient(BrewingIngredients.BlazePowder);
    registerIngredient(BrewingIngredients.CocoaBeans);
    registerIngredient(BrewingIngredients.EnderPearl);
    registerIngredient(BrewingIngredients.GlisteningMelon);
    registerIngredient(BrewingIngredients.Kelp);
    registerIngredient(BrewingIngredients.Slime);
    registerIngredient(BrewingIngredients.PufferFish);
    registerIngredient(BrewingIngredients.DragonBreath);
    registerIngredient(BrewingIngredients.NetherWart);
    registerIngredient(BrewingIngredients.SpiderEye);
    registerIngredient(BrewingIngredients.FermentedSpiderEye);
    registerIngredient(BrewingIngredients.MagmaCream);
    registerIngredient(BrewingIngredients.PhantomMembrane);
    registerIngredient(BrewingIngredients.IronChestplate);
    registerIngredient(BrewingIngredients.GoldenCarrot);
    registerIngredient(BrewingIngredients.RottenFlesh);
    registerIngredient(BrewingIngredients.Stick);
    registerIngredient(BrewingIngredients.LapisLazuli);
    registerIngredient(BrewingIngredients.Diamond);
    registerIngredient(BrewingIngredients.RabbitFoot);
  }

  private void registerIngredient(BrewingIngredient ingredient){
    Ingredients.put(ingredient.id(), ingredient);
  }

  public static BrewingIngredient getIngredient(String id){
    return Ingredients.getOrDefault(id, null);
  }

  private void registerRecipes() {
    RecipeAutomata = new BrewingAutomata();

    // BLUEBERRY
    RecipeAutomata.addRecipe(
        WATER_BREATHING,
        BrewingIngredients.Blueberry,
        BrewingIngredients.DriedRedMushroom,
        BrewingIngredients.Kelp);
    RecipeAutomata.addRecipe(
        LIQUID_FLAME,
        BrewingIngredients.Blueberry,
        BrewingIngredients.Slime,
        BrewingIngredients.BlazePowder,
        BrewingIngredients.BlazePowder);
    RecipeAutomata.addRecipe(
        FREEZING,
        BrewingIngredients.Blueberry,
        BrewingIngredients.GlisteningMelon,
        BrewingIngredients.NetherWart,
        BrewingIngredients.DriedShroom,
        BrewingIngredients.FrostPumpkin);
    RecipeAutomata.addRecipe(
        SANCTITY,
        BrewingIngredients.Blueberry,
        BrewingIngredients.GlisteningMelon,
        BrewingIngredients.GoldenCarrot,
        BrewingIngredients.GlowstoneDust);
    RecipeAutomata.addRecipe(
        POISON,
        BrewingIngredients.Blueberry,
        BrewingIngredients.RottenFlesh,
        BrewingIngredients.SpiderEye);

    // CLOUDBERRY
    RecipeAutomata.addRecipe(
        SATURATION,
        BrewingIngredients.Cloudberry,
        BrewingIngredients.Bone,
        BrewingIngredients.Apple);
    RecipeAutomata.addRecipe(
        SPEED,
        BrewingIngredients.Cloudberry,
        BrewingIngredients.Sugar,
        BrewingIngredients.RedstoneDust);
    RecipeAutomata.addRecipe(
        FAST_SWIMMING,
        BrewingIngredients.Cloudberry,
        BrewingIngredients.DriedBrownMushroom,
        BrewingIngredients.StoneScale);
    RecipeAutomata.addRecipe(
        LUMBERJACK,
        BrewingIngredients.Cloudberry,
        BrewingIngredients.Stick,
        BrewingIngredients.Acorn);
    RecipeAutomata.addRecipe(
        HASTE,
        BrewingIngredients.Cloudberry,
        BrewingIngredients.LapisLazuli,
        BrewingIngredients.RedstoneDust,
        BrewingIngredients.PufferFish);
    RecipeAutomata.addRecipe(
        STRENGTH,
        BrewingIngredients.Cloudberry,
        BrewingIngredients.GlisteningMelon,
        BrewingIngredients.BlazePowder);
    RecipeAutomata.addRecipe(
        JUMP_BOOST,
        BrewingIngredients.Cloudberry,
        BrewingIngredients.DriedShroom,
        BrewingIngredients.Slime);

    // BLACKBERRY
    RecipeAutomata.addRecipe(
        NIGHT_VISION,
        BrewingIngredients.Blackberry,
        BrewingIngredients.SpiderEye,
        BrewingIngredients.GoldenCarrot);
//    BrewingRecipes.RecipeAutomata.addRecipe(
//        MANA,
//        BrewingRecipes.Blackberry,
//        BrewingRecipes.CrimsonLeaf,
//        BrewingRecipes.Shroom);
    RecipeAutomata.addRecipe(
        FORTUNE,
        BrewingIngredients.Blackberry,
        BrewingIngredients.CrimsonLeaf,
        BrewingIngredients.NetherWart,
        BrewingIngredients.Diamond,
        BrewingIngredients.RabbitFoot);
    RecipeAutomata.addRecipe(
        FLIGHT,
        BrewingIngredients.Blackberry,
        BrewingIngredients.NetherWart,
        BrewingIngredients.DriedShroom,
        BrewingIngredients.ManaFeather);
    RecipeAutomata.addRecipe(
        LEVITATION,
        BrewingIngredients.Blackberry,
        BrewingIngredients.NetherWart,
        BrewingIngredients.EnderPearl,
        BrewingIngredients.PhantomMembrane);
    RecipeAutomata.addRecipe(
        FEATHER_FALLING,
        BrewingIngredients.Blackberry,
        BrewingIngredients.NetherWart,
        BrewingIngredients.CrimsonLeaf);

    // RASPBERRY
    RecipeAutomata.addRecipe(
        HEALING,
        BrewingIngredients.Raspberry,
        BrewingIngredients.DriedRedMushroom,
        BrewingIngredients.GlisteningMelon);
    RecipeAutomata.addRecipe(
        ANTIDOTE,
        BrewingIngredients.Raspberry,
        BrewingIngredients.DriedRedMushroom,
        BrewingIngredients.FermentedSpiderEye);
    RecipeAutomata.addRecipe(
        VAMPIRISM,
        BrewingIngredients.Raspberry,
        BrewingIngredients.DriedShroom,
        BrewingIngredients.CrimsonLeaf,
        BrewingIngredients.Shadowveil);
    RecipeAutomata.addRecipe(
        BERSERK,
        BrewingIngredients.Raspberry,
        BrewingIngredients.DriedShroom,
        BrewingIngredients.NetherWart,
        BrewingIngredients.GlisteningMelon,
        BrewingIngredients.CrimsonwoodBerry);
    RecipeAutomata.addRecipe(
        STONE_SKIN,
        BrewingIngredients.Raspberry,
        BrewingIngredients.DriedShroom,
        BrewingIngredients.Bone,
        BrewingIngredients.IronChestplate);
    RecipeAutomata.addRecipe(
        FIRE_RESISTANCE,
        BrewingIngredients.Raspberry,
        BrewingIngredients.DriedBrownMushroom,
        BrewingIngredients.MagmaCream);
    RecipeAutomata.addRecipe(
        HOT_CHOCOLATE,
        BrewingIngredients.Raspberry,
        BrewingIngredients.BlazePowder,
        BrewingIngredients.CocoaBeans);
  }
}
