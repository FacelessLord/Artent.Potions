package faceless.artent.potions.network;

import faceless.artent.potions.ArtentPotions;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public record ShelfSyncPayload(
    BlockPos pos, List<ItemStack> items
) implements CustomPayload {

  public static final Id<ShelfSyncPayload> PayloadId = new Id<>(Identifier.of(
      ArtentPotions.MODID,
      "packet.shelf.sync"));
  public static final PacketCodec<RegistryByteBuf, ShelfSyncPayload> CODEC = PacketCodec.tuple(
      BlockPos.PACKET_CODEC,
      (ShelfSyncPayload payload) -> payload.pos,
      PacketCodecs.collection(ArrayList::new, ItemStack.OPTIONAL_PACKET_CODEC),
      (ShelfSyncPayload payload) -> payload.items,
      ShelfSyncPayload::new);

  @Override
  public Id<? extends CustomPayload> getId() {
    return PayloadId;
  }
}
