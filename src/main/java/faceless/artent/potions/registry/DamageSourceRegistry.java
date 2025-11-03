package faceless.artent.potions.registry;

import faceless.artent.potions.ArtentPotions;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class DamageSourceRegistry {
  public static final Identifier BleedingDamageId = Identifier.of(ArtentPotions.MODID, "bleeding");
  public static final RegistryKey<DamageType> BleedingDamageKey = RegistryKey.of(
      RegistryKeys.DAMAGE_TYPE,
      BleedingDamageId);

  public static void bootstrap(Registerable<DamageType> damageTypeRegistry) {
    damageTypeRegistry.register(BleedingDamageKey, new DamageType(ArtentPotions.MODID + "_bleeding", 0.0f));
  }
}
