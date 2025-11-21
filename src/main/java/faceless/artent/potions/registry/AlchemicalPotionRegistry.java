package faceless.artent.potions.registry;

import faceless.artent.potions.brewingApi.AlchemicalPotion;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class AlchemicalPotionRegistry {
  @Deprecated
  private static final Hashtable<String, AlchemicalPotion> PotionsMap = new Hashtable<>();
  @Deprecated
  private static final Hashtable<String, AlchemicalPotion> FermentedPotionsMap = new Hashtable<>();
  @Deprecated
  private static final List<String> PotionsList = new ArrayList<>();

  public static AlchemicalPotion getPotion(String key) {
    return PotionsMap.getOrDefault(key, null);
  }

  public static AlchemicalPotion getFermentedPotion(String key) {
    return FermentedPotionsMap.getOrDefault(key, null);
  }

  public static List<String> getRegisteredPotions() {
    return PotionsList;
  }
}
