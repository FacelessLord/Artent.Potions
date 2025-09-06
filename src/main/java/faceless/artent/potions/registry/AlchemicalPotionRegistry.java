package faceless.artent.potions.registry;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.brewingApi.AlchemicalPotion;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static faceless.artent.potions.objects.AlchemicalPotions.*;

public class AlchemicalPotionRegistry implements IRegistry {
  private static final Hashtable<String, AlchemicalPotion> PotionsMap = new Hashtable<>();
  private static final Hashtable<String, AlchemicalPotion> FermentedPotionsMap = new Hashtable<>();
  private static final List<String> PotionsList = new ArrayList<>();
  private static final List<String> FermentedPotionsList = new ArrayList<>();

  public static AlchemicalPotion getPotion(String key) {
    return PotionsMap.getOrDefault(key, null);
  }

  public static AlchemicalPotion getFermentedPotion(String key) {
    return FermentedPotionsMap.getOrDefault(key, null);
  }

  public static List<String> getRegisteredPotions() {
    return PotionsList;
  }

  public static List<String> getFermentedPotions() {
    return FermentedPotionsList;
  }

  @Override
  public void register() {
    register(POISON);
    register(STRENGTH);
    register(VAMPIRISM);
    register(HOLY_WATER);
    registerFermented(FERMENTED_HOLY_WATER);
    register(BERSERK);

    register(STONE_SKIN);
    register(FIRE_RESISTANCE);
    register(FREEZING);
    register(LIQUID_FLAME);
    registerFermented(FERMENTED_LIQUID_FLAME);
    register(HEALING);
    registerFermented(INSTANT_HEALING);
    register(ANTIDOTE);
    registerFermented(FERMENTED_ANTIDOTE);

    register(FAST_SWIMMING);
    register(WATER_BREATHING);
    register(JUMP_BOOST);
    register(FEATHER_FALLING);
    register(NIGHT_VISION);

    register(FLIGHT);
    register(FORTUNE);
    register(SATURATION);
    registerFermented(FERMENTED_SATURATION);
    register(LUMBERJACK);
    register(HASTE);
    register(LEVITATION);
    registerFermented(SURFACE_TELEPORTATION);
  }

  public void register(AlchemicalPotion potion) {
    PotionsMap.put(potion.id, potion);
    PotionsList.add(potion.id);
  }

  public void registerFermented(AlchemicalPotion fermented) {
    FermentedPotionsMap.put(fermented.id, fermented);
    FermentedPotionsList.add(fermented.id);
    register(fermented);
  }

  public void register(AlchemicalPotion[] potions) {
    for (AlchemicalPotion potion : potions) {
      register(potion);
    }
  }

  public void registerFermented(AlchemicalPotion[] fermented) {
    for (AlchemicalPotion potion : fermented) {
      FermentedPotionsMap.put(potion.id, potion);
      FermentedPotionsList.add(potion.id);
    }
  }

  public static boolean fermentedPotionIsRegistered(String id) {
    return FermentedPotionsMap.containsKey(id);
  }
}
