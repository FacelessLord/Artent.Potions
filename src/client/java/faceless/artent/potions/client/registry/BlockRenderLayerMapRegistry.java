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
        ModBlocks.SHROOM.block(),
        ModBlocks.SHADOWVEIL.block(),
        ModBlocks.BLAZING_MARIGOLD.block(),
        ModBlocks.SLIME_BERRY.block(),
        ModBlocks.BERRY_BUSH[0].block(),
        ModBlocks.BERRY_BUSH[1].block(),
        ModBlocks.BERRY_BUSH[2].block(),
        ModBlocks.BERRY_BUSH[3].block(),
        ModBlocks.CRIMSONWOOD_LEAVES.block(),
        ModBlocks.CRIMSONWOOD_SAPLING.block(),
        ModBlocks.DRYING_RACK.block(),
        ModBlocks.FROST_PUMPKIN_CARVED.block(),
        ModBlocks.FROST_PUMPKIN.block(),
        ModBlocks.FROST_PUMPKIN_STEM.block(),
        ModBlocks.FROST_PUMPKIN_STEM_ATTACHED.block(),
        ModBlocks.MUSHROOM_INFO[0].growingMushroom(),
        ModBlocks.MUSHROOM_INFO[1].growingMushroom(),
        ModBlocks.MUSHROOM_INFO[2].growingMushroom(),
        ModBlocks.ICE_CRYSTAL_BLOCK.block(),
        ModBlocks.ICE_CRYSTAL_CLUSTER.block(),
        ModBlocks.ICE_CRYSTAL_BUD_LARGE.block(),
        ModBlocks.ICE_CRYSTAL_BUD_MEDIUM.block(),
        ModBlocks.ICE_CRYSTAL_BUD_SMALL.block());
  }
}
