package faceless.artent.potions.block;

import com.mojang.serialization.MapCodec;
import faceless.artent.core.api.ChatUtils;
import faceless.artent.core.api.MiscUtils;
import faceless.artent.core.item.INamed;
import faceless.artent.potions.blockEntities.FermentingBarrelBlockEntity;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.item.ConcentrateItem;
import faceless.artent.potions.objects.ModBlockEntities;
import faceless.artent.potions.objects.ModItems;
import faceless.artent.potions.registry.AlchemicalPotionRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FermentingBarrel extends BlockWithEntity implements INamed {
  public static final MapCodec<FermentingBarrel> CODEC = FermentingBarrel.createCodec(FermentingBarrel::new);
  public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;

  @Override
  protected MapCodec<? extends BlockWithEntity> getCodec() {
    return CODEC;
  }

  public FermentingBarrel(Settings settings) {
    super(settings);
    this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
  }

  @Override
  public String getId() {
    return "fermenting_barrel";
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    // With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
    return BlockRenderType.MODEL;
  }

  @Override
  protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    var be = (FermentingBarrelBlockEntity) world.getBlockEntity(pos);
    if (be == null) return ActionResult.FAIL;
    var stack = player.getEquippedStack(player.getActiveHand() == Hand.MAIN_HAND
                                            ? EquipmentSlot.MAINHAND
                                            : EquipmentSlot.OFFHAND);
    if (stack.getItem() == ModItems.GoldenBucketFilled) {
      if (be.potionKey.isEmpty()) {
        var potion = AlchemicalPotionUtil.getPotion(stack);
        if (potion != null) {
          be.setPotionId(potion.id);
          player.setStackInHand(
              player.getActiveHand(),
              ItemUsage.exchangeStack(stack, player, new ItemStack(ModItems.GoldenBucket)));
          ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled");
        }
      } else
        ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled.already");
    } else if (stack.getItem() == ModItems.PotionPhial) {
      var potion = AlchemicalPotionUtil.getPotion(stack);
      if (potion == null || !AlchemicalPotionRegistry.fermentedPotionIsRegistered(potion.id)) {
        ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.potion.infermentable");
        return ActionResult.FAIL;
      }
      if (be.potionKey.isEmpty()) {
        be.setPotionId(potion.id, 3);
        if (!player.isCreative()) {
          stack.decrement(1);
          MiscUtils.giveOrDropItem(world, pos, player, new ItemStack(ModItems.EmptyPhial));
        }
        ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled");
      } else if (be.potionKey.equals(potion.id)) {
        if (be.portionsLeft <= 6) {
          be.setPotionId(potion.id, be.portionsLeft + 3);
          if (!player.isCreative()) {
            stack.decrement(1);
            MiscUtils.giveOrDropItem(world, pos, player, new ItemStack(ModItems.EmptyPhial));
          }
          ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled");
        } else
          ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled.already");
      } else
        ChatUtils.sendMessageToPlayer(world, player, "text.artent_potions.barrel.filled.different");
    } else if (be.isFermented()) {
      ActionResult result = fillConcentrate(world, pos, player, stack, be);
      if (result == ActionResult.FAIL) return result;
    }
    if (stack.isEmpty() && world.isClient()) {
      if (be.isFermented()) {
        player.sendMessage(
            Text
                .translatable("text.artent_potions.barrel.fermented." + be.portionsLeft + "/" + 9 + ".of")
                .append(Text.translatable(be.potionKey)), false);
      } else {
        if (!be.potionKey.isEmpty())
          player.sendMessage(
              Text
                  .translatable("text.artent_potions.barrel.fermenting." + be.portionsLeft + "/" + 9)
                  .append(Text.translatable(be.potionKey))
                  .append(Text.translatable("text.artent_potions.fermentation.time.prefix"))
                  .append(Text.translatable(String.valueOf((FermentingBarrelBlockEntity.FERMENTATION_TIME
                                                            -
                                                            be.fermentedTime) / 20)))
                  .append(Text.translatable("text.artent_potions.fermentation.time.suffix")), false);
        else
          player.sendMessage(Text.translatable("text.artent_potions.barrel.empty"), false);
      }
    }
    return ActionResult.SUCCESS;
  }

  private static ActionResult fillConcentrate(
      World world,
      BlockPos pos,
      PlayerEntity player,
      ItemStack stack,
      FermentingBarrelBlockEntity be) {
    var barrelPotion = AlchemicalPotionRegistry.getFermentedPotions().getOrDefault(be.potionKey, null);
    if (barrelPotion == null) return ActionResult.FAIL;
    var potionKey = barrelPotion.id;
    var item = stack.getItem();

    if (item instanceof ConcentrateItem container) {
      var itemPotion = AlchemicalPotionUtil.getFermentedPotion(stack);
      var containerSize = container.getMaxSize(stack);

      if (itemPotion == null || itemPotion.id == null) {
        var amount = be.takePotionPortions(containerSize);
        if (amount == 0) return ActionResult.FAIL;
        if (!player.isCreative()) {
          stack.decrement(containerSize);
        }

        var containerStack = new ItemStack(container);
        container.setConcentrateAmount(containerStack, potionKey, amount);
        MiscUtils.giveOrDropItem(world, pos, player, containerStack);
      } else if (itemPotion.id.equals(potionKey)) {
        var concentrateAmount = container.getConcentrateAmount(stack);
        var amount = be.takePotionPortions(Math.min(containerSize - concentrateAmount, containerSize));
        if (amount == 0) return ActionResult.FAIL;
        container.setConcentrateAmount(stack, amount + concentrateAmount);
      }
    }
    return ActionResult.SUCCESS;
  }

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
      World world,
      BlockState state,
      BlockEntityType<T> type
                                                               ) {
    return validateTicker(
        type,
        ModBlockEntities.FermentingBarrel,
        (world1, pos, state1, be) -> be.tick(world1, pos, state1));
  }

  @Nullable
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new FermentingBarrelBlockEntity(pos, state);
  }

  // TODO change model based on surrounding - cut upper portion of box when there is no block over barrel
  // add top legs when there is a barrel over it
}