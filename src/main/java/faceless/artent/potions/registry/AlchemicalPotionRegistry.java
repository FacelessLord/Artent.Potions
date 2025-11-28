package faceless.artent.potions.registry;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.brewingApi.AlchemicalPotion;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static faceless.artent.potions.objects.AlchemicalPotions.*;

public class AlchemicalPotionRegistry implements IRegistry {
  private static final Hashtable<String, AlchemicalPotion> PotionsMap = new Hashtable<>();
  private static final List<String> PotionsList = new ArrayList<>();

  public static AlchemicalPotion getPotion(String key) {
    return PotionsMap.getOrDefault(key, null);
  }


  public static List<String> getRegisteredPotions() {
    return PotionsList;
  }

  @Override
  public void register() {
    register(POISON);
    register(INSTANT_HARM);
    register(STRENGTH);
    register(VAMPIRISM);
    register(FERMENTED_VAMPIRISM);
    register(HOLY_WATER);
    register(FERMENTED_HOLY_WATER);
    register(BERSERK);

    register(STONE_SKIN);
    register(FIRE_RESISTANCE);
    register(FREEZING);
    register(LIQUID_FLAME);
    register(FERMENTED_LIQUID_FLAME);
    register(HEALING);
    register(INSTANT_HEALING);
    register(ANTIDOTE);
//    register(FERMENTED_ANTIDOTE);

    register(FAST_SWIMMING);
    register(WATER_BREATHING);
    register(JUMP_BOOST);
    register(FEATHER_FALLING);
    register(NIGHT_VISION);

    register(FLIGHT);
    register(FORTUNE);
    register(SATURATION);
    register(FERMENTED_SATURATION);
    register(LUMBERJACK);
    register(HASTE);
    register(LEVITATION);
    register(SURFACE_TELEPORTATION);
  }

  public void register(AlchemicalPotion potion) {
    PotionsMap.put(potion.id, potion);
    PotionsList.add(potion.id);
  }

  public void register(AlchemicalPotion[] potions) {
    for (AlchemicalPotion potion : potions) {
      register(potion);
    }
  }
}
