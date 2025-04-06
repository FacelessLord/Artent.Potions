package faceless.artent.potions.item;

import faceless.artent.potions.api.IConcentrateContainerItem;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
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

public class ConcentrateItem extends Item implements IConcentrateContainerItem {
  public String type;
  public int size;

  public ConcentrateItem(Settings settings, String type, int size) {
    super(settings);
    this.type = type;
    this.size = size;
  }

  @Override
  public Text getName(ItemStack stack) {
    var key = stack.get(POTION_KEY);
    return Text.translatable("item.concentrate." + this.type + "." + (key == null ? "empty" : key));
  }

  @Override
  public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
    AlchemicalPotionUtil.createFermentedEffectTooltip(stack, tooltip);
  }

  @Override
  public int getMaxUseTime(ItemStack stack, LivingEntity user) {
    return 32;
  }

  @Override
  public UseAction getUseAction(ItemStack stack) {
    var concentrateAmount = AlchemicalPotionUtil.getConcentrateAmount(stack);
    var potion = AlchemicalPotionUtil.getPotion(stack);
    if (concentrateAmount > 0 && potion != null)
      return UseAction.DRINK;
    return UseAction.NONE;
  }

  @Override
  public ActionResult use(World world, PlayerEntity user, Hand hand) {
    var stack = user.getStackInHand(hand);
    var concentrateAmount = AlchemicalPotionUtil.getConcentrateAmount(stack);
    var potion = AlchemicalPotionUtil.getPotion(stack);
    if (concentrateAmount > 0 && potion != null)
      return ItemUsage.consumeHeldItem(world, user, hand);
    return ActionResult.PASS;
  }

  @Override
  public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
    PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
    return AlchemicalPotionUtil.drinkFermentedPotion(stack, world, playerEntity);
  }

  @Override
  public int getMaxSize(ItemStack stack) {
    return this.size;
  }

  @Override
  public int getConcentrateAmount(ItemStack stack) {
    return AlchemicalPotionUtil.getConcentrateAmount(stack);
  }

  @Override
  public String getPotionKey(ItemStack stack) {
    return AlchemicalPotionUtil.getPotionKey(stack);
  }

  @Override
  public void setConcentrateAmount(ItemStack stack, String potionKey, int amount) {
    AlchemicalPotionUtil.setFermentedPotionByKey(stack, potionKey, amount);
  }

  @Override
  public void setConcentrateAmount(ItemStack stack, int amount) {
    AlchemicalPotionUtil.setConcentrateAmount(stack, amount);
  }
}