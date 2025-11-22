package faceless.artent.potions.network;

import faceless.artent.core.math.Color;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.objects.ModRegistries;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public record CauldronSyncPayload(
    BlockPos pos,
    int fuelAmount,
    int portionsLeft,
    int crystalsRequired,
    Color color,
    List<Identifier> ingredients,
    List<Identifier> potions
) implements CustomPayload {

  public static final Id<CauldronSyncPayload> PayloadId = new Id<>(Identifier.of(
      ArtentPotions.MODID,
      "packet.cauldron.sync"));
  public static final PacketCodec<RegistryByteBuf, CauldronSyncPayload> CODEC = PacketCodec.tuple(
      BlockPos.PACKET_CODEC,
      CauldronSyncPayload::pos,
      PacketCodecs.INTEGER,
      CauldronSyncPayload::fuelAmount,
      PacketCodecs.INTEGER,
      CauldronSyncPayload::portionsLeft,
      PacketCodecs.INTEGER,
      CauldronSyncPayload::crystalsRequired,
      Color.PACKET_CODEC,
      CauldronSyncPayload::color,
      PacketCodecs.collection(ArrayList::new, Identifier.PACKET_CODEC),
      CauldronSyncPayload::ingredients,
      PacketCodecs.collection(ArrayList::new, Identifier.PACKET_CODEC),
      CauldronSyncPayload::potions,
      CauldronSyncPayload::new);

  @Override
  public Id<? extends CustomPayload> getId() {
    return PayloadId;
  }
}
