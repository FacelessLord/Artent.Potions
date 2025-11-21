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

import java.util.List;

public record CauldronSyncPayload(
    BlockPos pos,
    int fuelAmount,
    int portionsLeft,
    int crystalsRequired,
    Color color,
    List<BrewingIngredient> ingredients,
    List<AlchemicalPotion> potions
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
      PacketCodecs.registryEntryList(ModRegistries.POTION_INGREDIENT_REGISTRY_KEY),
      payload -> RegistryEntryList.of(BrewingIngredient::getRegistryEntry, payload.ingredients()),
      PacketCodecs.registryEntryList(ModRegistries.POTION_EFFECTS_REGISTRY_KEY),
      payload -> RegistryEntryList.of(AlchemicalPotion::getRegistryEntry, payload.potions()),
      (pos, fuelAmount, portionsLeft, crystalsRequired, color, ingredientsList, potionsList) -> new CauldronSyncPayload(
          pos,
          fuelAmount,
          portionsLeft,
          crystalsRequired,
          color,
          ingredientsList.stream().map(RegistryEntry::value).toList(),
          potionsList.stream().map(RegistryEntry::value).toList()));

  @Override
  public Id<? extends CustomPayload> getId() {
    return PayloadId;
  }
}
