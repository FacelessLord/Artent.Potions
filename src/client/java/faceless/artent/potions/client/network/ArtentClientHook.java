package faceless.artent.potions.client.network;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.blockEntities.BrewingCauldronBlockEntity;
import faceless.artent.potions.network.CauldronSyncPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class ArtentClientHook {

  public void loadClient() {
    System.out.println("Artent.Potions Network Hook client side load");
    ClientPlayNetworking
        .registerGlobalReceiver(
            CauldronSyncPayload.PayloadId,
            (payload, context) -> {
              BlockPos pos = payload.pos();
              var nbt = payload.nbt();

              // Client sided code
              try (var client = context.client()) {
                client.execute(() -> {
                  if (client.player == null)
                    return;

                  var world = client.player.getWorld();
                  if (world == null)
                    return;

                  BlockEntity blockEntity = world.getBlockEntity(pos);
                  if (!(blockEntity instanceof BrewingCauldronBlockEntity cauldron) || blockEntity.getWorld() == null)
                    return;

                  cauldron.readNbt(nbt, blockEntity.getWorld().getRegistryManager());
                });
              }
            });
  }
}
