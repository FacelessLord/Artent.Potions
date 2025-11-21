package faceless.artent.potions.registry;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModPotionEffects;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class StatusEffectsRegistry implements IRegistry {

  public static RegistryEntry.Reference<StatusEffect> VAMPIRISM;
  public static RegistryEntry.Reference<StatusEffect> HOLY_WATER;
  public static RegistryEntry.Reference<StatusEffect> BERSERK;
  public static RegistryEntry.Reference<StatusEffect> BERSERK_RECOIL;
  public static RegistryEntry.Reference<StatusEffect> STONE_SKIN;
  public static RegistryEntry.Reference<StatusEffect> FREEZING;
  public static RegistryEntry.Reference<StatusEffect> SATURATION;
  public static RegistryEntry.Reference<StatusEffect> ANTIDOTE;
  public static RegistryEntry.Reference<StatusEffect> FORTUNE;

  public static RegistryEntry.Reference<StatusEffect> LIQUID_FLAME;
  public static RegistryEntry.Reference<StatusEffect> FEATHER_FALLING;
  public static RegistryEntry.Reference<StatusEffect> FLIGHT;
  public static RegistryEntry.Reference<StatusEffect> LUMBERJACK;
  public static RegistryEntry.Reference<StatusEffect> BLEEDING;
  public static RegistryEntry.Reference<StatusEffect> INSTANT_HEALING;

  public static RegistryEntry.Reference<StatusEffect> SURFACE_TELEPORTATION;
  public static RegistryEntry.Reference<StatusEffect> FERMENTED_SATURATION;
  public static RegistryEntry.Reference<StatusEffect> FERMENTED_LIQUID_FLAME;
  public static RegistryEntry.Reference<StatusEffect> FERMENTED_ANTIDOTE;

  public static RegistryEntry.Reference<StatusEffect> FERMENTED_VAMPIRISM;
  public static RegistryEntry.Reference<StatusEffect> FERMENTED_HOLY_WATER;

  private static RegistryEntry.Reference<StatusEffect> register(String id, StatusEffect entry) {
    return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(ArtentPotions.MODID, id), entry);
  }

  @Override
  public void register() {
    VAMPIRISM = register("vampirism", ModPotionEffects.VAMPIRISM);
    HOLY_WATER = register("holy_water", ModPotionEffects.HOLY_WATER);
    BERSERK = register("berserk", ModPotionEffects.BERSERK);
    BERSERK_RECOIL = register("berserk_recoil", ModPotionEffects.BERSERK_RECOIL);
    STONE_SKIN = register("stone_skin", ModPotionEffects.STONE_SKIN);
    FREEZING = register("freezing", ModPotionEffects.FREEZING);
    SATURATION = register("saturation", ModPotionEffects.SATURATION);
    ANTIDOTE = register("antidote", ModPotionEffects.ANTIDOTE);
    FORTUNE = register("fortune", ModPotionEffects.FORTUNE);

    LIQUID_FLAME = register("liquid_flame", ModPotionEffects.LIQUID_FLAME);
    FEATHER_FALLING = register("feather_falling", ModPotionEffects.FEATHER_FALLING);
    FLIGHT = register("flight", ModPotionEffects.FLIGHT);
    LUMBERJACK = register("lumberjack", ModPotionEffects.LUMBERJACK);
    BLEEDING = register("bleeding", ModPotionEffects.BLEEDING);
    INSTANT_HEALING = register("instant_healing", ModPotionEffects.INSTANT_HEALING);

    SURFACE_TELEPORTATION = register("surface_teleportation", ModPotionEffects.SURFACE_TELEPORTATION);
    FERMENTED_SATURATION = register("fermented_saturation", ModPotionEffects.FERMENTED_SATURATION);
    FERMENTED_LIQUID_FLAME = register("fermented_liquid_flame", ModPotionEffects.FERMENTED_LIQUID_FLAME);
    FERMENTED_ANTIDOTE = register("fermented_antidote", ModPotionEffects.FERMENTED_ANTIDOTE);

    FERMENTED_VAMPIRISM = register("fermented_vampirism", ModPotionEffects.FERMENTED_VAMPIRISM);
    FERMENTED_HOLY_WATER = register("fermented_holy_water", ModPotionEffects.FERMENTED_HOLY_WATER);

  }
}
