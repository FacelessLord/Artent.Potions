package faceless.artent.potions.bootstrap;

import faceless.artent.core.math.Color;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.objects.ModRegistries;
import faceless.artent.potions.recipes.PotionEnhancementRecipe;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.List;

import static faceless.artent.potions.ArtentPotions.LOGGER;
import static faceless.artent.potions.bootstrap.PotionEffectsBootstrap.*;

public class PotionEnhancementRecipeBootstrap {
  public static void bootstrap(Registerable<PotionEnhancementRecipe> enhancementRecipeRegistry) {
    LOGGER.info("PotionEnhancementRecipe bootstrap");
    // BLUEBERRY

    registerRecipe(
        enhancementRecipeRegistry,
        "liquid_flame",
        LIQUID_FLAME,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.Slime,
        BrewingIngredientsBootstrap.BlazePowder,
        BrewingIngredientsBootstrap.BlazePowder);

    registerRecipe(
        enhancementRecipeRegistry,
        "freezing",
        FREEZING,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.GlisteningMelon,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.FrostPumpkin);

    registerRecipe(
        enhancementRecipeRegistry,
        "holy_water",
        HOLY_WATER,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.GlisteningMelon,
        BrewingIngredientsBootstrap.GoldenCarrot,
        BrewingIngredientsBootstrap.GlowstoneDust);

    registerRecipe(
        enhancementRecipeRegistry,
        "poison",
        POISON,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.RottenFlesh,
        BrewingIngredientsBootstrap.SpiderEye);

    // CLOUDBERRY

    registerRecipe(
        enhancementRecipeRegistry,
        "saturation",
        SATURATION,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.Bone,
        BrewingIngredientsBootstrap.Apple);

    registerRecipe(
        enhancementRecipeRegistry,
        "fast_swimming",
        FAST_SWIMMING,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.BrownMushroom,
        BrewingIngredientsBootstrap.StoneScale);

    registerRecipe(
        enhancementRecipeRegistry,
        "lumberjack",
        LUMBERJACK,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.Stick,
        BrewingIngredientsBootstrap.Acorn);

    registerRecipe(
        enhancementRecipeRegistry,
        "haste",
        HASTE,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.LapisLazuli,
        BrewingIngredientsBootstrap.RedstoneDust,
        BrewingIngredientsBootstrap.PufferFish);

    registerRecipe(
        enhancementRecipeRegistry,
        "strength",
        STRENGTH,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.GlisteningMelon,
        BrewingIngredientsBootstrap.BlazePowder);

    registerRecipe(
        enhancementRecipeRegistry,
        "jump_boost",
        JUMP_BOOST,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.Slime);

    // BLACKBERRY

// TODO add to resources of magic module
//    BrewingRecipes.RecipeAutomata.addRecipe(
//        MANA,
//        BrewingRecipes.Blackberry,
//        BrewingRecipes.CrimsonLeaf,
//        BrewingRecipes.Shroom);

    registerRecipe(
        enhancementRecipeRegistry,
        "fortune",
        FORTUNE,
        BrewingIngredientsBootstrap.Blackberry,
        BrewingIngredientsBootstrap.CrimsonLeaf,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.Diamond,
        BrewingIngredientsBootstrap.RabbitFoot);

    registerRecipe(
        enhancementRecipeRegistry,
        "levitation",
        LEVITATION,
        BrewingIngredientsBootstrap.Blackberry,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.EnderPearl,
        BrewingIngredientsBootstrap.PhantomMembrane);

    // RASPBERRY

    registerRecipe(
        enhancementRecipeRegistry,
        "healing",
        HEALING,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.RedMushroom,
        BrewingIngredientsBootstrap.GlisteningMelon);

    registerRecipe(
        enhancementRecipeRegistry,
        "antidote",
        ANTIDOTE,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.RedMushroom,
        BrewingIngredientsBootstrap.FermentedSpiderEye);

    registerRecipe(
        enhancementRecipeRegistry,
        "vampirism",
        VAMPIRISM,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.CrimsonLeaf,
        BrewingIngredientsBootstrap.Shadowveil);

    registerRecipe(
        enhancementRecipeRegistry,
        "berserk",
        BERSERK,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.GlisteningMelon,
        BrewingIngredientsBootstrap.CrimsonwoodBerry);

    registerRecipe(
        enhancementRecipeRegistry,
        "stone_skin",
        STONE_SKIN,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.Bone,
        BrewingIngredientsBootstrap.IronChestplate);
  }

  public static void registerRecipe(
      Registerable<PotionEnhancementRecipe> enhancementRecipeRegistry,
      String key,
      AlchemicalPotion[] potions,
      BrewingIngredient... ingredients) {
    var previousPotion = potions[0];

    for (int i = 1; i < potions.length; i++) {
      var enhancementRecipe = new PotionEnhancementRecipe(
          previousPotion,
          ingredients[ingredients.length - 1],
          potions[i]);
      potions[i].color = potions[i].color.add(ingredients[ingredients.length - 1].color);
      previousPotion = potions[i];
      var registryKey2 = RegistryKey.of(
          ModRegistries.POTION_ENHANCEMENT_RECIPE_REGISTRY_KEY,
          Identifier.of(ArtentPotions.MODID, key + "_" + i));

      enhancementRecipeRegistry.register(registryKey2, enhancementRecipe);
    }
  }
}
