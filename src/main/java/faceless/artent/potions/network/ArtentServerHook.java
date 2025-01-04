package faceless.artent.potions.network;

import faceless.artent.potions.ArtentPotions;
import net.minecraft.util.Identifier;

public class ArtentServerHook {

    public static final Identifier SYNC_CAULDRON_PACKET_ID = Identifier.of(ArtentPotions.MODID, "packet.cauldron.sync");

    public void load() {
        System.out.println("ArtentServerHook load");
    }

//    public static void packetSyncCauldron(PlayerEntity player, BrewingCauldronBlockEntity entity) {
//        if (player.getWorld().isClient)
//            return;
//
//        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
//        passedData.writeBlockPos(entity.getPos());
//        var nbt = entity.createNbt();
//        passedData.writeNbt(nbt);
//        ServerPlayNetworking.send((ServerPlayerEntity) player, SYNC_CAULDRON_PACKET_ID, passedData);
//    }
}
