package faceless.artent.potions.network;

import faceless.artent.potions.ArtentPotions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record CauldronSyncPayload(BlockPos pos, NbtCompound nbt) implements CustomPayload {
  public static final Id<CauldronSyncPayload> PayloadId = new Id<>(Identifier.of(
      ArtentPotions.MODID,
      "packet.cauldron.sync"));
  public static final PacketCodec<RegistryByteBuf, CauldronSyncPayload> CODEC = PacketCodec.tuple(
      BlockPos.PACKET_CODEC,
      CauldronSyncPayload::pos,
      PacketCodecs.NBT_COMPOUND,
      CauldronSyncPayload::nbt,
      CauldronSyncPayload::new);

  @Override
  public Id<? extends CustomPayload> getId() {
    return PayloadId;
  }
}
