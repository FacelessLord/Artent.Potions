package faceless.artent.potions.network;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.registry.AlchemicalPotionRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public record BarrelSyncPayload(
    BlockPos pos, List<AlchemicalPotion> potions, int potionAmount, int fermentedTime, boolean fermentationStarted
) implements CustomPayload {
  public static final Id<BarrelSyncPayload> PayloadId = new Id<>(Identifier.of(
      ArtentPotions.MODID,
      "packet.barrel.sync"));
  public static final PacketCodec<RegistryByteBuf, BarrelSyncPayload> CODEC = new PacketCodec<>() {

    @Override
    public void encode(RegistryByteBuf buf, BarrelSyncPayload value) {
      buf.writeBlockPos(value.pos);
      buf.writeInt(value.potionAmount);
      buf.writeInt(value.fermentedTime);
      buf.writeBoolean(value.fermentationStarted);

      var potionsCount = value.potions.size();
      buf.writeInt(potionsCount);

      for (int i = 0; i < potionsCount; i++) {
        var potion = value.potions.get(i);
        buf.writeString(potion.id);
      }
    }

    @Override
    public BarrelSyncPayload decode(RegistryByteBuf buf) {
      var pos = buf.readBlockPos();
      var potionAmount = buf.readInt();
      var fermentedTime = buf.readInt();
      var fermentationStarted = buf.readBoolean();
      var potionsCount = buf.readInt();

      var potions = new ArrayList<AlchemicalPotion>(potionsCount);
      for (int i = 0; i < potionsCount; i++) {
        var id = buf.readString();

        var potion = AlchemicalPotionRegistry.getPotion(id);
        if (potion == null) {
          System.out.println("Unknown potion with identifier '" + id + "' in cauldron. Removing it.");
          continue;
        }
        potions.add(potion);
      }

      return new BarrelSyncPayload(pos, potions, potionAmount, fermentedTime, fermentationStarted);
    }
  };

  @Override
  public Id<? extends CustomPayload> getId() {
    return PayloadId;
  }
}
