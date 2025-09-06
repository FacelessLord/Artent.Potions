package faceless.artent.potions.item;

import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class FilledGoldenBucket extends Item {
  public FilledGoldenBucket(Settings settings) {
    super(settings);
  }

  @Override
  public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
    AlchemicalPotionUtil.createEffectListTooltip(stack, tooltip);
  }
}