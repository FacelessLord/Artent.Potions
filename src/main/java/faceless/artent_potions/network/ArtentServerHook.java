package faceless.artent_potions.network;

import faceless.artent_potions.blockEntities.BrewingCauldronBlockEntity;
import faceless.artent_potions.ArtentPotions;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ArtentServerHook {

    public static final Identifier SYNC_CAULDRON_PACKET_ID = Identifier.of(ArtentPotions.MODID, "packet.cauldron.sync");

    public void load() {
        System.out.println("ArtentServerHook load");
    }

    public static void packetSyncCauldron(PlayerEntity player, BrewingCauldronBlockEntity entity) {
        if (player.getWorld().isClient)
            return;

        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        passedData.writeBlockPos(entity.getPos());
        var nbt = entity.createNbt();
        passedData.writeNbt(nbt);
        ServerPlayNetworking.send((ServerPlayerEntity) player, SYNC_CAULDRON_PACKET_ID, passedData);
    }
}
