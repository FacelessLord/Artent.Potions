package faceless.artent.potions.blockEntities;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.network.ArtentServerHook;
import faceless.artent.potions.network.DryingRackSyncPayload;
import faceless.artent.potions.objects.ModBlockEntities;
import faceless.artent.potions.objects.ModRecipes;
import faceless.artent.potions.recipes.DryingRecipe;
import faceless.artent.potions.recipes.DryingRecipeInput;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DryingRackBlockEntity extends BlockEntity {
  private static final int inventorySize = 4;
  public ItemStack[] items = new ItemStack[inventorySize];
  public int[] timesLeft = new int[inventorySize];
  public ItemStack[] byproducts = new ItemStack[inventorySize];

  public DryingRackBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.DryingRack, pos, state);
    Arrays.fill(items, ItemStack.EMPTY);
    Arrays.fill(timesLeft, 0);
    Arrays.fill(byproducts, ItemStack.EMPTY);
  }

  public void tick(World world, BlockPos pos, BlockState state) {
    for (int i = 0; i < inventorySize; i++) {
      var stack = items[i];
      if (stack == null) continue;
      if (timesLeft[i] == -1) continue;
      timesLeft[i] -= 1;
      if (timesLeft[i] == -1) {
        var recipe = getRecipeByInputStack(stack);
        if (recipe == null) continue;
        items[i] = recipe.result().copy();
        if (byproducts[i] == null) {
          if (Math.random() < recipe.byproductChance()) byproducts[i] = recipe.byproduct().copy();
        } else {
          ArtentPotions.LOGGER.warn("Drying rack has not empty byproduct before recipe is done");
        }
        markDirty();
      }
    }
  }

  public boolean hasRecipe(ItemStack input) {
    return getRecipeByInputStack(input) != null;
  }

  public int getInventorySize() {
    return inventorySize;
  }

  private DryingRecipe getRecipeByInputStack(ItemStack input) {
    var registryOptional = this.world.getRegistryManager().getOptional(ModRecipes.DRYING_RECIPES_REGISTRY_KEY);
    if (registryOptional.isEmpty()) return null;
    var registry = registryOptional.get();
    var recipeInput = new DryingRecipeInput(input);
    return registry
        .getEntrySet()
        .stream()
        .map(Map.Entry::getValue)
        .filter(e -> e.matches(recipeInput, world))
        .findFirst()
        .orElse(null);
  }

  public void exchangeSlot(int slot, ItemStack stack, PlayerEntity player) {
    if (items[slot].isEmpty() && !stack.isEmpty()) {
      items[slot] = stack.splitUnlessCreative(1, player);
      markDirty();
    } else if (!items[slot].isEmpty()) {
      dropSlot(slot).forEach(player::giveOrDropStack);
    }
    ArtentServerHook.packetSyncDryingRack(player, this);
  }

  public List<ItemStack> dropSlot(int slot) {
    var result = new ArrayList<ItemStack>(0);
    if (slot < 0 || slot >= inventorySize)
      return result;
    result.add(items[slot]);
    result.add(byproducts[slot]);
    items[slot] = ItemStack.EMPTY;
    byproducts[slot] = ItemStack.EMPTY;
    markDirty();
    return result;
  }

  public void acceptPayload(DryingRackSyncPayload payload) {
    items = payload.items().toArray(ItemStack[]::new);
    timesLeft = payload.timesLeft().stream().mapToInt(i -> i).toArray();
    byproducts = payload.byproducts().toArray(ItemStack[]::new);
    markDirty();
  }

  @Override
  public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.readNbt(nbt, registryLookup);

    var items = new ArrayList<ItemStack>(4);
    var times = new ArrayList<Integer>(4);
    var byproducts = new ArrayList<ItemStack>(4);

    var rackTag = nbt.getCompound("drying_rack");
    for (int i = 0; i < inventorySize; i++) {
      items.add(ItemStack.fromNbtOrEmpty(registryLookup, rackTag.getCompound("item_" + i)));
      times.add(rackTag.getInt("time_" + i));
      byproducts.add(ItemStack.fromNbtOrEmpty(registryLookup, rackTag.getCompound("byproduct_" + i)));
    }
    var payload = new DryingRackSyncPayload(null, items, times, byproducts);
    this.acceptPayload(payload);
  }

  @Override
  protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.writeNbt(nbt, registryLookup);

    var rackTag = new NbtCompound();
    for (int i = 0; i < inventorySize; i++) {
      var item = items[i].toNbtAllowEmpty(registryLookup);
      rackTag.put("item_" + i, item);
      rackTag.putInt("time_" + i, timesLeft[i]);
      var byproduct = byproducts[i].toNbtAllowEmpty(registryLookup);
      rackTag.put("byproduct_" + i, byproduct);
    }
    nbt.put("drying_rack", rackTag);
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