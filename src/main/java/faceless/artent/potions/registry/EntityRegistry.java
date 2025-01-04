package faceless.artent.potions.registry;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityRegistry implements IRegistry {
  @Override
  public void register() {
    register("potion_phial_entity", ModEntities.POTION_PHIAL);
  }

  private static <T extends Entity> void register(String id, EntityType<T> type) {
    Registry.register(Registries.ENTITY_TYPE, Identifier.of(ArtentPotions.MODID, id), type);
  }
}
