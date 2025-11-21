package faceless.artent.potions.bootstrap;

import com.google.common.collect.ImmutableList;
import faceless.artent.core.math.Color;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.objects.ModRegistries;
import faceless.artent.potions.recipes.PotionRecipe;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.List;

import static faceless.artent.potions.ArtentPotions.LOGGER;
import static faceless.artent.potions.bootstrap.PotionEffectsBootstrap.*;


public class PotionRecipeBootstrap {
  public static void bootstrap(Registerable<PotionRecipe> recipeRegistry) {
    LOGGER.info("PotionRecipe bootstrap");
    // BLUEBERRY
    registerRecipe(
        recipeRegistry,
        "water_breathing",
        WATER_BREATHING,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.RedMushroom,
        BrewingIngredientsBootstrap.Kelp);

    registerRecipe(
        recipeRegistry,
        "liquid_flame",
        LIQUID_FLAME,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.Slime,
        BrewingIngredientsBootstrap.BlazePowder,
        BrewingIngredientsBootstrap.BlazePowder);

    registerRecipe(
        recipeRegistry,
        "freezing",
        FREEZING,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.GlisteningMelon,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.FrostPumpkin);

    registerRecipe(
        recipeRegistry,
        "holy_water",
        HOLY_WATER,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.GlisteningMelon,
        BrewingIngredientsBootstrap.GoldenCarrot,
        BrewingIngredientsBootstrap.GlowstoneDust);

    registerRecipe(
        recipeRegistry,
        "poison",
        POISON,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.RottenFlesh,
        BrewingIngredientsBootstrap.SpiderEye);

    // CLOUDBERRY

    registerRecipe(
        recipeRegistry,
        "saturation",
        SATURATION,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.Bone,
        BrewingIngredientsBootstrap.Apple);

    registerRecipe(
        recipeRegistry,
        "fast_swimming",
        FAST_SWIMMING,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.BrownMushroom,
        BrewingIngredientsBootstrap.StoneScale);

    registerRecipe(
        recipeRegistry,
        "lumberjack",
        LUMBERJACK,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.Stick,
        BrewingIngredientsBootstrap.Acorn);

    registerRecipe(
        recipeRegistry,
        "haste",
        HASTE,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.LapisLazuli,
        BrewingIngredientsBootstrap.RedstoneDust,
        BrewingIngredientsBootstrap.PufferFish);

    registerRecipe(
        recipeRegistry,
        "strength",
        STRENGTH,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.GlisteningMelon,
        BrewingIngredientsBootstrap.BlazePowder);

    registerRecipe(
        recipeRegistry,
        "jump_boost",
        JUMP_BOOST,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.Slime);

    // BLACKBERRY

    registerRecipe(
        recipeRegistry,
        "night_vision",
        NIGHT_VISION,
        BrewingIngredientsBootstrap.Blackberry,
        BrewingIngredientsBootstrap.SpiderEye,
        BrewingIngredientsBootstrap.GoldenCarrot);
// TODO add to resources of magic module
//    BrewingRecipes.RecipeAutomata.addRecipe(
//        MANA,
//        BrewingRecipes.Blackberry,
//        BrewingRecipes.CrimsonLeaf,
//        BrewingRecipes.Shroom);

    registerRecipe(
        recipeRegistry,
        "fortune",
        FORTUNE,
        BrewingIngredientsBootstrap.Blackberry,
        BrewingIngredientsBootstrap.CrimsonLeaf,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.Diamond,
        BrewingIngredientsBootstrap.RabbitFoot);

    registerRecipe(
        recipeRegistry,
        "flight",
        FLIGHT,
        BrewingIngredientsBootstrap.Blackberry,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.ManaFeather);

    registerRecipe(
        recipeRegistry,
        "levitation",
        LEVITATION,
        BrewingIngredientsBootstrap.Blackberry,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.EnderPearl,
        BrewingIngredientsBootstrap.PhantomMembrane);

    registerRecipe(
        recipeRegistry,
        "feather_falling",
        FEATHER_FALLING,
        BrewingIngredientsBootstrap.Blackberry,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.CrimsonLeaf);

    // RASPBERRY

    registerRecipe(
        recipeRegistry,
        "healing",
        HEALING,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.RedMushroom,
        BrewingIngredientsBootstrap.GlisteningMelon);

    registerRecipe(
        recipeRegistry,
        "antidote",
        ANTIDOTE,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.RedMushroom,
        BrewingIngredientsBootstrap.FermentedSpiderEye);

    registerRecipe(
        recipeRegistry,
        "vampirism",
        VAMPIRISM,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.CrimsonLeaf,
        BrewingIngredientsBootstrap.Shadowveil);

    registerRecipe(
        recipeRegistry,
        "berserk",
        BERSERK,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.GlisteningMelon,
        BrewingIngredientsBootstrap.CrimsonwoodBerry);

    registerRecipe(
        recipeRegistry,
        "stone_skin",
        STONE_SKIN,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.Bone,
        BrewingIngredientsBootstrap.IronChestplate);

    registerRecipe(
        recipeRegistry,
        "fire_resistance",
        FIRE_RESISTANCE,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.BrownMushroom,
        BrewingIngredientsBootstrap.MagmaCream);
  }

  public static void registerRecipe(
      Registerable<PotionRecipe> recipeRegistry,
      String key,
      AlchemicalPotion potion,
      BrewingIngredient... ingredients) {
    var recipe = new PotionRecipe(ImmutableList.copyOf(ingredients), potion);

    var registryKey = RegistryKey.of(
        ModRegistries.POTION_RECIPES_REGISTRY_KEY,
        Identifier.of(ArtentPotions.MODID, key));
    potion.color = getColorFromIngredients(ImmutableList.copyOf(ingredients));
    recipeRegistry.register(registryKey, recipe);
  }

  public static void registerRecipe(
      Registerable<PotionRecipe> recipeRegistry,
      String key,
      AlchemicalPotion[] potion,
      BrewingIngredient... ingredients) {
    registerRecipe(recipeRegistry, key, potion[0], ingredients);
  }

  public static Color getColorFromIngredients(List<BrewingIngredient> ingredients) {
    var color = Color.Blue;
    for (var ingredient : ingredients) {
      color = color.add(ingredient.color);
    }
    return color;
  }
}
