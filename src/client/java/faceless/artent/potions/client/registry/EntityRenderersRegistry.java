package faceless.artent.potions.client.registry;


import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.client.entity.FrostedGolemEntityRegistry;
import faceless.artent.potions.client.entity.ManaChickenEntityRenderer;
import faceless.artent.potions.objects.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class EntityRenderersRegistry implements IRegistry {
  @Override
  public void register() {
    EntityRendererRegistry.register(ModEntities.POTION_PHIAL, FlyingItemEntityRenderer::new);
    EntityRendererRegistry.register(ModEntities.MANA_CHICKEN, ManaChickenEntityRenderer::new);
    EntityRendererRegistry.register(ModEntities.FROSTED_GOLEM, FrostedGolemEntityRegistry::new);
  }
}
