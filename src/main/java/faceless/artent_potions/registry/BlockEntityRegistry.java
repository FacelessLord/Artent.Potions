package faceless.artent_potions.registry;

import faceless.artent_potions.Artent;
import faceless.artent_potions.brewing.blockEntities.BrewingCauldronBlockEntity;
import faceless.artent_potions.brewing.blockEntities.FermentingBarrelBlockEntity;
import faceless.artent_potions.objects.ModBlockEntities;
import faceless.artent_potions.objects.ModBlocks;
import faceless.artent_potions.sharpening.blockEntity.SharpeningAnvilBlockEntity;
import faceless.artent_potions.spells.blockEntity.InscriptionTableBlockEntity;
import faceless.artent_potions.spells.blockEntity.VoidBlockEntity;
import faceless.artent_potions.trading.blockEntities.TraderBlockEntity;
import faceless.artent_potions.transmutations.blockEntities.AlchemicalCircleEntity;
import faceless.artent_potions.ArtentPotions;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockEntityRegistry implements IRegistry {
    @Override
    public void register() {
        ModBlockEntities.BrewingCauldron = register(BrewingCauldronBlockEntity::new,
                                                    "cauldron_entity",
                                                    ModBlocks.BrewingCauldron,
                                                    ModBlocks.BrewingCauldronCopper);
        ModBlockEntities.FermentingBarrel = register(FermentingBarrelBlockEntity::new,
                                                     "fermenting_barrel",
                                                     ModBlocks.FermentingBarrel);
    }

    public <T extends BlockEntity> BlockEntityType<T> register(
      FabricBlockEntityTypeBuilder.Factory<T> item, String id,
      Block... block
    ) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ArtentPotions.MODID, id),
                                 FabricBlockEntityTypeBuilder.create(item, block).build(null));
    }
}
