package faceless.artent.potions.objects;

import faceless.artent.potions.ArtentPotions;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ItemKeys {
  public static final RegistryKey<Item> FROST_PUMPKIN_SEEDS = of("frost_pumpkin_seeds");

  private static RegistryKey<Item> of(String id) {
    return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ArtentPotions.MODID, id));
  }
}

