package faceless.artent.potions.blockEntities;

import faceless.artent.potions.api.IPotionContainerBlock;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.objects.ModBlockEntities;
import faceless.artent.potions.registry.AlchemicalPotionRegistry;
import faceless.artent.potions.registry.FermentationRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FermentingBarrelBlockEntity extends BlockEntity implements IPotionContainerBlock {
  public FermentingBarrelBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.FermentingBarrel, pos, state);
  }

  public static final int FERMENTATION_TIME = 20 * 10;
  public int fermentedTime = 0;
  public int potionAmount = 9;
  public List<AlchemicalPotion> potions = new ArrayList<>();

  public void tick(World world, BlockPos pos, BlockState state) {
    if (!potions.isEmpty()) {
      if (fermentedTime < FERMENTATION_TIME) {
        fermentedTime++;
      }
    }
  }

  public boolean isFermented() {
    return !potions.isEmpty() && fermentedTime >= FERMENTATION_TIME;
  }

  @Override
  public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.readNbt(nbt, registryLookup);
    fermentedTime = nbt.getInt("fermentingTime");
    potionAmount = nbt.getInt("portionsLeft");

    var potionsTag = nbt.getList("potions", NbtCompound.LIST_TYPE);
    potions = new ArrayList<>(potionsTag.size());
    for (net.minecraft.nbt.NbtElement nbtElement : potionsTag) {
      var potionTag = (NbtString) nbtElement;
      var id = potionTag.asString();
      var potion = AlchemicalPotionRegistry.getPotion(id);
      potions.add(potion);
    }
  }

  @Override
  public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.writeNbt(nbt, registryLookup);
    nbt.putInt("fermentingTime", fermentedTime);
    nbt.putInt("portionsLeft", potionAmount);

    var potionsTag = new NbtList();
    for (int i = 0; i < potions.size(); i++) {
      var potion = potions.get(i);
      potionsTag.add(i, NbtString.of(potion.id));
    }
    nbt.put("potions", potionsTag);
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

  @Override
  public int getMaxPotionAmount() {
    return 9;
  }

  @Override
  public int getPotionAmount() {
    return potionAmount;
  }

  @Override
  public void setPotionAmount(int amount) {
    potionAmount = amount;
    if (amount == 0) {
      clear();
    }
  }

  @Override
  public List<AlchemicalPotion> getPotions() {
    return potions;
  }

  @Override
  public void clear() {
    potionAmount = 9;
    potions = new ArrayList<>();
    fermentedTime = 0;
    markDirty();
  }

  @Override
  public boolean canExtractPotion() {
    return true;
  }

  @Override
  public void setPotions(List<AlchemicalPotion> potions) {
    this.potions = potions;
  }

  @Override
  public boolean canContainPotion(List<AlchemicalPotion> potion) {
    return potion.stream().allMatch(id -> FermentationRegistry.getFermentedPotion(id) != null);
  }

  @Override
  public void onCanNotContainPotion(PlayerEntity player, List<AlchemicalPotion> potion) {
    player.sendMessage(Text.translatable("text.artent_potions.potion.infermentable"), false);
  }
}