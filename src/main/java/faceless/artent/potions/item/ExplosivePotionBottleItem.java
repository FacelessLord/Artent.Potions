package faceless.artent.potions.item;

import faceless.artent.potions.api.IPotionContainerItem;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.brewingApi.PotionDataUtil;
import faceless.artent.potions.entity.ThrowablePotionPhialEntity;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.consume.UseAction;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class ExplosivePotionBottleItem extends PotionBottleItem {

  public ExplosivePotionBottleItem(Settings settings, String type, int size) {
    super(settings, type, size);
  }

  @Override
  protected String getPhialBaseNameTranslationId() {
    return "artent.item.potion_bottle.explosive." + this.type;
  }

  @Override
  public UseAction getUseAction(ItemStack stack) {
    return UseAction.NONE;
  }

  @Override
  public ActionResult use(World world, PlayerEntity user, Hand hand) {
    ItemStack itemStack = user.getStackInHand(hand);
    if (!world.isClient) {
      ThrowablePotionPhialEntity potionEntity = new ThrowablePotionPhialEntity(world, user, itemStack);
      potionEntity.setItem(itemStack);
      potionEntity.setVelocity(user, user.getPitch(), user.getYaw(), -20.0f, 0.5f, 1.0f);
      world.spawnEntity(potionEntity);
    }
    user.incrementStat(Stats.USED.getOrCreateStat(this));
    if (!user.getAbilities().creativeMode) {
      itemStack.decrement(1);
    }
    return ItemUsage.consumeHeldItem(world, user, hand);
  }
}