package faceless.artent.potions.item;

import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.objects.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.consume.UseAction;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

import static faceless.artent.potions.registry.DataComponentRegistry.POTION_KEY;

public class SmallConcentrate extends Item {
  public SmallConcentrate(Settings settings) {
    super(settings);
  }

  @Override
  public Text getName(ItemStack stack) {
    var key = stack.get(POTION_KEY);
    return Text.translatable("item.concentrate.small." + key);
  }

  @Override
  public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
    AlchemicalPotionUtil.createFermentedEffectTooltip(stack, tooltip);
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
  public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
    PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
    return AlchemicalPotionUtil.drinkFermentedPotion(stack, world, playerEntity, ModItems.SmallConcentratePhial);
  }
}