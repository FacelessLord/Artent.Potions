package faceless.artent.potions.blockEntities;

import faceless.artent.core.math.Color;
import faceless.artent.potions.BrewingAutomata;
import faceless.artent.potions.block.BrewingCauldron;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.brewingApi.IBrewable;
import faceless.artent.potions.network.ArtentServerHook;
import faceless.artent.potions.network.CauldronSyncPayload;
import faceless.artent.potions.objects.*;
import faceless.artent.potions.registry.AlchemicalPotionRegistry;
import faceless.artent.potions.registry.BrewingRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DryingRackBlockEntity extends BlockEntity {
  private final int inventorySize = 4;
  public ItemStack[] items = new ItemStack[inventorySize];
  public int[] timesLeft = new int[inventorySize];

  public DryingRackBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.DryingRack, pos, state);
  }

  public void tick(World world, BlockPos pos, BlockState state) {
    var registry =this.world.getRegistryManager().getOptional(ModRecipes.DRYING_RECIPES_REGISTRY_KEY);
    if(registry.isEmpty())
      System.out.println("No registry");
    else {
      var items = registry.get().getKeys();
      System.out.println("Got "+items.size()+" items");
    }
    for (int i = 0; i < inventorySize; i++) {
      var stack = items[i];
      if (stack == null) continue;
      if (timesLeft[i] == -1)
        continue;
      timesLeft[i] -= 1;
      if (timesLeft[i] == -1) {
//        serverworld.getRecipeManager().getFirstMatch(RecipeType.SMITHING, smithingRecipeInput, serverWorld);
      }
    }
  }

  public void acceptPayload(CauldronSyncPayload payload) {
//    this.fuelAmount = payload.fuelAmount();
//    this.potionAmount = payload.portionsLeft();
//    this.crystalsRequired = payload.crystalsRequired();
//    this.color = payload.color();
//    this.ingredients = payload.ingredients();
//    this.potions = payload.potions();
    markDirty();
  }

  @Override
  public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.readNbt(nbt, registryLookup);
    var fuelAmount = nbt.getInt("fuelAmount");
    var portionsLeft = nbt.getInt("portionsLeft");
    var crystalsRequired = nbt.getInt("crystalsRequired");
    var colorHex = nbt.getInt("color");
    var color = colorHex == 0 ? Color.Blue : Color.fromInt(colorHex);

    var ingredientsCount = nbt.getInt("ingredientCount");
    var ingredientsTag = nbt.getCompound("ingredients");
    var potionCount = nbt.getInt("potionCount");
    var potionsTag = nbt.getCompound("potions");

    var ingredients = new ArrayList<BrewingIngredient>(ingredientsCount);
    for (int i = 0; i < ingredientsCount; i++) {
      var ingredientTag = ingredientsTag.getCompound("" + i);
      var id = ingredientTag.getString("id");
      var item = Registries.ITEM.get(Identifier.of(id));
      if (item == Items.AIR) {
        System.out.println("Unknown item with identifier '" + id + "' in cauldron. Removing it.");
        continue;
      }
      var meta = ingredientTag.getInt("meta");
      var ingredient = new BrewingIngredient(item, meta);
      ingredients.add(ingredient);
    }

    var potions = new ArrayList<AlchemicalPotion>(potionCount);
    for (int i = 0; i < potionCount; i++) {
      var id = potionsTag.getString(i + "");
      var potion = AlchemicalPotionRegistry.getPotion(id);
      if (potion == null) {
        System.out.println("Unknown potion with identifier '" + id + "' in cauldron. Removing it.");
        crystalsRequired = Math.max(0, crystalsRequired - 1);
        continue;
      }
      potions.add(potion);
    }
    var payload = new CauldronSyncPayload(
        null,
        fuelAmount,
        portionsLeft,
        crystalsRequired,
        color,
        ingredients,
        potions);
    this.acceptPayload(payload);
  }

  @Override
  protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.writeNbt(nbt, registryLookup);
//    nbt.putInt("fuelAmount", fuelAmount);
//    nbt.putInt("portionsLeft", potionAmount);
//    nbt.putInt("crystalsRequired", crystalsRequired);
//    nbt.putInt("color", color.toHex());
//    nbt.putInt("ingredientCount", ingredients.size());
//    nbt.putInt("potionCount", potions.size());
//
//    var ingredientsTag = new NbtCompound();
//    for (int i = 0; i < ingredients.size(); i++) {
//      var ingredient = ingredients.get(i);
//      var ingredientTag = new NbtCompound();
//      var id = Registries.ITEM.getId(ingredient.item());
//      ingredientTag.putString("id", id.toString());
//      ingredientTag.putInt("meta", ingredient.meta());
//      ingredientsTag.put(i + "", ingredientTag);
//    }
//    nbt.put("ingredients", ingredientsTag);
//
//    var potionsTag = new NbtCompound();
//    for (int i = 0; i < potions.size(); i++) {
//      var potion = potions.get(i);
//      potionsTag.putString(i + "", potion.id);
//    }
//    nbt.put("potions", potionsTag);
  }

  @Override
  public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
    return this.createNbt(registries);
  }

  @Nullable
  public Packet<ClientPlayPacketListener> toUpdatePacket() {
    return BlockEntityUpdateS2CPacket.create(this, BlockEntity::createNbt);
  }
}