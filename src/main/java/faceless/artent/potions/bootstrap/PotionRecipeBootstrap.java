package faceless.artent.potions.bootstrap;

import com.google.common.collect.ImmutableList;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.objects.ModRegistries;
import faceless.artent.potions.recipes.PotionEnhancementRecipe;
import faceless.artent.potions.recipes.PotionRecipe;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import static faceless.artent.potions.objects.AlchemicalPotions.*;

public class PotionRecipeBootstrap {
  public static void bootstrap(PotionRegisterContext ctx) {
    // BLUEBERRY
    registerRecipe(
        ctx,
        "water_breathing",
        WATER_BREATHING,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.RedMushroom,
        BrewingIngredientsBootstrap.Kelp);

    registerRecipe(
        ctx,
        "liquid_flame",
        LIQUID_FLAME,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.Slime,
        BrewingIngredientsBootstrap.BlazePowder,
        BrewingIngredientsBootstrap.BlazePowder);

    registerRecipe(
        ctx,
        "freezing",
        FREEZING,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.GlisteningMelon,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.FrostPumpkin);

    registerRecipe(
        ctx,
        "holy_water",
        HOLY_WATER,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.GlisteningMelon,
        BrewingIngredientsBootstrap.GoldenCarrot,
        BrewingIngredientsBootstrap.GlowstoneDust);

    registerRecipe(
        ctx,
        "poison",
        POISON,
        BrewingIngredientsBootstrap.Blueberry,
        BrewingIngredientsBootstrap.RottenFlesh,
        BrewingIngredientsBootstrap.SpiderEye);

    // CLOUDBERRY

    registerRecipe(
        ctx,
        "saturation",
        SATURATION,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.Bone,
        BrewingIngredientsBootstrap.Apple);

    registerRecipe(
        ctx,
        "fast_swimming",
        FAST_SWIMMING,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.BrownMushroom,
        BrewingIngredientsBootstrap.StoneScale);

    registerRecipe(
        ctx,
        "lumberjack",
        LUMBERJACK,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.Stick,
        BrewingIngredientsBootstrap.Acorn);

    registerRecipe(
        ctx,
        "haste",
        HASTE,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.LapisLazuli,
        BrewingIngredientsBootstrap.RedstoneDust,
        BrewingIngredientsBootstrap.PufferFish);

    registerRecipe(
        ctx,
        "strength",
        STRENGTH,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.GlisteningMelon,
        BrewingIngredientsBootstrap.BlazePowder);

    registerRecipe(
        ctx,
        "jump_boost",
        JUMP_BOOST,
        BrewingIngredientsBootstrap.Cloudberry,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.Slime);

    // BLACKBERRY

    registerRecipe(
        ctx,
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
        ctx,
        "fortune",
        FORTUNE,
        BrewingIngredientsBootstrap.Blackberry,
        BrewingIngredientsBootstrap.CrimsonLeaf,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.Diamond,
        BrewingIngredientsBootstrap.RabbitFoot);

    registerRecipe(
        ctx,
        "flight",
        FLIGHT,
        BrewingIngredientsBootstrap.Blackberry,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.ManaFeather);

    registerRecipe(
        ctx,
        "levitation",
        LEVITATION,
        BrewingIngredientsBootstrap.Blackberry,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.EnderPearl,
        BrewingIngredientsBootstrap.PhantomMembrane);

    registerRecipe(
        ctx,
        "feather_falling",
        FEATHER_FALLING,
        BrewingIngredientsBootstrap.Blackberry,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.CrimsonLeaf);

    // RASPBERRY

    registerRecipe(
        ctx,
        "healing",
        HEALING,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.RedMushroom,
        BrewingIngredientsBootstrap.GlisteningMelon);

    registerRecipe(
        ctx,
        "antidote",
        ANTIDOTE,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.RedMushroom,
        BrewingIngredientsBootstrap.FermentedSpiderEye);

    registerRecipe(
        ctx,
        "vampirism",
        VAMPIRISM,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.CrimsonLeaf,
        BrewingIngredientsBootstrap.Shadowveil);

    registerRecipe(
        ctx,
        "berserk",
        BERSERK,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.NetherWart,
        BrewingIngredientsBootstrap.GlisteningMelon,
        BrewingIngredientsBootstrap.CrimsonwoodBerry);

    registerRecipe(
        ctx,
        "stone_skin",
        STONE_SKIN,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.Shroom,
        BrewingIngredientsBootstrap.Bone,
        BrewingIngredientsBootstrap.IronChestplate);

    registerRecipe(
        ctx,
        "fire_resistance",
        FIRE_RESISTANCE,
        BrewingIngredientsBootstrap.Raspberry,
        BrewingIngredientsBootstrap.BrownMushroom,
        BrewingIngredientsBootstrap.MagmaCream);
  }

  public static void registerRecipe(
      PotionRegisterContext ctx,
      String key,
      AlchemicalPotion potion,
      BrewingIngredient... ingredients) {
    var recipe = new PotionRecipe(ImmutableList.copyOf(ingredients), potion);

    var registryKey = RegistryKey.of(
        ModRegistries.POTION_RECIPES_REGISTRY_KEY,
        Identifier.of(ArtentPotions.MODID, key));
    ctx.recipeRegistry().register(registryKey, recipe);
  }

  public static void registerRecipe(
      PotionRegisterContext ctx,
      String key,
      AlchemicalPotion[] potions,
      BrewingIngredient... ingredients) {
    var recipe = new PotionRecipe(ImmutableList.copyOf(ingredients), potions[0]);

    var registryKey = RegistryKey.of(
        ModRegistries.POTION_RECIPES_REGISTRY_KEY,
        Identifier.of(ArtentPotions.MODID, key + "_" + 0));
    ctx.recipeRegistry().register(registryKey, recipe);

    var previousPotion = potions[0];

    for (int i = 1; i < potions.length; i++) {
      var enhancementRecipe = new PotionEnhancementRecipe(
          previousPotion,
                                                          ingredients[ingredients.length - 1],
                                                          potions[i]);
      previousPotion = potions[i];
      var registryKey2 = RegistryKey.of(
          ModRegistries.POTION_ENHANCEMENT_RECIPE_REGISTRY_KEY,
          Identifier.of(ArtentPotions.MODID, key + "_" + i));

      ctx.enhancementRecipeRegistry().register(registryKey2, enhancementRecipe);
    }
  }
}
