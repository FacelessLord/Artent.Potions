package faceless.artent.potions.client.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ArtentClientHook {

    public void loadClient() {
        System.out.println("Artent.Potions Network Hook client side load");
//        ClientPlayNetworking
//          .registerGlobalReceiver(
//            ArtentServerHook.SYNC_CAULDRON_PACKET_ID,
//            (buffer, context) -> {
//
//                BlockPos pos = buffer.readBlockPos();
//                var nbt = buffer.readNbt();
//
//                // Client sided code
//                client.execute(() -> {
//                    if (client.player == null)
//                        return;
//
//                    var world = client.player.getWorld();
//                    if (world == null)
//                        return;
//
//                    BlockEntity blockEntity = world.getBlockEntity(pos);
//                    if (!(blockEntity instanceof BrewingCauldronBlockEntity cauldron))
//                        return;
//
//                    cauldron.readNbt(nbt);
//                });
//            });
    }
}
