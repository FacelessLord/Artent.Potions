package faceless.artent.potions.network;

import faceless.artent.potions.blockEntities.BrewingCauldronBlockEntity;
import faceless.artent.potions.blockEntities.DryingRackBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Arrays;

public class ArtentServerHook {
  public void load() {
    System.out.println("ArtentServerHook load");
    PayloadTypeRegistry.playS2C().register(CauldronSyncPayload.PayloadId, CauldronSyncPayload.CODEC);
    PayloadTypeRegistry.playS2C().register(DryingRackSyncPayload.PayloadId, DryingRackSyncPayload.CODEC);
  }

  public static void packetSyncCauldron(PlayerEntity player, BrewingCauldronBlockEntity entity) {
    if (player.getWorld().isClient)
      return;

    var payload = new CauldronSyncPayload(
        entity.getPos(),
        entity.fuelAmount,
        entity.potionAmount,
        entity.crystalsRequired,
        entity.color,
        entity.ingredients,
        entity.potions);
    ServerPlayNetworking.send((ServerPlayerEntity) player, payload);
  }

  public static void packetSyncDryingRack(PlayerEntity player, DryingRackBlockEntity entity) {
    if (player.getWorld().isClient)
      return;

    var payload = new DryingRackSyncPayload(
        entity.getPos(),
        Arrays.stream(entity.items).toList(),
        Arrays.stream(entity.timesLeft).boxed().toList(),
        Arrays.stream(entity.byproducts).toList()
    );
    ServerPlayNetworking.send((ServerPlayerEntity) player, payload);
  }
}
