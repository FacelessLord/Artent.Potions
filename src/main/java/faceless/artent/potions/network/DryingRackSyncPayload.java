package faceless.artent.potions.network;

import faceless.artent.core.math.Color;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.registry.AlchemicalPotionRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public record DryingRackSyncPayload(
    BlockPos pos, List<ItemStack> items, List<Integer> timesLeft, List<ItemStack> byproducts
) implements CustomPayload {

  public static final Id<DryingRackSyncPayload> PayloadId = new Id<>(Identifier.of(
      ArtentPotions.MODID,
      "packet.drying_rack.sync"));
  public static final PacketCodec<RegistryByteBuf, DryingRackSyncPayload> CODEC = PacketCodec.tuple(
      BlockPos.PACKET_CODEC,
      (DryingRackSyncPayload payload) -> payload.pos,
      PacketCodecs.collection(ArrayList::new, ItemStack.OPTIONAL_PACKET_CODEC),
      (DryingRackSyncPayload payload) -> payload.items,
      PacketCodecs.collection(ArrayList::new, PacketCodecs.INTEGER),
      (DryingRackSyncPayload payload) -> payload.timesLeft,
      PacketCodecs.collection(ArrayList::new, ItemStack.OPTIONAL_PACKET_CODEC),
      (DryingRackSyncPayload payload) -> payload.byproducts,
      DryingRackSyncPayload::new);

  @Override
  public Id<? extends CustomPayload> getId() {
    return PayloadId;
  }
}
