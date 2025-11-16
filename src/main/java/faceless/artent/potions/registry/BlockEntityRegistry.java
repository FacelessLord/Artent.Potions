package faceless.artent.potions.registry;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.api.RegisteredBlock;
import faceless.artent.potions.blockEntities.BrewingCauldronBlockEntity;
import faceless.artent.potions.blockEntities.DryingRackBlockEntity;
import faceless.artent.potions.blockEntities.FermentingBarrelBlockEntity;
import faceless.artent.potions.blockEntities.ShelfBlockEntity;
import faceless.artent.potions.objects.ModBlockEntities;
import faceless.artent.potions.objects.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class BlockEntityRegistry implements IRegistry {
  @Override
  public void register() {
    ModBlockEntities.BrewingCauldron = register(
        BrewingCauldronBlockEntity::new,
        "cauldron_entity",
        ModBlocks.BREWING_CAULDRON.block(),
        ModBlocks.BREWING_CAULDRON_COPPER.block());
    ModBlockEntities.FermentingBarrel = register(
        FermentingBarrelBlockEntity::new,
        "fermenting_barrel",
        ModBlocks.FEMENTING_BARREL.block());
    ModBlockEntities.DryingRack = register(DryingRackBlockEntity::new, "drying_rack", ModBlocks.DRYING_RACK.block());
    ModBlockEntities.Shelf = register(
        ShelfBlockEntity::new, "shelf", Arrays.stream(ModBlocks.SHELVES).map(
            RegisteredBlock::block).toArray(Block[]::new));
  }

  public <T extends BlockEntity> BlockEntityType<T> register(
      FabricBlockEntityTypeBuilder.Factory<T> item,
      String id,
      Block... block) {
    return Registry.register(
        Registries.BLOCK_ENTITY_TYPE,
        Identifier.of(ArtentPotions.MODID, id),
        FabricBlockEntityTypeBuilder.create(item, block).build());
  }
}
