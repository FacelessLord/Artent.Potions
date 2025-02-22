package faceless.artent.potions.objects;

import faceless.artent.core.math.Color;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class AlchemicalPotions {
    public static final AlchemicalPotion POISON = new AlchemicalPotion("poison",
                                                                       Color.Green,
                                                                       new StatusEffectInstance(StatusEffects.POISON,
                                                                                                2 * 1200));
    public static final AlchemicalPotion STRENGTH = new AlchemicalPotion("strength",
                                                                         Color.Red,
                                                                         new StatusEffectInstance(StatusEffects.STRENGTH,
                                                                                                  3 * 1200));
    public static final AlchemicalPotion VAMPIRISM = new AlchemicalPotion("vampirism",
                                                                          Color.Red,
                                                                          new StatusEffectInstance(StatusEffectsRegistry.VAMPIRISM,
                                                                                                   3 * 1200));
    public static final AlchemicalPotion HOLY_WATER = new AlchemicalPotion("holy_water",
                                                                           Color.Gold,
                                                                           new StatusEffectInstance(StatusEffectsRegistry.HOLY_WATER,
                                                                                                    3 * 1200));
    public static final AlchemicalPotion BERSERK = new AlchemicalPotion("berserk",
                                                                        Color.Red,
                                                                        new StatusEffectInstance(StatusEffectsRegistry.BERSERK,
                                                                                                 3 * 1200));

    public static final AlchemicalPotion STONE_SKIN = new AlchemicalPotion("stone_skin",
                                                                           Color.Gray,
                                                                           new StatusEffectInstance(StatusEffectsRegistry.STONE_SKIN,
                                                                                                    3 * 1200));
    public static final AlchemicalPotion FIRE_RESISTANCE = new AlchemicalPotion("fire_resistance",
                                                                                Color.Orange,
                                                                                new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,
                                                                                                         2 * 1200));
    public static final AlchemicalPotion FREEZING = new AlchemicalPotion("freezing",
                                                                         Color.Orange,
                                                                         new StatusEffectInstance(StatusEffectsRegistry.FREEZING,
                                                                                                  1200));
    public static final AlchemicalPotion LIQUID_FLAME = new AlchemicalPotion("liquid_flame",
                                                                             Color.Red,
                                                                             new StatusEffectInstance(StatusEffectsRegistry.LIQUID_FLAME,
                                                                                                      2 * 1200));
    public static final AlchemicalPotion HEALING = new AlchemicalPotion("healing",
                                                                        Color.Red,
                                                                        new StatusEffectInstance(StatusEffects.REGENERATION,
                                                                                                 600,
                                                                                                 0));
    public static final AlchemicalPotion ANTIDOTE = new AlchemicalPotion("antidote",
                                                                         Color.Red,
                                                                         new StatusEffectInstance(StatusEffectsRegistry.ANTIDOTE,
                                                                                                  1200));

    public static final AlchemicalPotion FAST_SWIMMING = new AlchemicalPotion("fast_swimming",
                                                                              Color.Red,
                                                                              new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE,
                                                                                                       1800));
    public static final AlchemicalPotion WATER_BREATHING = new AlchemicalPotion("water_breathing",
                                                                                Color.Red,
                                                                                new StatusEffectInstance(StatusEffects.WATER_BREATHING,
                                                                                                         1800));
    public static final AlchemicalPotion JUMP_BOOST = new AlchemicalPotion("jump_boost",
                                                                           Color.Red,
                                                                           new StatusEffectInstance(StatusEffects.JUMP_BOOST,
                                                                                                    1800));
    public static final AlchemicalPotion FEATHER_FALLING = new AlchemicalPotion("feather_falling",
                                                                                Color.Red,
                                                                                new StatusEffectInstance(
                                                                                        StatusEffectsRegistry.FEATHER_FALLING,
                                                                                  1800));
    public static final AlchemicalPotion NIGHT_VISION = new AlchemicalPotion("night_vision",
                                                                             Color.Red,
                                                                             new StatusEffectInstance(StatusEffects.NIGHT_VISION,
                                                                                                      1800));

    public static final AlchemicalPotion FLIGHT = new AlchemicalPotion("flight",
                                                                       Color.Red,
                                                                       new StatusEffectInstance(StatusEffectsRegistry.FLIGHT,
                                                                                                3600));
    // TODO FORTUNE
    public static final AlchemicalPotion FORTUNE = new AlchemicalPotion("fortune",
                                                                        Color.Green,
                                                                        new StatusEffectInstance(StatusEffects.LUCK,
                                                                                              1800));
    public static final AlchemicalPotion SATURATION = new AlchemicalPotion("saturation",
                                                                           Color.Red,
                                                                           new StatusEffectInstance(StatusEffectsRegistry.SATURATION,
                                                                                                    6000));
    public static final AlchemicalPotion SURFACE_TELEPORTATION = new AlchemicalPotion("surface_teleportation",
                                                                                      Color.Red,
                                                                                      new StatusEffectInstance(
                                                                                              StatusEffectsRegistry.SURFACE_TELEPORTATION,
                                                                                        1));
    public static final AlchemicalPotion LUMBERJACK = new AlchemicalPotion("lumberjack",
                                                                           Color.Red,
                                                                           new StatusEffectInstance(StatusEffectsRegistry.LUMBERJACK,
                                                                                                    3600));
    public static final AlchemicalPotion HASTE = new AlchemicalPotion("haste",
                                                                      Color.Red,
                                                                      new StatusEffectInstance(StatusEffects.HASTE,
                                                                                               1800));
    public static final AlchemicalPotion LEVITATION = new AlchemicalPotion("levitation",
                                                                      Color.Red,
                                                                      new StatusEffectInstance(StatusEffects.LEVITATION,
                                                                                               200));

    public static final AlchemicalPotion INSTANT_HARM = new AlchemicalPotion("instant_harm",
                                                                             Color.Red,
                                                                             new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE,
                                                                                                      1));
    public static final AlchemicalPotion FERMENTED_VAMPIRISM = new AlchemicalPotion("fermented_vampirism",
                                                                                    Color.Red,
                                                                                    new StatusEffectInstance(
                                                                                      StatusEffectsRegistry.FERMENTED_VAMPIRISM,
                                                                                      1200));
    public static final AlchemicalPotion FERMENTED_HOLY_WATER = new AlchemicalPotion("fermented_holy_water",
                                                                                     Color.Red,
                                                                                     new StatusEffectInstance(
                                                                                       StatusEffectsRegistry.FERMENTED_HOLY_WATER,
                                                                                       1200));
    public static final AlchemicalPotion FERMENTED_LIQUID_FLAME = new AlchemicalPotion("fermented_liquid_flame",
                                                                                       Color.Red,
                                                                                       new StatusEffectInstance(
                                                                                         StatusEffectsRegistry.FERMENTED_LIQUID_FLAME,
                                                                                         1200));
    public static final AlchemicalPotion INSTANT_HEALING = new AlchemicalPotion("instant_healing",
                                                                                Color.Red,
                                                                                new StatusEffectInstance(StatusEffectsRegistry.INSTANT_HEALING,
                                                                                                         1));
    public static final AlchemicalPotion FERMENTED_ANTIDOTE = new AlchemicalPotion("fermented_antidote",
                                                                                   Color.Red,
                                                                                   new StatusEffectInstance(
                                                                                     StatusEffectsRegistry.FERMENTED_ANTIDOTE,
                                                                                     1200));
    public static final AlchemicalPotion FERMENTED_SATURATION = new AlchemicalPotion("fermented_saturation",
                                                                                     Color.Red,
                                                                                     new StatusEffectInstance(
                                                                                       StatusEffectsRegistry.FERMENTED_SATURATION,
                                                                                       1));

}