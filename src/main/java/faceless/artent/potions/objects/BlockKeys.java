package faceless.artent.potions.objects;

import faceless.artent.potions.ArtentPotions;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class BlockKeys {
  public static final RegistryKey<Block> FROST_PUMPKIN_CARVED = of("frost_pumpkin_carved");
  public static final RegistryKey<Block> FROST_PUMPKIN = of("frost_pumpkin");
  public static final RegistryKey<Block> FROST_PUMPKIN_STEM = of("frost_pumpkin_stem");
  public static final RegistryKey<Block> FROST_PUMPKIN_STEM_ATTACHED = of("frost_pumpkin_stem_attached");

  public static final RegistryKey<Block> BLAZING_MARIGOLD_CROPS = of("blazing_marigold_crops");
  public static final RegistryKey<Block> SHADOWVEIL_CROPS = of("shadowveil_crops");
  public static final RegistryKey<Block> SLIME_BERRY_CROPS = of("slime_berry_crops");

  private static RegistryKey<Block> of(String id) {
    return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(ArtentPotions.MODID, id));
  }
}

