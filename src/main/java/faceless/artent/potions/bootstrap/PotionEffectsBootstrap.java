package faceless.artent.potions.bootstrap;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.objects.ModRegistries;
import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class PotionEffectsBootstrap {
  public static AlchemicalPotion[] POISON;
  public static AlchemicalPotion[] STRENGTH;
  public static AlchemicalPotion[] VAMPIRISM;
  public static AlchemicalPotion[] HOLY_WATER;
  public static AlchemicalPotion[] BERSERK;

  public static AlchemicalPotion[] STONE_SKIN;
  public static AlchemicalPotion FIRE_RESISTANCE;
  public static AlchemicalPotion[] FREEZING;
  public static AlchemicalPotion[] LIQUID_FLAME;
  public static AlchemicalPotion[] HEALING;
  public static AlchemicalPotion[] ANTIDOTE;

  public static AlchemicalPotion[] FAST_SWIMMING;
  public static AlchemicalPotion WATER_BREATHING;
  public static AlchemicalPotion[] JUMP_BOOST;
  public static AlchemicalPotion FEATHER_FALLING;
  public static AlchemicalPotion NIGHT_VISION;

  public static AlchemicalPotion FLIGHT;
  public static AlchemicalPotion[] FORTUNE;
  public static AlchemicalPotion[] SATURATION;
  public static AlchemicalPotion SURFACE_TELEPORTATION;
  public static AlchemicalPotion[] LUMBERJACK;
  public static AlchemicalPotion[] HASTE;
  public static AlchemicalPotion[] LEVITATION;

  public static AlchemicalPotion INSTANT_HARM;
  public static AlchemicalPotion[] FERMENTED_VAMPIRISM;
  public static AlchemicalPotion[] FERMENTED_HOLY_WATER;
  public static AlchemicalPotion[] FERMENTED_LIQUID_FLAME;
  public static AlchemicalPotion[] INSTANT_HEALING;
  public static AlchemicalPotion[] FERMENTED_ANTIDOTE;
  public static AlchemicalPotion FERMENTED_SATURATION;

  public static void bootstrap(Registerable<AlchemicalPotion> ingredientsRegistry) {
    POISON = registerPotionWithLevels(
        ingredientsRegistry,
        "poison",
        3,
        new StatusEffectInstance(StatusEffects.POISON, 2 * 1200));
    STRENGTH = registerPotionWithLevels(
        ingredientsRegistry,
        "strength",
        3,
        new StatusEffectInstance(StatusEffects.STRENGTH, 3 * 1200));
    VAMPIRISM = registerPotionWithLevels(
        ingredientsRegistry,
        "vampirism",
        3,
        new StatusEffectInstance(StatusEffectsRegistry.VAMPIRISM, 3 * 1200));
    HOLY_WATER = registerPotionWithLevels(
        ingredientsRegistry,
        "holy_water",
        3,
        new StatusEffectInstance(StatusEffectsRegistry.HOLY_WATER, 3 * 1200));
    BERSERK = registerPotionWithLevels(
        ingredientsRegistry,
        "berserk",
        2,
        new StatusEffectInstance(StatusEffectsRegistry.BERSERK, 3 * 1200));

    STONE_SKIN = registerPotionWithLevels(
        ingredientsRegistry,
        "stone_skin",
        2,
        new StatusEffectInstance(StatusEffectsRegistry.STONE_SKIN, 3 * 1200));
    FIRE_RESISTANCE = register(
        ingredientsRegistry,
        "fire_resistance",
        new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 2 * 1200));
    FREEZING = registerPotionWithLevels(
        ingredientsRegistry,
        "freezing",
        3,
        new StatusEffectInstance(StatusEffectsRegistry.FREEZING, 1200));
    LIQUID_FLAME = registerPotionWithLevels(
        ingredientsRegistry,
        "liquid_flame",
        3,
        new StatusEffectInstance(StatusEffectsRegistry.LIQUID_FLAME, 2 * 1200));
    HEALING = registerPotionWithLevels(
        ingredientsRegistry,
        "healing",
        3,
        new StatusEffectInstance(StatusEffects.REGENERATION, 600, 0));
    ANTIDOTE = registerPotionWithLevels(
        ingredientsRegistry,
        "antidote",
        3,
        new StatusEffectInstance(StatusEffectsRegistry.ANTIDOTE, 1200));

    FAST_SWIMMING = registerPotionWithLevels(
        ingredientsRegistry,
        "fast_swimming",
        3,
        new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 1800));
    WATER_BREATHING = register(
        ingredientsRegistry,
        "water_breathing",
        new StatusEffectInstance(StatusEffects.WATER_BREATHING, 1800));
    JUMP_BOOST = registerPotionWithLevels(
        ingredientsRegistry,
        "jump_boost",
        3,
        new StatusEffectInstance(StatusEffects.JUMP_BOOST, 1800));
    FEATHER_FALLING = register(
        ingredientsRegistry,
        "feather_falling",
        new StatusEffectInstance(StatusEffectsRegistry.FEATHER_FALLING, 1800));
    NIGHT_VISION = register(
        ingredientsRegistry,
        "night_vision",
        new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1800));

    FLIGHT = register(ingredientsRegistry, "flight", new StatusEffectInstance(StatusEffectsRegistry.FLIGHT, 3600));
    FORTUNE = registerPotionWithLevels(
        ingredientsRegistry,
        "fortune",
        3,
        new StatusEffectInstance(StatusEffectsRegistry.FORTUNE, 1800));
    SATURATION = registerPotionWithLevels(
        ingredientsRegistry,
        "saturation",
        3,
        new StatusEffectInstance(StatusEffectsRegistry.SATURATION, 6000));
    SURFACE_TELEPORTATION = register(
        ingredientsRegistry,
        "surface_teleportation",
        new StatusEffectInstance(StatusEffectsRegistry.SURFACE_TELEPORTATION, 1));
    LUMBERJACK = registerPotionWithLevels(
        ingredientsRegistry,
        "lumberjack",
        3,
        new StatusEffectInstance(StatusEffectsRegistry.LUMBERJACK, 3600));
    HASTE = registerPotionWithLevels(
        ingredientsRegistry,
        "haste",
        3,
        new StatusEffectInstance(StatusEffects.HASTE, 1800));
    LEVITATION = registerPotionWithLevels(
        ingredientsRegistry,
        "levitation",
        3,
        new StatusEffectInstance(StatusEffects.LEVITATION, 200));

    INSTANT_HARM = register(
        ingredientsRegistry,
        "instant_harm",
        new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1));
    FERMENTED_VAMPIRISM = registerPotionWithLevels(
        ingredientsRegistry,
        "fermented_vampirism",
        3,
        new StatusEffectInstance(StatusEffectsRegistry.FERMENTED_VAMPIRISM, 1200));
    FERMENTED_HOLY_WATER = registerPotionWithLevels(
        ingredientsRegistry,
        "fermented_holy_water",
        3,
        new StatusEffectInstance(StatusEffectsRegistry.FERMENTED_HOLY_WATER, 1200));
    FERMENTED_LIQUID_FLAME = registerPotionWithLevels(
        ingredientsRegistry,
        "fermented_liquid_flame",
        3,
        new StatusEffectInstance(StatusEffectsRegistry.FERMENTED_LIQUID_FLAME, 1200));
    INSTANT_HEALING = registerPotionWithLevels(
        ingredientsRegistry,
        "instant_healing",
        3,
        new StatusEffectInstance(StatusEffectsRegistry.INSTANT_HEALING, 1));
    FERMENTED_ANTIDOTE = registerPotionWithLevels(
        ingredientsRegistry,
        "fermented_antidote",
        3,
        new StatusEffectInstance(StatusEffectsRegistry.FERMENTED_ANTIDOTE, 1200));
    FERMENTED_SATURATION = register(
        ingredientsRegistry,
        "fermented_saturation",
        new StatusEffectInstance(StatusEffectsRegistry.FERMENTED_SATURATION, 1));
  }

  private static AlchemicalPotion register(
      Registerable<AlchemicalPotion> ingredientsRegistry,
      String key,
      StatusEffectInstance... effects) {
    var potion = new AlchemicalPotion(effects);

    var registryKey = RegistryKey.of(
        ModRegistries.POTION_EFFECTS_REGISTRY_KEY,
        Identifier.of(ArtentPotions.MODID, key));
    ingredientsRegistry.register(registryKey, potion);

    return potion;
  }

  private static AlchemicalPotion[] registerPotionWithLevels(
      Registerable<AlchemicalPotion> ingredientsRegistry,
      String key,
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
      var registryKey = RegistryKey.of(
          ModRegistries.POTION_EFFECTS_REGISTRY_KEY,
          Identifier.of(ArtentPotions.MODID, key + (i > 0 ? "_" + i : "")));
      potions[i] = new AlchemicalPotion(newEffects);

      var entry = ingredientsRegistry.register(registryKey, potions[i]);
      potions[i].setRegistryEntry(entry);
    }

    return potions;
  }
}
