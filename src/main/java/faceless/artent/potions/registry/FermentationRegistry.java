package faceless.artent.potions.registry;

import faceless.artent.core.math.Color;
import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.recipes.FermentationRecipe;

import java.util.Hashtable;

import static faceless.artent.potions.objects.AlchemicalPotions.*;

public class FermentationRegistry implements IRegistry {
  private static final Hashtable<String, FermentationRecipe> FermentationRecipes = new Hashtable<>();

  public static AlchemicalPotion getFermentationResult(AlchemicalPotion potion) {
    if (potion == null)
      return null;
    var recipe = getFermentationRecipe(potion);
    return recipe == null ? null : recipe.result();
  }

  public static FermentationRecipe getFermentationRecipe(AlchemicalPotion potion) {
    var fermentedPotionRecipe = FermentationRecipes.getOrDefault(potion.id, null);
    if (fermentedPotionRecipe == null)
      return null;
    return fermentedPotionRecipe;
  }

  @Override
  public void register() {
    register(SANCTITY, CLEANSING, 600, 1.5f);
    register(VAMPIRISM, FERMENTED_VAMPIRISM, 600, 1.5f);
    register(LIQUID_FLAME, FERMENTED_LIQUID_FLAME, 600, 1.5f);
    register(HEALING, INSTANT_HEALING, 600, 1.5f);
//    register(ANTIDOTE, FERMENTED_ANTIDOTE, 600, 1.5f); TODO can make multiple fermentations upgrade antidote or poison level

    register(SATURATION, FERMENTED_SATURATION, 600, 1.5f);
    register(LEVITATION, SURFACE_TELEPORTATION, 600, 1.5f);
    register(SPEED, BLINK, 600, 1.5f);
  }

  private void register(
      AlchemicalPotion[] potions,
      AlchemicalPotion[] fermentedPotions,
      int baseSeconds,
      float levelModifier) {
    var baseTime = baseSeconds;
    for (int i = 0; i < potions.length; i++) {
      register(potions[i], fermentedPotions[i], baseTime);
      baseTime = (int) Math.max(1, baseTime * levelModifier);
    }
  }

  private void register(AlchemicalPotion potion, AlchemicalPotion fermentedPotion, int seconds) {
    FermentationRecipes.put(potion.id, new FermentationRecipe(potion, fermentedPotion, 10 /*seconds*/));
    fermentedPotion.color = potion.color.addNoMixing(new Color(64, 0, 0));
  }
}
