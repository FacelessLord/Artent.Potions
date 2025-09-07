package faceless.artent.potions.network;

import faceless.artent.core.math.Color;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.registry.AlchemicalPotionRegistry;
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
  public static final PacketCodec<RegistryByteBuf, CauldronSyncPayload> CODEC = new PacketCodec<RegistryByteBuf, CauldronSyncPayload>() {

    @Override
    public void encode(RegistryByteBuf buf, CauldronSyncPayload value) {
      buf.writeBlockPos(value.pos);
      buf.writeInt(value.fuelAmount);
      buf.writeInt(value.portionsLeft);
      buf.writeInt(value.crystalsRequired);
      buf.writeInt(value.color.toHex());

      var ingredientCount = value.ingredients.size();
      buf.writeInt(ingredientCount);

      var potionsCount = value.potions.size();
      buf.writeInt(potionsCount);

      for (int i = 0; i < ingredientCount; i++) {
        var ingredient = value.ingredients.get(i);
        var id = Registries.ITEM.getId(ingredient.item());
        buf.writeString(id.toString());
        buf.writeInt(ingredient.meta());
      }

      for (int i = 0; i < potionsCount; i++) {
        var potion = value.potions.get(i);
        buf.writeString(potion.id);
      }
    }

    @Override
    public CauldronSyncPayload decode(RegistryByteBuf buf) {
      var pos = buf.readBlockPos();
      var fuelAmount = buf.readInt();
      var portionsLeft = buf.readInt();
      var crystalsRequired = buf.readInt();
      var colorHex = buf.readInt();
      var color = colorHex == 0 ? Color.Blue : Color.fromInt(colorHex);

      var ingredientCount = buf.readInt();
      var potionsCount = buf.readInt();

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

      var potions = new ArrayList<AlchemicalPotion>(potionsCount);
      for (int i = 0; i < potionsCount; i++) {
        var id = buf.readString();

        var potion = AlchemicalPotionRegistry.getPotion(id);
        if (potion == null) {
          System.out.println("Unknown potion with identifier '" + id + "' in cauldron. Removing it.");
          crystalsRequired = Math.max(0, crystalsRequired - 1);
          continue;
        }
        potions.add(potion);
      }

      return new CauldronSyncPayload(
          pos,
          fuelAmount,
          portionsLeft,
          crystalsRequired,
          color,
          ingredients,
          potions);
    }
  };

  @Override
  public Id<? extends CustomPayload> getId() {
    return PayloadId;
  }
}
