package faceless.artent.potions.blockEntities;

import faceless.artent.core.math.Color;
import faceless.artent.potions.BrewingAutomata;
import faceless.artent.potions.block.BrewingCauldron;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.brewingApi.IBrewable;
import faceless.artent.potions.network.ArtentServerHook;
import faceless.artent.potions.objects.BrewingRecipes;
import faceless.artent.potions.objects.ModBlockEntities;
import faceless.artent.potions.registry.BrewingRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class BrewingCauldronBlockEntity extends BlockEntity {
  public ArrayList<BrewingIngredient> ingredients = new ArrayList<>();
  public int fuelAmount = 0;
  public int waterAmount = 0;
  public int portionsLeft = 9;
  public Color color = Color.Blue;

  public BrewingCauldronBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.BrewingCauldron, pos, state);
  }

  public static final int BrewingCooldown = 30;

  public void tick(World world, BlockPos pos, BlockState state) {
    if (fuelAmount <= 0 && (state.get(BrewingCauldron.HAS_COAL) || state.get(BrewingCauldron.IS_BURNING))) {
      state = state.with(BrewingCauldron.HAS_COAL, false).with(BrewingCauldron.IS_BURNING, false);
      world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
    }

    if (state.get(BrewingCauldron.IS_BURNING)) {
      if (fuelAmount <= 0) {
        state = state.with(BrewingCauldron.HAS_COAL, false).with(BrewingCauldron.IS_BURNING, false);
        world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
        return;
      }
      if (waterAmount > 0) {
        var one16th = 1d / 16d;
        var box = new Box(
            pos.getX() + 2 * one16th,
            pos.getY() + 2 * one16th,
            pos.getZ() + 2 * one16th,
            pos.getX() + 1 - 2 * one16th,
            pos.getY() + 12 * one16th,
            pos.getZ() + 1 - 2 * one16th);
        var items = world.getEntitiesByClass(ItemEntity.class, box, ie -> BrewingRecipes.IsIngredient(ie.getStack()));
        if (!items.isEmpty()) {
          var first = items.getFirst();
          var brewable = (IBrewable) first;
          if ((brewable.getBrewingTime() > BrewingCooldown)) {
            brewItem(first);
          } else brewable.setBrewingTime(brewable.getBrewingTime() + 1);
        }
        waterAmount--;
        fuelAmount--;
      }
      if (world.getTime() % 20 == 0) {
        updateBlock();
      }
    }
    markDirty();
  }

  private void brewItem(ItemEntity item) {
    if (world == null || world.isClient) return;

    var brewable = (IBrewable) item;
    final var stack = item.getStack();
    var ingredient = BrewingRecipes.AsIngredient(stack);
    if (stack.getCount() > 1) {
      item.resetPickupDelay();
      brewable.setBrewingTime(0);
      stack.setCount(stack.getCount() - 1);
    } else {
      item.setDespawnImmediately();
    }
    ingredients.add(ingredient);
    if (ingredients.size() == 1) color = BrewingRegistry.Ingredients.get(ingredient);
    else color = color.add(BrewingRegistry.Ingredients.get(ingredient));

    updateBlock();
  }

  @Override
  public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.readNbt(nbt, registryLookup);
    fuelAmount = nbt.getInt("fuelAmount");
    waterAmount = nbt.getInt("waterAmount");
    portionsLeft = nbt.getInt("portionsLeft");
    var ingredientsTag = nbt.getList("ingredients", NbtCompound.COMPOUND_TYPE);
    ingredients = new ArrayList<>(ingredientsTag.size());
    var colors = new ArrayList<Color>();
    color.add(Color.Blue);
    for (net.minecraft.nbt.NbtElement nbtElement : ingredientsTag) {
      var ingredientTag = (NbtCompound) nbtElement;
      var id = ingredientTag.getString("id");
      var item = Registries.ITEM.get(Identifier.of(id));
      if (item == Items.AIR) {
        System.out.println("Unknown item with identifier '" + id + "' in cauldron. Removing it.");
        continue;
      }
      var meta = ingredientTag.getInt("meta");
      var ingredient = new BrewingIngredient(item, meta);
      ingredients.add(ingredient);
      colors.add(BrewingRegistry.Ingredients.get(ingredient));
    }
    color = colors.stream().reduce(Color::add).orElse(Color.Blue);
  }

  @Override
  protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.writeNbt(nbt, registryLookup);
    nbt.putInt("fuelAmount", fuelAmount);
    nbt.putInt("waterAmount", waterAmount);
    nbt.putInt("portionsLeft", portionsLeft);
    var ingredientsTag = new NbtList();
    for (int i = 0; i < ingredients.size(); i++) {
      var ingredient = ingredients.get(i);
      var ingredientTag = new NbtCompound();
      var id = Registries.ITEM.getId(ingredient.item());
      ingredientTag.putString("id", id.toString());
      ingredientTag.putInt("meta", ingredient.meta());
      ingredientsTag.add(i, ingredientTag);
    }
    nbt.put("ingredients", ingredientsTag);
  }

  public AddFuelResultType addFuel(ItemStack stack, boolean consumeStack) {
    if (world == null) return AddFuelResultType.Fail;

    var time = world.getFuelRegistry().getFuelTicks(stack);

    if ((!world.getBlockState(pos).get(BrewingCauldron.HAS_COAL) || fuelAmount <= 0) && time > 0) {
      var state = world.getBlockState(pos).with(BrewingCauldron.HAS_COAL, true).with(BrewingCauldron.IS_BURNING, false);
      world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
    }
    if (time == 0) return AddFuelResultType.Fail;
    this.fuelAmount += time * (consumeStack ? stack.getCount() : 1);
    updateBlock();
    return consumeStack || stack.getCount() == 1 ? AddFuelResultType.ConsumeStack : AddFuelResultType.ConsumeItem;
  }

  public BrewingAutomata.State getBrewingState() {
    return BrewingRecipes.RecipeAutomata.getStateFromIngredients(ingredients);
  }

  @Override
  public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
    return this.createNbt(registries);
  }

  @Nullable
  public Packet<ClientPlayPacketListener> toUpdatePacket() {
    return BlockEntityUpdateS2CPacket.create(this, BlockEntity::createNbt);
  }

  public void consumePortions(int amount) {
    if (portionsLeft >= amount) portionsLeft -= amount;
    if (portionsLeft != 0) {
      updateBlock();
    } else {
      clearCauldron();
    }
  }

  public void clearCauldron() {
    ingredients.clear();
    color = Color.Blue;
    waterAmount = 0;
    portionsLeft = 9;
    updateBlock();
  }

  public void updateBlock() {
    if (world == null) return;

    var state = world.getBlockState(pos);
    world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);

    if (world.isClient) return;

//    for (var player : PlayerLookup.tracking((ServerWorld) world, pos)) {
//      ArtentServerHook.packetSyncCauldron(player, this);
//    }
  }

  public static enum AddFuelResultType {
    Fail, ConsumeStack, ConsumeItem,
  }
}