package faceless.artent.potions.blockEntities;

import faceless.artent.core.api.ChatUtils;
import faceless.artent.potions.api.IPotionContainerBlock;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.network.ArtentServerHook;
import faceless.artent.potions.network.BarrelSyncPayload;
import faceless.artent.potions.objects.ModBlockEntities;
import faceless.artent.potions.registry.AlchemicalPotionRegistry;
import faceless.artent.potions.registry.FermentationRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
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
import net.minecraft.server.world.ServerWorld;
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

  public int fermentedTime = 0;
  public int potionAmount = 0;
  public boolean fermentationStarted = false;
  public List<AlchemicalPotion> potions = new ArrayList<>();

  public void tick(World world, BlockPos pos, BlockState state) {
    if (!potions.isEmpty() && !world.isClient) {
      if (fermentedTime > 0) {
        fermentedTime--;
        if (fermentedTime <= 0) {
          this.potions = this.potions.stream().map(potion -> {
            var fermentedPotion = FermentationRegistry.getFermentationResult(potion);
            if (fermentedPotion == null)
              return potion;
            return fermentedPotion;
          }).toList();
        }

        syncAround((ServerWorld) world, pos);
      }
    }
  }

  private void syncAround(ServerWorld world, BlockPos pos) {
    for (var player : PlayerLookup.tracking(world, pos)) {
      ArtentServerHook.packetSyncBarrel(player, this);
    }
  }

  public boolean isFermented() {
    return !potions.isEmpty() && fermentedTime <= 0 && fermentationStarted;
  }

  public void startFermentation() {
    if (world.isClient)
      return;
    fermentationStarted = true;
    var tickRate = this.world.getTickManager().getTickRate();
    this.fermentedTime = this.potions.stream().map(FermentationRegistry::getFermentationRecipe)
                                     .map(recipe -> recipe == null ? 0 : (int) (recipe.seconds() * tickRate))
                                     .reduce(0, Integer::sum);
    syncAround((ServerWorld) world, pos);
  }

  @Override
  public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.readNbt(nbt, registryLookup);
    fermentedTime = nbt.getInt("fermentingTime");
    potionAmount = nbt.getInt("portionsLeft");
    fermentationStarted = nbt.getBoolean("fermentationStarted");

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
    nbt.putBoolean("fermentationStarted", fermentationStarted);

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
    return fermentationStarted ? this.potionAmount : 9;
  }

  @Override
  public int getPotionAmount() {
    return potionAmount;
  }

  @Override
  public void setPotionAmount(int amount) {
    if (world.isClient)
      return;
    potionAmount = amount;
    if (amount == 0) {
      clear();
    }
    syncAround((ServerWorld) world, pos);
  }

  @Override
  public List<AlchemicalPotion> getPotions() {
    return potions;
  }

  @Override
  public void clear() {
    if (world.isClient)
      return;
    potionAmount = 0;
    potions = new ArrayList<>();
    fermentedTime = 0;
    fermentationStarted = false;
    markDirty();
    syncAround((ServerWorld) world, pos);
  }

  @Override
  public boolean canExtractPotion() {
    return true;
  }

  @Override
  public void setPotions(List<AlchemicalPotion> potions) {
    if (world.isClient)
      return;
    this.potions = potions;
    syncAround((ServerWorld) world, pos);
  }

  @Override
  public boolean canContainPotion(List<AlchemicalPotion> potion) {
    return potion.stream().allMatch(id -> FermentationRegistry.getFermentationResult(id) != null);
  }

  @Override
  public void onCanNotContainPotion(PlayerEntity player, List<AlchemicalPotion> potion) {
    ChatUtils.sendMessageToPlayer(this.world, player, Text.translatable("text.artent_potions.potion.infermentable"));
  }

  public BarrelSyncPayload createSyncPayload() {
    return new BarrelSyncPayload(this.pos, potions, potionAmount, fermentedTime, fermentationStarted);
  }

  public void acceptPayload(BarrelSyncPayload payload) {
    potions = payload.potions();
    potionAmount = payload.potionAmount();
    fermentedTime = payload.fermentedTime();
    fermentationStarted = payload.fermentationStarted();
    markDirty();
  }
}