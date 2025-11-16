package faceless.artent.potions.blockEntities;

import faceless.artent.potions.network.ArtentServerHook;
import faceless.artent.potions.network.ShelfSyncPayload;
import faceless.artent.potions.objects.ModBlockEntities;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShelfBlockEntity extends BlockEntity {
  private static final int inventorySize = 4;
  public ItemStack[] items = new ItemStack[inventorySize];

  public ShelfBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.Shelf, pos, state);
    Arrays.fill(items, ItemStack.EMPTY);
  }

  public int getInventorySize() {
    return inventorySize;
  }

  public void exchangeSlot(int slot, ItemStack stack, PlayerEntity player) {
    if (items[slot].isEmpty() && !stack.isEmpty()) {
      items[slot] = stack.copy();
      stack.setCount(0);
      markDirty();
    } else if (!items[slot].isEmpty()) {
      dropSlot(slot).forEach(player::giveOrDropStack);
    }
    ArtentServerHook.packetSyncShelf(player, this);
  }

  public List<ItemStack> dropSlot(int slot) {
    var result = new ArrayList<ItemStack>(0);
    if (slot < 0 || slot >= inventorySize) return result;
    result.add(items[slot]);
    items[slot] = ItemStack.EMPTY;
    markDirty();
    return result;
  }

  public void acceptPayload(ShelfSyncPayload payload) {
    items = payload.items().toArray(ItemStack[]::new);
    markDirty();
  }

  public ShelfSyncPayload createSyncPayload() {
    return new ShelfSyncPayload(this.getPos(), Arrays.stream(this.items).toList());
  }

  @Override
  public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.readNbt(nbt, registryLookup);

    var items = new ArrayList<ItemStack>(4);

    var rackTag = nbt.getCompound("shelf");
    for (int i = 0; i < inventorySize; i++) {
      items.add(ItemStack.fromNbtOrEmpty(registryLookup, rackTag.getCompound("item_" + i)));
    }
    var payload = new ShelfSyncPayload(null, items);
    this.acceptPayload(payload);
  }

  @Override
  protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.writeNbt(nbt, registryLookup);

    var rackTag = new NbtCompound();
    for (int i = 0; i < inventorySize; i++) {
      var item = items[i].toNbtAllowEmpty(registryLookup);
      rackTag.put("item_" + i, item);
    }
    nbt.put("shelf", rackTag);
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