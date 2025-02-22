package faceless.artent.potions.registry;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.brewingApi.AlchemicalPotion;

import java.util.Hashtable;

import static faceless.artent.potions.objects.AlchemicalPotions.*;

public class AlchemicalPotionRegistry implements IRegistry {
    private static final Hashtable<String, AlchemicalPotion> RegisteredPotions = new Hashtable<>();
    private static final Hashtable<String, AlchemicalPotion> FermentedPotions = new Hashtable<>();

    public static Hashtable<String, AlchemicalPotion> getRegisteredPotions() {
        return RegisteredPotions;
    }

    public static Hashtable<String, AlchemicalPotion> getFermentedPotions() {
        return FermentedPotions;
    }

    @Override
    public void register() {
        register(POISON);
        register(STRENGTH);
        register(VAMPIRISM, FERMENTED_VAMPIRISM); // TODO вапиризм + эффект кровотечения у жертвы, который также восстанавливает хп атакующего
        register(HOLY_WATER, FERMENTED_HOLY_WATER); // TODO снимает дебаффы
        register(BERSERK);

        register(STONE_SKIN);
        register(FIRE_RESISTANCE);
        register(FREEZING);
        register(LIQUID_FLAME, FERMENTED_LIQUID_FLAME);
        register(HEALING, INSTANT_HEALING);
        register(ANTIDOTE, FERMENTED_ANTIDOTE);

        register(FAST_SWIMMING);
        register(WATER_BREATHING);
        register(JUMP_BOOST);
        register(FEATHER_FALLING);
        register(NIGHT_VISION);

        register(FLIGHT);
        register(FORTUNE);
        register(SATURATION, FERMENTED_SATURATION);
        register(LUMBERJACK);
        register(HASTE);
        register(LEVITATION, SURFACE_TELEPORTATION);
    }

    public void register(AlchemicalPotion potion) {
        RegisteredPotions.put(potion.id, potion);
    }

    public void register(AlchemicalPotion potion, AlchemicalPotion fermented) {
        RegisteredPotions.put(potion.id, potion);
        FermentedPotions.put(potion.id, fermented);
    }
//
//	public static boolean potionIsRegistered(String id) {
//		return RegisteredPotions.containsKey(id);
//	}

    public static boolean fermentedPotionIsRegistered(String id) {
        return FermentedPotions.containsKey(id);
    }


}
