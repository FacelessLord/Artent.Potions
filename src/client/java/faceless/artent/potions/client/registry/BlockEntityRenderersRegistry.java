package faceless.artent.potions.client.registry;


import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.client.renderers.BrewingCauldronRenderer;
import faceless.artent.potions.client.renderers.DryingRackRenderer;
import faceless.artent.potions.objects.ModBlockEntities;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class BlockEntityRenderersRegistry implements IRegistry {
  @Override
  public void register() {
    BlockEntityRendererFactories.register(ModBlockEntities.BrewingCauldron, BrewingCauldronRenderer::new);
    BlockEntityRendererFactories.register(ModBlockEntities.DryingRack, DryingRackRenderer::new);
  }
}
