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
        ModBlocks.Shroom.block(),
        ModBlocks.Shadowveil.block(),
        ModBlocks.BlazingMarigold.block(),
        ModBlocks.SlimeBerry.block(),
        ModBlocks.berryBush[0].block(),
        ModBlocks.berryBush[1].block(),
        ModBlocks.berryBush[2].block(),
        ModBlocks.berryBush[3].block(),
        ModBlocks.CrimsonwoodLeaves.block(),
        ModBlocks.CrimsonwoodSapling.block(),
        ModBlocks.DryingRack.block(),
        ModBlocks.FrostPumpkinCarved.block(),
        ModBlocks.FrostPumpkin.block(),
        ModBlocks.FrostPumpkinStem.block(),
        ModBlocks.FrostPumpkinStemAttached.block(),
        ModBlocks.MushroomInfo[0].growingMushroom(),
        ModBlocks.MushroomInfo[1].growingMushroom(),
        ModBlocks.MushroomInfo[2].growingMushroom(),
        ModBlocks.IceCrystalBlock.block(),
        ModBlocks.IceCrystalBud_Cluster.block(),
        ModBlocks.IceCrystalBud_Large.block(),
        ModBlocks.IceCrystalBud_Medium.block(),
        ModBlocks.IceCrystalBud_Small.block());
  }
}
