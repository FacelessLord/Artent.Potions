package faceless.artent.potions.objects;

import faceless.artent.core.math.Color;
import faceless.artent.potions.api.AttributeUuids;
import faceless.artent.potions.brewingApi.ArtentStatusEffect;
import faceless.artent.potions.brewingApi.ArtentInstantStatusEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ModPotionEffects {
  public static final StatusEffect VAMPIRISM = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
  public static final StatusEffect SANCTITY = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
  public static final StatusEffect BERSERK = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red)
      .addAttributeModifier(
          EntityAttributes.MOVEMENT_SPEED,
          AttributeUuids.BerserkModifier,
          0.30,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .addAttributeModifier(
          EntityAttributes.ATTACK_DAMAGE,
          AttributeUuids.BerserkModifier,
          0.30,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .addAttributeModifier(
          EntityAttributes.ARMOR_TOUGHNESS,
          AttributeUuids.BerserkModifier,
          0.30,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .addAttributeModifier(
          EntityAttributes.ATTACK_KNOCKBACK,
          AttributeUuids.BerserkModifier,
          0.30,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .addAttributeModifier(
          EntityAttributes.KNOCKBACK_RESISTANCE,
          AttributeUuids.BerserkModifier,
          0.30,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .addAttributeModifier(
          EntityAttributes.MAX_HEALTH,
          AttributeUuids.BerserkModifier,
          0.30,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
  public static final StatusEffect BERSERK_RECOIL = new ArtentStatusEffect(StatusEffectCategory.HARMFUL, Color.Red)
      .addAttributeModifier(
          EntityAttributes.MOVEMENT_SPEED,
          AttributeUuids.BerserkModifier,
          -0.5,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .addAttributeModifier(
          EntityAttributes.ATTACK_DAMAGE,
          AttributeUuids.BerserkModifier,
          -0.5,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .addAttributeModifier(
          EntityAttributes.ARMOR_TOUGHNESS,
          AttributeUuids.BerserkModifier,
          -0.5,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .addAttributeModifier(
          EntityAttributes.ATTACK_KNOCKBACK,
          AttributeUuids.BerserkModifier,
          -0.5,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .addAttributeModifier(
          EntityAttributes.KNOCKBACK_RESISTANCE,
          AttributeUuids.BerserkModifier,
          -0.5,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .addAttributeModifier(
          EntityAttributes.MAX_HEALTH,
          AttributeUuids.BerserkModifier,
          -0.5,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
  public static final StatusEffect STONE_SKIN = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red)
      .addAttributeModifier(
          EntityAttributes.MOVEMENT_SPEED,
          AttributeUuids.StoneSkin,
          -0.1,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .addAttributeModifier(
          EntityAttributes.ARMOR_TOUGHNESS,
          AttributeUuids.StoneSkin,
          1.5,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
  public static final StatusEffect FREEZING = new ArtentStatusEffect(StatusEffectCategory.HARMFUL, Color.LightBlue);
  public static final StatusEffect HOT_CHOCOLATE = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Brown);
  public static final StatusEffect SATURATION = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
  public static final StatusEffect LIQUID_FLAME = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
  public static final StatusEffect ANTIDOTE = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
  public static final StatusEffect FORTUNE = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);

  public static final StatusEffect FEATHER_FALLING = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
  public static final StatusEffect FLIGHT = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
  public static final StatusEffect SURFACE_TELEPORTATION = new ArtentInstantStatusEffect(
      StatusEffectCategory.BENEFICIAL,
      Color.Red);
  public static final StatusEffect LUMBERJACK = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);

  public static final StatusEffect BLEEDING = new ArtentStatusEffect(StatusEffectCategory.HARMFUL, Color.Red);
  public static final StatusEffect INSTANT_HEALING = new ArtentInstantStatusEffect(
      StatusEffectCategory.BENEFICIAL,
      Color.Red);

  public static final StatusEffect FLAMING_SOUL = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
  //  public static final StatusEffect FERMENTED_ANTIDOTE = new ArtentInstantStatusEffect(
//      StatusEffectCategory.BENEFICIAL,
//      Color.Red);
  public static final StatusEffect LIQUID_MEAT = new ArtentInstantStatusEffect(
      StatusEffectCategory.BENEFICIAL,
      Color.Red);
  public static final StatusEffect VAMPIRE_BARON = new ArtentStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
  public static final StatusEffect CLEANSING = new ArtentInstantStatusEffect(
      StatusEffectCategory.BENEFICIAL,
      Color.Red);
  public static final StatusEffect BLINK = new ArtentInstantStatusEffect(StatusEffectCategory.BENEFICIAL, Color.Red);
}