package faceless.artent.potions.block;

import com.mojang.serialization.MapCodec;
import faceless.artent.core.item.INamed;
import faceless.artent.potions.blockEntities.BrewingCauldronBlockEntity;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.objects.ModBlockEntities;
import faceless.artent.potions.objects.ModItems;
import faceless.artent.potions.registry.AlchemicalPotionRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class BrewingCauldron extends BlockWithEntity implements INamed {
  public static final MapCodec<BrewingCauldron> CODEC = BrewingCauldron.createCodec(BrewingCauldron::new);
  public static final BooleanProperty HAS_COAL = BooleanProperty.of("has_coal");
  public static final BooleanProperty IS_BURNING = BooleanProperty.of("is_burning");

  public BrewingCauldron(Settings settings) {
    super(settings);
    this.setDefaultState(this.stateManager.getDefaultState().with(HAS_COAL, false).with(IS_BURNING, false));
  }

  @Override
  public String getId() {
    return "cauldron";
  }

  @Override
  protected MapCodec<? extends BlockWithEntity> getCodec() {
    return CODEC;
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(HAS_COAL, IS_BURNING);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    // With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
    return BlockRenderType.MODEL;
  }

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
      World world,
      BlockState state,
      BlockEntityType<T> type) {
    return validateTicker(
        type,
        ModBlockEntities.BrewingCauldron,
        (world1, pos, state1, be) -> be.tick(world1, pos, state1));
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new BrewingCauldronBlockEntity(pos, state);
  }

  @Override
  protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    var be = world.getBlockEntity(pos);
    if (!(be instanceof BrewingCauldronBlockEntity cauldron)) {
      return ActionResult.SUCCESS;
    }

    var stack = player.getEquippedStack(player.getActiveHand() == Hand.MAIN_HAND
                                            ? EquipmentSlot.MAINHAND
                                            : EquipmentSlot.OFFHAND);

    var brewingState = cauldron.getBrewingState();
    if (stack.getItem() == ModItems.EmptyPhial) {
      if (brewingState.IsFinishing()) {
        var potion = brewingState.BrewedPotion();
        var potionStack = new ItemStack(ModItems.PotionPhial);
        AlchemicalPotionUtil.setPotion(potionStack, potion);
        player.giveItemStack(potionStack);
        stack.decrement(1);
        cauldron.consumePortions(3);
        if (stack.getCount() == 1) return ActionResult.CONSUME;
      }
    } else if (stack.getItem() == ModItems.EmptyPhialExplosive) {
      if (brewingState.IsFinishing()) {
        var potion = brewingState.BrewedPotion();
        var potionStack = new ItemStack(ModItems.PotionPhialExplosive);
        AlchemicalPotionUtil.setPotion(potionStack, potion);

        player.giveItemStack(potionStack);
        stack.decrement(1);
        cauldron.consumePortions(3);
        if (stack.getCount() == 1) return ActionResult.CONSUME;
      }
    } else if (stack.getItem() == ModItems.GoldenBucket) {
      if (brewingState.IsFinishing()) {
        if (AlchemicalPotionRegistry.fermentedPotionIsRegistered(brewingState.BrewedPotion().id)) {
          var filledBucket = new ItemStack(ModItems.GoldenBucketFilled);
          AlchemicalPotionUtil.setPotion(filledBucket, brewingState.BrewedPotion());
          player.setStackInHand(player.getActiveHand(), ItemUsage.exchangeStack(stack, player, filledBucket));
          cauldron.clearCauldron();
        } else player.sendMessage(Text.translatable("text.artent_potions.potion.infermentable"), false);
      }
    } else if (stack.getItem() == Items.WATER_BUCKET) {
      fillCauldron(world, pos, cauldron, player, player.getActiveHand(), stack);
    } else if (world.getFuelRegistry().isFuel(stack)) {
      addFuel(cauldron, player, player.getActiveHand(), stack);
    } else if (stack.getItem() == Items.FLINT_AND_STEEL) {
      setOnFire(world, pos, state, cauldron, player, stack);
    } else if (stack.isEmpty()) {
      if (player.isSneaking()) {
        cauldron.clearCauldron();
      }
      state = state.with(IS_BURNING, false);
      world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
    }

    return ActionResult.SUCCESS;
  }

  private void setOnFire(
      World world,
      BlockPos pos,
      BlockState state,
      BrewingCauldronBlockEntity cauldron,
      PlayerEntity player,
      ItemStack stack) {
    if (cauldron.fuelAmount > 0) {
      state = state.with(IS_BURNING, true);
      world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
      if (!player.getAbilities().creativeMode) return;
      stack.damage(1, player);
    }
  }

  private void addFuel(BrewingCauldronBlockEntity cauldron, PlayerEntity player, Hand hand, ItemStack stack) {
    var result = cauldron.addFuel(stack, player.isSneaking());
    if (!player.getAbilities().creativeMode) return;
    if (result == BrewingCauldronBlockEntity.AddFuelResultType.ConsumeStack) {
      player.setStackInHand(hand, ItemStack.EMPTY);
    }
    if (result == BrewingCauldronBlockEntity.AddFuelResultType.ConsumeItem) {
      var newStack = stack.copy();
      newStack.setCount(stack.getCount() - 1);
      player.setStackInHand(hand, newStack);
    }
  }

  public void fillCauldron(
      World world,
      BlockPos pos,
      BrewingCauldronBlockEntity cauldron,
      PlayerEntity player,
      Hand hand,
      ItemStack stack) {
    cauldron.waterAmount = 1000;
    cauldron.updateBlock();
    if (!world.isClient) {
      if (!player.getAbilities().creativeMode) {
        player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BUCKET)));
      }

      world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
      world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
    }
  }

  protected static final VoxelShape SHAPE = VoxelShapes.union(
      Block.createCuboidShape(3, 0, 3, 5, 3, 5),
      Block.createCuboidShape(3, 0, 11, 5, 3, 13),
      Block.createCuboidShape(11, 0, 11, 13, 3, 13),
      Block.createCuboidShape(11, 0, 3, 13, 3, 5),
      Block.createCuboidShape(2, 3, 2, 14, 4, 14),
      Block.createCuboidShape(14, 4, 2, 15, 13, 14),
      Block.createCuboidShape(2, 4, 14, 14, 13, 15),
      Block.createCuboidShape(1, 4, 2, 2, 13, 14),
      Block.createCuboidShape(2, 4, 1, 14, 13, 2));
  protected static final VoxelShape SHAPE_WITH_COAL = VoxelShapes.union(
      Block.createCuboidShape(3, 0, 3, 5, 3, 5),
      Block.createCuboidShape(3, 0, 11, 5, 3, 13),
      Block.createCuboidShape(11, 0, 11, 13, 3, 13),
      Block.createCuboidShape(11, 0, 3, 13, 3, 5),
      Block.createCuboidShape(2, 3, 2, 14, 4, 14),
      Block.createCuboidShape(14, 4, 2, 15, 13, 14),
      Block.createCuboidShape(2, 4, 14, 14, 13, 15),
      Block.createCuboidShape(1, 4, 2, 2, 13, 14),
      Block.createCuboidShape(2, 4, 1, 14, 13, 2),
      Block.createCuboidShape(1, 0, 1, 15, 2, 15));

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return state.get(HAS_COAL) ? SHAPE_WITH_COAL : SHAPE;
  }
}