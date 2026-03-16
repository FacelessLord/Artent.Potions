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
    public static final RegistryEntry.Reference<StatusEffect> SANCTITY = register("sanctity", ModPotionEffects.SANCTITY);
    public static final RegistryEntry.Reference<StatusEffect> BERSERK = register("berserk", ModPotionEffects.BERSERK);
    public static final RegistryEntry.Reference<StatusEffect> BERSERK_RECOIL = register("berserk_recoil", ModPotionEffects.BERSERK_RECOIL);
    public static final RegistryEntry.Reference<StatusEffect> STONE_SKIN = register("stone_skin", ModPotionEffects.STONE_SKIN);
    public static final RegistryEntry.Reference<StatusEffect> FREEZING = register("freezing", ModPotionEffects.FREEZING);
    public static final RegistryEntry.Reference<StatusEffect> SATURATION = register("saturation", ModPotionEffects.SATURATION);
    public static final RegistryEntry.Reference<StatusEffect> HOT_CHOCOLATE = register("hot_chocolate", ModPotionEffects.HOT_CHOCOLATE);
    public static final RegistryEntry.Reference<StatusEffect> ANTIDOTE = register("antidote", ModPotionEffects.ANTIDOTE);
    public static final RegistryEntry.Reference<StatusEffect> FORTUNE = register("fortune", ModPotionEffects.FORTUNE);

    public static final RegistryEntry.Reference<StatusEffect> LIQUID_FLAME = register("liquid_flame", ModPotionEffects.LIQUID_FLAME);
    public static final RegistryEntry.Reference<StatusEffect> FEATHER_FALLING = register("feather_falling", ModPotionEffects.FEATHER_FALLING);
    public static final RegistryEntry.Reference<StatusEffect> FLIGHT = register("flight", ModPotionEffects.FLIGHT);
    public static final RegistryEntry.Reference<StatusEffect> LUMBERJACK = register("lumberjack", ModPotionEffects.LUMBERJACK);
    public static final RegistryEntry.Reference<StatusEffect> BLEEDING = register("bleeding", ModPotionEffects.BLEEDING);
    public static final RegistryEntry.Reference<StatusEffect> INSTANT_HEALING = register("instant_healing", ModPotionEffects.INSTANT_HEALING);

    public static final RegistryEntry.Reference<StatusEffect> SURFACE_TELEPORTATION = register("surface_teleportation", ModPotionEffects.SURFACE_TELEPORTATION);
    public static final RegistryEntry.Reference<StatusEffect> LIQUID_MEAT = register("liquid_meat", ModPotionEffects.LIQUID_MEAT);
    public static final RegistryEntry.Reference<StatusEffect> FLAMING_SOUL = register("flaming_soul", ModPotionEffects.FLAMING_SOUL);
//    public static final RegistryEntry.Reference<StatusEffect> FERMENTED_ANTIDOTE = register("fermented_antidote", ModPotionEffects.FERMENTED_ANTIDOTE);

    public static final RegistryEntry.Reference<StatusEffect> VAMPIRE_BARON = register("vampire_baron", ModPotionEffects.VAMPIRE_BARON);
    public static final RegistryEntry.Reference<StatusEffect> CLEANSING = register("cleansing", ModPotionEffects.CLEANSING);
    public static final RegistryEntry.Reference<StatusEffect> BLINK = register("blink", ModPotionEffects.BLINK);

    private static RegistryEntry.Reference<StatusEffect> register(String id, StatusEffect entry) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(ArtentPotions.MODID, id), entry);
    }
}
