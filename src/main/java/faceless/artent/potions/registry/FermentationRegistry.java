package faceless.artent.potions.registry;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.brewingApi.AlchemicalPotion;

import java.util.Hashtable;

import static faceless.artent.potions.objects.AlchemicalPotions.*;

public class FermentationRegistry implements IRegistry {
  private static final Hashtable<String, String> FermentationRecipes = new Hashtable<>();

  public static AlchemicalPotion getFermentedPotion(AlchemicalPotion potion) {
    if(potion == null)
      return null;
    return getFermentedPotion(potion.id);
  }
  public static AlchemicalPotion getFermentedPotion(String potion) {
    var fermentedPotion = FermentationRecipes.getOrDefault(potion, null);
    if (fermentedPotion == null)
      return null;
    return AlchemicalPotionRegistry.getFermentedPotion(fermentedPotion);
  }

  @Override
  public void register() {
    register(HOLY_WATER, FERMENTED_HOLY_WATER);
    register(LIQUID_FLAME, FERMENTED_LIQUID_FLAME);
    register(HEALING, INSTANT_HEALING);
    register(ANTIDOTE, FERMENTED_ANTIDOTE);

    register(SATURATION, FERMENTED_SATURATION);
    register(LEVITATION, SURFACE_TELEPORTATION);
  }

  private void register(AlchemicalPotion[] potions, AlchemicalPotion fermentedPotion) {
    for (AlchemicalPotion potion : potions) {
      register(potion, fermentedPotion);
    }
  }

  private void register(AlchemicalPotion[] potions, AlchemicalPotion[] fermentedPotions) {
    for (int i = 0; i < potions.length; i++) {
      register(potions[i], fermentedPotions[i]);
    }
  }

  private void register(AlchemicalPotion potion, AlchemicalPotion fermentedPotion) {
    FermentationRecipes.put(potion.id, fermentedPotion.id);
  }
}
