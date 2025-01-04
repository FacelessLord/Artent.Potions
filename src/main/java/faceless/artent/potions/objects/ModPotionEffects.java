package faceless.artent.potions.objects;

import faceless.artent.core.math.Color;
import faceless.artent.potions.api.AttributeUuids;
import faceless.artent.potions.brewingApi.ArtentStatusEffect;
import faceless.artent.potions.brewingApi.ConcentrateStatusEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ModPotionEffects {
    public static final StatusEffect VAMPIRISM = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
    public static final StatusEffect HOLY_WATER = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
    public static final StatusEffect BERSERK =
      new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red)
        .addAttributeModifier(EntityAttributes.MOVEMENT_SPEED,
                              AttributeUuids.BerserkModifier,
                              0.30,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        .addAttributeModifier(EntityAttributes.ATTACK_DAMAGE,
                              AttributeUuids.BerserkModifier,
                              0.30,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        .addAttributeModifier(EntityAttributes.ARMOR_TOUGHNESS,
                              AttributeUuids.BerserkModifier,
                              0.30,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        .addAttributeModifier(EntityAttributes.ATTACK_KNOCKBACK,
                              AttributeUuids.BerserkModifier,
                              0.30,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        .addAttributeModifier(EntityAttributes.KNOCKBACK_RESISTANCE,
                              AttributeUuids.BerserkModifier,
                              0.30,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        .addAttributeModifier(EntityAttributes.MAX_HEALTH,
                              AttributeUuids.BerserkModifier,
                              0.30,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    public static final StatusEffect BERSERK_RECOIL =
      new ArtentStatusEffect(StatusEffectCategory.HARMFUL, Color.Red)
        .addAttributeModifier(EntityAttributes.MOVEMENT_SPEED,
                              AttributeUuids.BerserkModifier,
                              -0.5,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        .addAttributeModifier(EntityAttributes.ATTACK_DAMAGE,
                              AttributeUuids.BerserkModifier,
                              -0.5,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        .addAttributeModifier(EntityAttributes.ARMOR_TOUGHNESS,
                              AttributeUuids.BerserkModifier,
                              -0.5,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        .addAttributeModifier(EntityAttributes.ATTACK_KNOCKBACK,
                              AttributeUuids.BerserkModifier,
                              -0.5,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        .addAttributeModifier(EntityAttributes.KNOCKBACK_RESISTANCE,
                              AttributeUuids.BerserkModifier,
                              -0.5,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        .addAttributeModifier(EntityAttributes.MAX_HEALTH,
                              AttributeUuids.BerserkModifier,
                              -0.5,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    public static final StatusEffect STONE_SKIN =
      new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red)
        .addAttributeModifier(EntityAttributes.MOVEMENT_SPEED,
                              AttributeUuids.BerserkModifier,
                              -0.5,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        .addAttributeModifier(EntityAttributes.ARMOR_TOUGHNESS,
                              AttributeUuids.BerserkModifier,
                              1.5,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    public static final StatusEffect FREEZING =
      new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red, false)
        .addAttributeModifier(EntityAttributes.MOVEMENT_SPEED,
                              AttributeUuids.Freezing,
                              -0.8,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    // TODO
    public static final StatusEffect LIQUID_FLAME = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
    public static final StatusEffect ANTIDOTE = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
    public static final StatusEffect FAST_SWIMMING =
      new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red)
        .addAttributeModifier(EntityAttributes.MOVEMENT_SPEED,
                              AttributeUuids.SwimmingModifier,
                              1,
                              EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    public static final StatusEffect FEATHER_FALLING = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL,
                                                                              Color.Red);
    public static final StatusEffect FLIGHT = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
    public static final StatusEffect SURFACE_TELEPORTATION = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL,
                                                                                    Color.Red,
                                                                                    true);
    public static final StatusEffect LUMBERJACK = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
    public static final StatusEffect FERMENTED_VAMPIRISM = new ConcentrateStatusEffect(StatusEffectCategory.BENEFICIAL,
                                                                                       Color.Red,
                                                                                       false);
    public static final StatusEffect FERMENTED_HOLY_WATER = new ConcentrateStatusEffect(StatusEffectCategory.BENEFICIAL,
                                                                                        Color.Red,
                                                                                        false);
    public static final StatusEffect FERMENTED_ANTIDOTE = new ConcentrateStatusEffect(StatusEffectCategory.BENEFICIAL,
                                                                                      Color.Red);
    public static final StatusEffect FERMENTED_SATURATION = new ConcentrateStatusEffect(StatusEffectCategory.BENEFICIAL,
                                                                                        Color.Red);
    public static final StatusEffect FERMENTED_LIQUID_FLAME = new ConcentrateStatusEffect(StatusEffectCategory.BENEFICIAL,
                                                                                          Color.Red);
}