package faceless.artent.potions.registry;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModPotionEffects;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class StatusEffectsRegistry {

    public static final RegistryEntry.Reference<StatusEffect> VAMPIRISM = register("vampirism", ModPotionEffects.VAMPIRISM);
    public static final RegistryEntry.Reference<StatusEffect> HOLY_WATER = register("holy_water", ModPotionEffects.HOLY_WATER);
    public static final RegistryEntry.Reference<StatusEffect> BERSERK = register("berserk", ModPotionEffects.BERSERK);
    public static final RegistryEntry.Reference<StatusEffect> BERSERK_RECOIL = register("berserk_recoil", ModPotionEffects.BERSERK_RECOIL);
    public static final RegistryEntry.Reference<StatusEffect> STONE_SKIN = register("stone_skin", ModPotionEffects.STONE_SKIN);
    public static final RegistryEntry.Reference<StatusEffect> FREEZING = register("freezing", ModPotionEffects.FREEZING);
    // TODO
    public static final RegistryEntry.Reference<StatusEffect> LIQUID_FLAME = register("liquid_flame", ModPotionEffects.LIQUID_FLAME);
    public static final RegistryEntry.Reference<StatusEffect> ANTIDOTE = register("antidote", ModPotionEffects.ANTIDOTE);
    public static final RegistryEntry.Reference<StatusEffect> FAST_SWIMMING = register("fast_swimming", ModPotionEffects.FAST_SWIMMING);
    public static final RegistryEntry.Reference<StatusEffect> FEATHER_FALLING = register("feather_falling", ModPotionEffects.FEATHER_FALLING);
    public static final RegistryEntry.Reference<StatusEffect> FLIGHT = register("flight", ModPotionEffects.FLIGHT);
    public static final RegistryEntry.Reference<StatusEffect> SURFACE_TELEPORTATION = register("surface_teleportation", ModPotionEffects.SURFACE_TELEPORTATION);
    public static final RegistryEntry.Reference<StatusEffect> LUMBERJACK = register("lumberjack", ModPotionEffects.LUMBERJACK);
    public static final RegistryEntry.Reference<StatusEffect> FERMENTED_VAMPIRISM = register("fermented_vampirism", ModPotionEffects.FERMENTED_VAMPIRISM);
    public static final RegistryEntry.Reference<StatusEffect> FERMENTED_HOLY_WATER = register("fermented_holy_water", ModPotionEffects.FERMENTED_HOLY_WATER);
    public static final RegistryEntry.Reference<StatusEffect> FERMENTED_ANTIDOTE = register("fermented_antidote", ModPotionEffects.FERMENTED_ANTIDOTE);
    public static final RegistryEntry.Reference<StatusEffect> FERMENTED_SATURATION = register("fermented_saturation", ModPotionEffects.FERMENTED_SATURATION);
    public static final RegistryEntry.Reference<StatusEffect> FERMENTED_LIQUID_FLAME = register("fermented_liquid_flame", ModPotionEffects.FERMENTED_LIQUID_FLAME);

    private static RegistryEntry.Reference<StatusEffect> register(String id, StatusEffect entry) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(ArtentPotions.MODID, id), entry);
    }
}
