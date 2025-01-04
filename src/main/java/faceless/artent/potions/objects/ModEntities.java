package faceless.artent.potions.objects;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.entity.ThrowablePotionPhialEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModEntities {
  public static EntityType<ThrowablePotionPhialEntity> POTION_PHIAL = EntityType.Builder
      .create(
          (EntityType<ThrowablePotionPhialEntity> type, World world) -> new ThrowablePotionPhialEntity(type, world),
          SpawnGroup.MISC)
      .dimensions(0.25f, 0.25f)
      .maxTrackingRange(4)
      .trackingTickInterval(10)
      .build(RegistryKey.of(Registries.ENTITY_TYPE.getKey(),
                            Identifier.of(ArtentPotions.MODID, "potion_phial_entity")));
}