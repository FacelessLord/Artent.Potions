package faceless.artent.potions.registry;

import com.mojang.serialization.Codec;
import faceless.artent.potions.ArtentPotions;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;

public class DataComponentRegistry {
  public static final ComponentType<List<String>> POTION_KEY = Registry.register(
      Registries.DATA_COMPONENT_TYPE,
      Identifier.of(ArtentPotions.MODID, "potion_key"),
      ComponentType.<List<String>>builder().codec(Codec.list(Codec.STRING)).build()
                                                                                );
  public static final ComponentType<Integer> CONCENTRATE_AMOUNT = Registry.register(
      Registries.DATA_COMPONENT_TYPE,
      Identifier.of(ArtentPotions.MODID, "amount"),
      ComponentType.<Integer>builder().codec(Codec.INT).build()
                                                                                   );
}
