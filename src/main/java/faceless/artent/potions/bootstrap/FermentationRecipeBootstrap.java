package faceless.artent.potions.bootstrap;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.objects.ModRegistries;
import faceless.artent.potions.recipes.FermentationRecipe;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import static faceless.artent.potions.ArtentPotions.LOGGER;
import static faceless.artent.potions.bootstrap.PotionEffectsBootstrap.*;


public class FermentationRecipeBootstrap {
  public static void bootstrap(Registerable<FermentationRecipe> recipeRegistry) {
    LOGGER.info("FermentationRecipe bootstrap");

    register(recipeRegistry, "holy_water", HOLY_WATER, FERMENTED_HOLY_WATER, 600, 1.5f);
    register(recipeRegistry, "liquid_flame", LIQUID_FLAME, FERMENTED_LIQUID_FLAME, 600, 1.5f);
    register(recipeRegistry, "healing", HEALING, INSTANT_HEALING, 600, 1.5f);
    register(recipeRegistry, "antidote", ANTIDOTE, FERMENTED_ANTIDOTE, 600, 1.5f);

    register(recipeRegistry, "saturation", SATURATION, FERMENTED_SATURATION, 600, 1.5f);
    register(recipeRegistry, "surface_teleportation", LEVITATION, SURFACE_TELEPORTATION, 600, 1.5f);
  }

  public static void register(
      Registerable<FermentationRecipe> recipeRegistry,
      String key,
      AlchemicalPotion source,
      AlchemicalPotion potion,
      int seconds) {
    var recipe = new FermentationRecipe(source, potion, seconds);

    var registryKey = RegistryKey.of(
        ModRegistries.POTION_FERMENTATION_RECIPE_REGISTRY_KEY,
        Identifier.of(ArtentPotions.MODID, key));
    recipeRegistry.register(registryKey, recipe);
  }

  public static void register(
      Registerable<FermentationRecipe> recipeRegistry,
      String key,
      AlchemicalPotion[] source,
      AlchemicalPotion[] result,
      int seconds,
      float leveledTimeMultiplier) {
    assert source.length == result.length : "Fermentation recipe group \""
                                            + key
                                            + "\" has unequal potion array sizes: "
                                            + source.length
                                            + " and "
                                            + result.length;

    var resultTime = seconds;

    for (int i = 0; i < source.length; i++) {
      register(recipeRegistry, key + "_" + i, source[i], result[i], resultTime);
      resultTime = (int) Math.max(1, Math.floor(resultTime * leveledTimeMultiplier));
    }
  }
}
