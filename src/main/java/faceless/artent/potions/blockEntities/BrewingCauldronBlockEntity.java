package faceless.artent.potions.blockEntities;

import faceless.artent.core.math.Color;
import faceless.artent.potions.BrewingAutomata;
import faceless.artent.potions.api.IPotionContainerBlock;
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

public class BrewingCauldronBlockEntity extends BlockEntity implements IPotionContainerBlock {
  public static final int BrewingCooldown = 30;
  public static final int CopperBrewingCooldown = 50;

  public List<BrewingIngredient> ingredients = new ArrayList<>();
  public int fuelAmount = 0;
  public int potionAmount = 0;
  public Color color = Color.Blue;
  public List<AlchemicalPotion> potions = new ArrayList<>();
  public int crystalsRequired = 0;

  public BrewingCauldronBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.BrewingCauldron, pos, state);
  }

  public void tick(World world, BlockPos pos, BlockState state) {
    updateBurningState(world, pos, state);
    if (!state.get(BrewingCauldron.IS_BURNING)) return;

    brewIngredients(world, pos, state);

    if (!(world instanceof ServerWorld serverWorld)) return;

    if (canExtractPotion()) spawnBubbles(pos, serverWorld);

    if (world.getTime() % 20 == 0) {
      updateBlock();
    }
  }

  private void brewIngredients(World world, BlockPos pos, BlockState state) {
    var waterBox = getWaterBox(pos);
    var brewingState = getBrewingState();
    var items = world.getEntitiesByClass(
        ItemEntity.class,
        waterBox,
        ie -> (brewingState.isFinishing() && ie.getStack().getItem() == ModItems.IceCrystalShard)
              || BrewingRecipes.IsIngredient(ie.getStack()));
    if (items.isEmpty()) return;

    var brewingCooldown = state.getBlock() == ModBlocks.BrewingCauldronCopper ? CopperBrewingCooldown : BrewingCooldown;
    var first = items.getFirst();
    var brewable = (IBrewable) first;
    if ((brewable.getBrewingTime() > brewingCooldown)) {
      brewItem(first);
    } else brewable.setBrewingTime(brewable.getBrewingTime() + 1);
  }

  private void spawnBubbles(BlockPos pos, ServerWorld serverWorld) {
    var random = new Random();

    serverWorld.spawnParticles(
        ModParticles.SPLASH,
        (double) pos.getX() + 0.2d + random.nextDouble() * 0.6d,
        pos.getY() + potionAmount / (float) getMaxPotionAmount() * 15f / 32f + 3 / 8d,
        (double) pos.getZ() + 0.2d + random.nextDouble() * 0.6d,
        1,
        0.0,
        0.0,
        0.0,
        0.001);
    serverWorld.spawnParticles(
        ModParticles.BUBBLE,
        (double) pos.getX() + 0.2d + random.nextDouble() * 0.6d,
        pos.getY() + potionAmount / (float) getMaxPotionAmount() * 15f / 32f + 3 / 8d,
        (double) pos.getZ() + 0.2d + random.nextDouble() * 0.6d,
        1,
        0.0,
        0.0,
        0.0,
        0.001);
  }

  private static @NotNull Box getWaterBox(BlockPos pos) {
    var one16th = 1d / 16d;
    return new Box(
        pos.getX() + 2 * one16th,
        pos.getY() + 2 * one16th,
        pos.getZ() + 2 * one16th,
        pos.getX() + 1 - 2 * one16th,
        pos.getY() + 12 * one16th,
        pos.getZ() + 1 - 2 * one16th);
  }

  private void updateBurningState(World world, BlockPos pos, BlockState state) {
    if (fuelAmount <= 0 && (state.get(BrewingCauldron.HAS_COAL) || state.get(BrewingCauldron.IS_BURNING))) {
      state = state.with(BrewingCauldron.HAS_COAL, false).with(BrewingCauldron.IS_BURNING, false);
      world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
    }
    if (state.get(BrewingCauldron.IS_BURNING)) {
      fuelAmount--;
      markDirty();
    }
  }

  private void brewItem(ItemEntity item) {
    if (world == null || world.isClient) return;

    final var brewable = (IBrewable) item;
    final var stack = item.getStack();
    var ingredient = BrewingRecipes.AsIngredient(stack);
    var isCrystal = stack.getItem() == ModItems.IceCrystalShard;

    handleLeveledPotions(ingredient);

    if (stack.getCount() > 1) {
      item.resetPickupDelay();
      brewable.setBrewingTime(0);
      stack.setCount(stack.getCount() - 1);
    } else {
      item.setDespawnImmediately();
    }

    if (isCrystal) {
      crystalsRequired--;
    } else if (ingredient != null) {
      ingredients.add(ingredient);
      if (ingredients.size() == 1) color = BrewingRegistry.Ingredients.get(ingredient);
      else color = color.add(BrewingRegistry.Ingredients.get(ingredient));
    }

    updateBrewingState();
    updateBlock();
  }

  private void handleLeveledPotions(BrewingIngredient ingredient) {
    if (!ingredients.isEmpty() || potions.isEmpty()) return;

    var lastPotion = potions.getLast();
    var automataState = BrewingRecipes.RecipeAutomata.LastIngredients.get(lastPotion.id);
    if (automataState == null) return;

    var edges = BrewingRecipes.RecipeAutomata.Edges.get(automataState);
    if (edges == null) return;

    var possibleEdges = edges.stream().filter(e -> e.Character() == ingredient).toList();
    if (possibleEdges.isEmpty()) return;

    var newPotion = possibleEdges.getFirst();
    if (newPotion.Target().brewedPotion() != null) {
      potions.removeLast();
      potions.add(newPotion.Target().brewedPotion());
    }
    markDirty();
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
    markDirty();
    updateBlock();
    return consumeStack || stack.getCount() == 1 ? AddFuelResultType.ConsumeStack : AddFuelResultType.ConsumeItem;
  }

  public BrewingAutomata.State getBrewingState() {
    return BrewingRecipes.RecipeAutomata.getStateFromIngredients(ingredients);
  }

  private void updateBrewingState() {
    var brewingState = getBrewingState();
    // Breaks leveled potion brewing
    if (brewingState.isFinishing()) {
      potions.add(brewingState.brewedPotion());
      ingredients.clear();
      crystalsRequired = potions.size();
      markDirty();
    }
  }

  public void acceptPayload(CauldronSyncPayload payload) {
    this.fuelAmount = payload.fuelAmount();
    this.potionAmount = payload.portionsLeft();
    this.crystalsRequired = payload.crystalsRequired();
    this.color = payload.color();
    this.ingredients = payload.ingredients();
    this.potions = payload.potions();
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
    nbt.putInt("fuelAmount", fuelAmount);
    nbt.putInt("portionsLeft", potionAmount);
    nbt.putInt("crystalsRequired", crystalsRequired);
    nbt.putInt("color", color.toHex());
    nbt.putInt("ingredientCount", ingredients.size());
    nbt.putInt("potionCount", potions.size());

    var ingredientsTag = new NbtCompound();
    for (int i = 0; i < ingredients.size(); i++) {
      var ingredient = ingredients.get(i);
      var ingredientTag = new NbtCompound();
      var id = Registries.ITEM.getId(ingredient.item());
      ingredientTag.putString("id", id.toString());
      ingredientTag.putInt("meta", ingredient.meta());
      ingredientsTag.put(i + "", ingredientTag);
    }
    nbt.put("ingredients", ingredientsTag);

    var potionsTag = new NbtCompound();
    for (int i = 0; i < potions.size(); i++) {
      var potion = potions.get(i);
      potionsTag.putString(i + "", potion.id);
    }
    nbt.put("potions", potionsTag);
  }

  @Override
  public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
    return this.createNbt(registries);
  }

  @Nullable
  public Packet<ClientPlayPacketListener> toUpdatePacket() {
    return BlockEntityUpdateS2CPacket.create(this, BlockEntity::createNbt);
  }

  public void updateBlock() {
    if (world == null) return;

    var state = world.getBlockState(pos);
    world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);

    if (world.isClient) return;

    for (var player : PlayerLookup.tracking((ServerWorld) world, pos)) {
      ArtentServerHook.packetSyncCauldron(player, this);
    }
    markDirty();
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
    markDirty();
  }

  @Override
  public List<AlchemicalPotion> getPotions() {
    return potions;
  }

  @Override
  public void clear() {
    ingredients.clear();
    color = Color.Blue;
    potions.clear();
    potionAmount = 0;
    crystalsRequired = 0;
    markDirty();
    updateBlock();
  }

  @Override
  public boolean canExtractPotion() {
    return !potions.isEmpty();
  }

  @Override
  public void setPotions(List<AlchemicalPotion> potions) {
    this.potions = potions;
    markDirty();
  }

  public enum AddFuelResultType {
    Fail, ConsumeStack, ConsumeItem,
  }
}