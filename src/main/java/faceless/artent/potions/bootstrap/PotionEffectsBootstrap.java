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

import static faceless.artent.potions.ArtentPotions.LOGGER;

public class PotionEffectsBootstrap {
  public static AlchemicalPotion[] POISON = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffects.POISON,
          2 * 1200));
  public static AlchemicalPotion[] STRENGTH = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffects.STRENGTH,
          3 * 1200));
  public static AlchemicalPotion[] VAMPIRISM = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.VAMPIRISM,
          3 * 1200));
  public static AlchemicalPotion[] HOLY_WATER = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.HOLY_WATER,
          3 * 1200));
  public static AlchemicalPotion[] BERSERK = createPotionWithLevels(
      2,
      new StatusEffectInstance(
          StatusEffectsRegistry.BERSERK,
          3 * 1200));

  public static AlchemicalPotion[] STONE_SKIN = createPotionWithLevels(
      2,
      new StatusEffectInstance(
          StatusEffectsRegistry.STONE_SKIN,
          3 * 1200));
  public static AlchemicalPotion FIRE_RESISTANCE = new AlchemicalPotion(new StatusEffectInstance(
      StatusEffects.FIRE_RESISTANCE,
      2 * 1200));
  public static AlchemicalPotion[] FREEZING = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.FREEZING,
          1200));
  public static AlchemicalPotion[] LIQUID_FLAME = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.LIQUID_FLAME,
          2 * 1200));
  public static AlchemicalPotion[] HEALING = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffects.REGENERATION,
          600,
          0));
  public static AlchemicalPotion[] ANTIDOTE = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.ANTIDOTE,
          1200));

  public static AlchemicalPotion[] FAST_SWIMMING = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffects.DOLPHINS_GRACE,
          1800));
  public static AlchemicalPotion WATER_BREATHING = new AlchemicalPotion(new StatusEffectInstance(
      StatusEffects.WATER_BREATHING,
      1800));
  public static AlchemicalPotion[] JUMP_BOOST = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffects.JUMP_BOOST,
          1800));
  public static AlchemicalPotion FEATHER_FALLING = new AlchemicalPotion(new StatusEffectInstance(
      StatusEffectsRegistry.FEATHER_FALLING,
      1800));
  public static AlchemicalPotion NIGHT_VISION = new AlchemicalPotion(new StatusEffectInstance(
      StatusEffects.NIGHT_VISION,
      1800));

  public static AlchemicalPotion FLIGHT = new AlchemicalPotion(new StatusEffectInstance(
      StatusEffectsRegistry.FLIGHT,
      3600));
  public static AlchemicalPotion[] FORTUNE = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.FORTUNE,
          1800));
  public static AlchemicalPotion[] SATURATION = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.SATURATION,
          6000));
  public static AlchemicalPotion[] SURFACE_TELEPORTATION = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.SURFACE_TELEPORTATION,
          1));
  public static AlchemicalPotion[] LUMBERJACK = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.LUMBERJACK,
          3600));
  public static AlchemicalPotion[] HASTE = createPotionWithLevels(
      3,
      new StatusEffectInstance(StatusEffects.HASTE, 1800));
  ;
  public static AlchemicalPotion[] LEVITATION = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffects.LEVITATION,
          200));

  public static AlchemicalPotion[] INSTANT_HARM = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffects.INSTANT_DAMAGE,
          1));
  public static AlchemicalPotion[] FERMENTED_VAMPIRISM = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.FERMENTED_VAMPIRISM,
          1200));
  public static AlchemicalPotion[] FERMENTED_HOLY_WATER = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.FERMENTED_HOLY_WATER,
          1200));
  public static AlchemicalPotion[] FERMENTED_LIQUID_FLAME = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.FERMENTED_LIQUID_FLAME,
          1200));
  public static AlchemicalPotion[] INSTANT_HEALING = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.INSTANT_HEALING,
          1));
  public static AlchemicalPotion[] FERMENTED_ANTIDOTE = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.FERMENTED_ANTIDOTE,
          1200));
  public static AlchemicalPotion[] FERMENTED_SATURATION = createPotionWithLevels(
      3,
      new StatusEffectInstance(
          StatusEffectsRegistry.FERMENTED_SATURATION,
          1));

  public static void bootstrap(Registerable<AlchemicalPotion> ingredientsRegistry) {
    LOGGER.info("PotionEffects bootstrap");
    register(POISON, ingredientsRegistry, "poison");
    register(STRENGTH, ingredientsRegistry, "strength");
    register(VAMPIRISM, ingredientsRegistry, "vampirism");
    register(HOLY_WATER, ingredientsRegistry, "holy_water");
    register(BERSERK, ingredientsRegistry, "berserk");

    register(STONE_SKIN, ingredientsRegistry, "stone_skin");
    register(FIRE_RESISTANCE, ingredientsRegistry, "fire_resistance");
    register(FREEZING, ingredientsRegistry, "freezing");
    register(LIQUID_FLAME, ingredientsRegistry, "liquid_flame");
    register(HEALING, ingredientsRegistry, "healing");
    register(ANTIDOTE, ingredientsRegistry, "antidote");

    register(FAST_SWIMMING, ingredientsRegistry, "fast_swimming");
    register(WATER_BREATHING, ingredientsRegistry, "water_breathing");
    register(JUMP_BOOST, ingredientsRegistry, "jump_boost");
    register(FEATHER_FALLING, ingredientsRegistry, "feather_falling");
    register(NIGHT_VISION, ingredientsRegistry, "night_vision");

    register(FLIGHT, ingredientsRegistry, "flight");
    register(FORTUNE, ingredientsRegistry, "fortune");
    register(SATURATION, ingredientsRegistry, "saturation");
    register(SURFACE_TELEPORTATION, ingredientsRegistry, "surface_teleportation");
    register(LUMBERJACK, ingredientsRegistry, "lumberjack");
    register(HASTE, ingredientsRegistry, "haste");
    register(LEVITATION, ingredientsRegistry, "levitation");

    register(INSTANT_HARM, ingredientsRegistry, "instant_harm");
    register(FERMENTED_VAMPIRISM, ingredientsRegistry, "fermented_vampirism");
    register(FERMENTED_HOLY_WATER, ingredientsRegistry, "fermented_holy_water");
    register(FERMENTED_LIQUID_FLAME, ingredientsRegistry, "fermented_liquid_flame");
    register(INSTANT_HEALING, ingredientsRegistry, "instant_healing");
    register(FERMENTED_ANTIDOTE, ingredientsRegistry, "fermented_antidote");
    register(FERMENTED_SATURATION, ingredientsRegistry, "fermented_saturation");
  }

  private static AlchemicalPotion[] createPotionWithLevels(int levelAmount, StatusEffectInstance... effects) {

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
      potions[i] = new AlchemicalPotion(newEffects);
    }

    return potions;
  }

  private static void register(
      AlchemicalPotion potion,
      Registerable<AlchemicalPotion> ingredientsRegistry,
      String key) {
    var registryKey = RegistryKey.of(
        ModRegistries.POTION_EFFECTS_REGISTRY_KEY,
        Identifier.of(ArtentPotions.MODID, key));
    var entry = ingredientsRegistry.register(registryKey, potion);
    potion.setRegistryEntry(entry);
  }

  private static void register(
      AlchemicalPotion[] potions,
      Registerable<AlchemicalPotion> ingredientsRegistry,
      String key) {
    for (int i = 0; i < potions.length; i++) {
      register(potions[i], ingredientsRegistry, key + (i > 0 ? "_" + i : ""));
    }
  }
}
