package faceless.artent.potions.item;

import faceless.artent.potions.api.IPotionContainerItem;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.brewingApi.PotionDataUtil;
import faceless.artent.potions.registry.AlchemicalPotionRegistry;
import faceless.artent.potions.registry.FermentationRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class GoldenBucket extends Item implements IPotionContainerItem {
  public GoldenBucket(Settings settings) {
    super(settings);
  }

  @Override
  public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
    AlchemicalPotionUtil.createEffectListTooltip(stack, tooltip);
  }

  @Override
  public List<AlchemicalPotion> getPotions(ItemStack stack) {
    return AlchemicalPotionUtil.getPotions(stack);
  }

  @Override
  public void setPotions(ItemStack stack, List<AlchemicalPotion> potions) {
    AlchemicalPotionUtil.setPotions(stack, potions);
  }

  @Override
  public int getMaxPotionAmount(ItemStack stack) {
    return 9;
  }

  @Override
  public int getPotionAmount(ItemStack stack) {
    return PotionDataUtil.getConcentrateAmount(stack);
  }

  @Override
  public void setPotionAmount(ItemStack stack, int amount) {
    PotionDataUtil.setConcentrateAmount(stack, amount);
  }

  @Override
  public boolean canContainPotion(List<AlchemicalPotion> potion) {
    return true;
  }
}