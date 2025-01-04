package faceless.artent.potions.blockEntities;

import faceless.artent.potions.objects.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FermentingBarrelBlockEntity extends BlockEntity {
  public FermentingBarrelBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.FermentingBarrel, pos, state);
  }

  public static final int FERMENTATION_TIME = 20 * 10;
  public int fermentedTime = 0;
  public int portionsLeft = 9;
  public String potionKey = "";

  public void tick(World world, BlockPos pos, BlockState state) {
    if (!potionKey.isEmpty()) {
      if (fermentedTime < FERMENTATION_TIME) {
        fermentedTime++;
      }
    }
  }

  public boolean isFermented() {
    return !potionKey.isEmpty() && fermentedTime >= FERMENTATION_TIME;
  }

  public int takePotionPortions(int amount) {
    if (amount > portionsLeft) {
      var portionsReturned = portionsLeft;
      clearBarrel();
      return portionsReturned;
    } else {
      portionsLeft -= amount;
      if (portionsLeft <= 0) clearBarrel();

      return amount;
    }
  }

  private void clearBarrel() {
    portionsLeft = 9;
    potionKey = "";
    fermentedTime = 0;
    markDirty();
  }

  @Override
  public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.readNbt(nbt, registryLookup);
    fermentedTime = nbt.getInt("fermentingTime");
    potionKey = nbt.getString("potionKey");
    portionsLeft = nbt.getInt("portionsLeft");
  }

  @Override
  public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.writeNbt(nbt, registryLookup);
    nbt.putInt("fermentingTime", fermentedTime);
    nbt.putString("potionKey", potionKey);
    nbt.putInt("portionsLeft", portionsLeft);
  }

  @Override
  public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
    return this.createNbt(registryLookup);
  }

  @Nullable
  @Override
  public Packet<ClientPlayPacketListener> toUpdatePacket() {
    return BlockEntityUpdateS2CPacket.create(this);
  }

  public void setPotionId(String id) {
    potionKey = id;
    fermentedTime = 0;
    markDirty();
  }

  public void setPotionId(String id, int portions) {
    portionsLeft = portions;
    setPotionId(id);
  }
}