package faceless.artent.potions.registry;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.brewingApi.AlchemicalPotion;

import java.util.Hashtable;


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
  }
}
