package faceless.artent.potions.objects;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.entity.ManaChicken;
import faceless.artent.potions.entity.ThrowablePotionPhialEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ModEntities {
  public static EntityType<ThrowablePotionPhialEntity> POTION_PHIAL = EntityType.Builder
      .create(
          (EntityType<ThrowablePotionPhialEntity> type, World world) -> new ThrowablePotionPhialEntity(type, world),
          SpawnGroup.MISC)
      .dimensions(0.25f, 0.25f)
      .maxTrackingRange(4)
      .trackingTickInterval(10)
      .build(RegistryKey.of(
          Registries.ENTITY_TYPE.getKey(),
          Identifier.of(ArtentPotions.MODID, "potion_phial_entity")));
  public static EntityType<ManaChicken> MANA_CHICKEN = EntityType.Builder
      .create(ManaChicken::new, SpawnGroup.CREATURE)
      .dimensions(0.4F, 0.7F)
      .eyeHeight(0.644F)
      .passengerAttachments(new Vec3d(0.0, 0.7, -0.1))
      .maxTrackingRange(10)
      .build(RegistryKey.of(
          Registries.ENTITY_TYPE.getKey(),
          Identifier.of(ArtentPotions.MODID, "mana_chicken")));
}