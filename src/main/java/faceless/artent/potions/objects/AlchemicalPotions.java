package faceless.artent.potions.objects;

import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class AlchemicalPotions {
  public static final AlchemicalPotion[] POISON = createPotionWithLevels(
      "poison",
      3,
      new StatusEffectInstance(
          StatusEffects.POISON,
          2 * 1200));
  public static final AlchemicalPotion[] STRENGTH = createPotionWithLevels(
      "strength",
      3,
      new StatusEffectInstance(
          StatusEffects.STRENGTH,
          3 * 1200));
  public static final AlchemicalPotion[] VAMPIRISM = createPotionWithLevels(
      "vampirism",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.VAMPIRISM,
          3 * 1200));
  public static final AlchemicalPotion[] SANCTITY = createPotionWithLevels(
      "sanctity",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.SANCTITY,
          3 * 1200));
  public static final AlchemicalPotion[] BERSERK = createPotionWithLevels(
      "berserk",
      2,
      new StatusEffectInstance(
          StatusEffectsRegistry.BERSERK,
          3 * 1200));

  public static final AlchemicalPotion[] STONE_SKIN = createPotionWithLevels(
      "stone_skin",
      2,
      new StatusEffectInstance(
          StatusEffectsRegistry.STONE_SKIN,
          3 * 1200));
  public static final AlchemicalPotion FIRE_RESISTANCE = new AlchemicalPotion(
      "fire_resistance",
      new StatusEffectInstance(
          StatusEffects.FIRE_RESISTANCE,
          2 * 1200));
  public static final AlchemicalPotion[] FREEZING = createPotionWithLevels(
      "freezing",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.FREEZING,
          1200));
  public static final AlchemicalPotion[] LIQUID_FLAME = createPotionWithLevels(
      "liquid_flame",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.LIQUID_FLAME,
          2 * 1200));
  public static final AlchemicalPotion[] HEALING = createPotionWithLevels(
      "healing",
      3,
      new StatusEffectInstance(
          StatusEffects.REGENERATION,
          600,
          0));
  public static final AlchemicalPotion[] ANTIDOTE = createPotionWithLevels(
      "antidote",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.ANTIDOTE,
          1200));

  public static final AlchemicalPotion[] FAST_SWIMMING = createPotionWithLevels(
      "fast_swimming",
      3,
      new StatusEffectInstance(
          StatusEffects.DOLPHINS_GRACE,
          1800));
  public static final AlchemicalPotion WATER_BREATHING = new AlchemicalPotion(
      "water_breathing",
      new StatusEffectInstance(
          StatusEffects.WATER_BREATHING,
          1800));
  public static final AlchemicalPotion[] JUMP_BOOST = createPotionWithLevels(
      "jump_boost",
      3,
      new StatusEffectInstance(
          StatusEffects.JUMP_BOOST,
          1800));
  public static final AlchemicalPotion FEATHER_FALLING = new AlchemicalPotion(
      "feather_falling",
      new StatusEffectInstance(
          StatusEffectsRegistry.FEATHER_FALLING,
          1800));
  public static final AlchemicalPotion NIGHT_VISION = new AlchemicalPotion(
      "night_vision",
      new StatusEffectInstance(
          StatusEffects.NIGHT_VISION,
          1800));

  public static final AlchemicalPotion FLIGHT = new AlchemicalPotion(
      "flight",
      new StatusEffectInstance(
          StatusEffectsRegistry.FLIGHT,
          3600));
  public static final AlchemicalPotion[] FORTUNE = createPotionWithLevels(
      "fortune",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.FORTUNE,
          1800));
  public static final AlchemicalPotion[] HOT_CHOCOLATE = createPotionWithLevels(
      "hot_chocolate",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.HOT_CHOCOLATE,
          6000));
  public static final AlchemicalPotion[] SATURATION = createPotionWithLevels(
      "saturation",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.SATURATION,
          6000));
  public static final AlchemicalPotion[] SPEED = createPotionWithLevels(
      "speed",
      3,
      new StatusEffectInstance(
          StatusEffects.SPEED,
          6000));
  public static final AlchemicalPotion[] SURFACE_TELEPORTATION = createPotionWithLevels(
      "surface_teleportation", 3,
      new StatusEffectInstance(
          StatusEffectsRegistry.SURFACE_TELEPORTATION,
          1));
  public static final AlchemicalPotion[] LUMBERJACK = createPotionWithLevels(
      "lumberjack",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.LUMBERJACK,
          3600));
  public static final AlchemicalPotion[] HASTE = createPotionWithLevels(
      "haste",
      3,
      new StatusEffectInstance(
          StatusEffects.HASTE,
          1800));
  public static final AlchemicalPotion[] LEVITATION = createPotionWithLevels(
      "levitation",
      3,
      new StatusEffectInstance(
          StatusEffects.LEVITATION,
          200));

  public static final AlchemicalPotion INSTANT_HARM = new AlchemicalPotion(
      "instant_harm",
      new StatusEffectInstance(
          StatusEffects.INSTANT_DAMAGE,
          1));
  public static final AlchemicalPotion[] FERMENTED_VAMPIRISM = createPotionWithLevels(
      "vampire_baron",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.VAMPIRE_BARON,
          1200));
  public static final AlchemicalPotion[] CLEANSING = createPotionWithLevels(
      "cleansing",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.CLEANSING,
          1200));
  public static final AlchemicalPotion[] FERMENTED_LIQUID_FLAME = createPotionWithLevels(
      "flaming_soul",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.FLAMING_SOUL,
          1200));
  public static final AlchemicalPotion[] INSTANT_HEALING = createPotionWithLevels(
      "instant_healing",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.INSTANT_HEALING,
          1));
//  public static final AlchemicalPotion[] FERMENTED_ANTIDOTE = createPotionWithLevels(
//      "fermented_antidote",
//      3,
//      new StatusEffectInstance(
//          StatusEffectsRegistry.FERMENTED_ANTIDOTE,
//          1200));
  public static final AlchemicalPotion[] FERMENTED_SATURATION = createPotionWithLevels(
      "liquid_meat",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.LIQUID_MEAT,
          1));
  public static final AlchemicalPotion[] BLINK = createPotionWithLevels(
      "blink",
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.BLINK,
          1));

  private static AlchemicalPotion[] createPotionWithLevels(
      String id,
      int levelAmount,
      StatusEffectInstance... effects) {
    var potions = new AlchemicalPotion[levelAmount];
    for (int i = 0; i < potions.length; i++) {
      var newEffects = effects.clone();
      for (int j = 0; j < newEffects.length; j++) {
        var effect = newEffects[j];
        newEffects[j] = new StatusEffectInstance(
            effect.getEffectType(),
            (int) (effect.getDuration() * Math.pow(0.75, i)),
            effect.getAmplifier() + i);
      }
      potions[i] = new AlchemicalPotion(id + (i > 0 ? "_" + i : ""), newEffects);
    }
    return potions;
  }
}