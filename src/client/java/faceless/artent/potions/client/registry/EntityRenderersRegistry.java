package faceless.artent.potions.client.registry;


import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.objects.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class EntityRenderersRegistry implements IRegistry {
  @Override
  public void register() {
    EntityRendererRegistry.register(ModEntities.POTION_PHIAL, FlyingItemEntityRenderer::new);
  }
}
