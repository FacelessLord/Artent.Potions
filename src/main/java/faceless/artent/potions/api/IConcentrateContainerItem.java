package faceless.artent.potions.api;

import net.minecraft.item.ItemStack;

public interface IConcentrateContainerItem {
  int getMaxSize(ItemStack stack);
  int getConcentrateAmount(ItemStack stack);

  String getPotionKey(ItemStack stack);

  void setConcentrateAmount(ItemStack stack, String potionKey, int amount);
  void setConcentrateAmount(ItemStack stack, int amount);
}
