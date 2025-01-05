package faceless.artent.potions.network;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public record CauldronSyncPayload(
    BlockPos pos, int fuelAmount, int waterAmount, int portionsLeft, List<BrewingIngredient> ingredients
) implements CustomPayload {

  public static final Id<CauldronSyncPayload> PayloadId = new Id<>(Identifier.of(
      ArtentPotions.MODID,
      "packet.cauldron.sync"));
  public static final PacketCodec<RegistryByteBuf, CauldronSyncPayload> CODEC = new PacketCodec<RegistryByteBuf, CauldronSyncPayload>() {

    @Override
    public void encode(RegistryByteBuf buf, CauldronSyncPayload value) {
      buf.writeBlockPos(value.pos);
      buf.writeInt(value.fuelAmount);
      buf.writeInt(value.waterAmount);
      buf.writeInt(value.portionsLeft);

      var ingredientCount = value.ingredients.size();
      buf.writeInt(ingredientCount);
      for (int i = 0; i < ingredientCount; i++) {
        var ingredient = value.ingredients.get(i);
        var id = Registries.ITEM.getId(ingredient.item());
        buf.writeString(id.toString());
        buf.writeInt(ingredient.meta());
      }
    }

    @Override
    public CauldronSyncPayload decode(RegistryByteBuf buf) {
      var pos = buf.readBlockPos();
      var fuelAmount = buf.readInt();
      var waterAmount = buf.readInt();
      var portionsLeft = buf.readInt();
      var ingredientCount = buf.readInt();

      var ingredients = new ArrayList<BrewingIngredient>(ingredientCount);
      for (int i = 0; i < ingredientCount; i++) {
        var id = buf.readString();
        var meta = buf.readInt();

        var item = Registries.ITEM.get(Identifier.of(id));
        if (item == Items.AIR) {
          System.out.println("Unknown item with identifier '" + id + "' in cauldron. Removing it.");
          continue;
        }
        var ingredient = new BrewingIngredient(item, meta);
        ingredients.add(ingredient);
      }

      return new CauldronSyncPayload(pos, fuelAmount, waterAmount, portionsLeft, ingredients);
    }
  };

  @Override
  public Id<? extends CustomPayload> getId() {
    return PayloadId;
  }
}
