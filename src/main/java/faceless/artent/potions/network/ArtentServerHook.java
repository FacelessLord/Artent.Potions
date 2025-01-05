package faceless.artent.potions.network;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.blockEntities.BrewingCauldronBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class ArtentServerHook {
  public void load() {
    System.out.println("ArtentServerHook load");
    PayloadTypeRegistry.playS2C().register(CauldronSyncPayload.PayloadId, CauldronSyncPayload.CODEC);
  }

  public static void packetSyncCauldron(PlayerEntity player, BrewingCauldronBlockEntity entity) {
    if (player.getWorld().isClient)
      return;

    var registryManager = player.getWorld().getRegistryManager();
    var nbt = entity.createNbt(registryManager);
    var payload = new CauldronSyncPayload(entity.getPos(), nbt);
    ServerPlayNetworking.send((ServerPlayerEntity) player, payload);
  }
}
