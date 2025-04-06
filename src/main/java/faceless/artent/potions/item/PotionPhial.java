package faceless.artent.potions.item;

import faceless.artent.core.item.INamed;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.objects.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.consume.UseAction;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class PotionPhial extends Item implements INamed {

  public PotionPhial(Settings settings) {
    super(settings);
  }

  @Override
  public String getId() {
    return "potion_phial";
  }

  @Override
  public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
    PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
    if (playerEntity instanceof ServerPlayerEntity) {
      Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity) playerEntity, stack);
    }
    if (!world.isClient && world instanceof ServerWorld serverWorld) {
      var potion = AlchemicalPotionUtil.getPotion(stack);
      AlchemicalPotionUtil.applyPotionEffects(serverWorld, user, playerEntity, potion);
    }
    if (playerEntity != null) {
      playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
      if (!playerEntity.getAbilities().creativeMode) {
        stack.decrement(1);
        playerEntity.giveOrDropStack(new ItemStack(ModItems.EmptyPhial));
      }
    }
    world.emitGameEvent(user, GameEvent.DRINK, user.getPos());
    return stack;
  }

  @Override
  public int getMaxUseTime(ItemStack stack, LivingEntity user) {
    return 32;
  }

  @Override
  public UseAction getUseAction(ItemStack stack) {
    return UseAction.DRINK;
  }

  @Override
  public ActionResult use(World world, PlayerEntity user, Hand hand) {
    return ItemUsage.consumeHeldItem(world, user, hand);
  }

  @Override
  public Text getName(ItemStack stack) {
    var key = AlchemicalPotionUtil.getPotionKey(stack);
    return Text.translatable("item.potion." + key);
  }

  @Override
  public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
    AlchemicalPotionUtil.buildTooltip(stack, tooltip, 1.0f, context.getUpdateTickRate());
  }
}