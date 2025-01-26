package faceless.artent.potions.client.registry;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.objects.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;


public class BlockRenderLayerMapRegistry implements IRegistry {
  @Override
  public void register() {
    BlockRenderLayerMap.INSTANCE.putBlocks(
        RenderLayer.getCutout(),
        ModBlocks.Shroom,
        ModBlocks.Shadowveil,
        ModBlocks.berryBush[0],
        ModBlocks.berryBush[1],
        ModBlocks.berryBush[2],
        ModBlocks.berryBush[3],
        ModBlocks.CrimsonwoodLeaves,
        ModBlocks.CrimsonwoodSapling,
        ModBlocks.FrostPumpkin);
  }
}
