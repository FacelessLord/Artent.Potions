package faceless.artent.potions.api;

import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface IPotionContainerItem {
  List<AlchemicalPotion> getPotions(ItemStack stack);

  default boolean hasPotion(ItemStack stack) {
    var potions = getPotions(stack);
    var amount = getPotionAmount(stack);
    return potions != null && !potions.isEmpty() && amount > 0;
  }

  void setPotions(ItemStack stack, List<AlchemicalPotion> potions);

  default void addPotion(ItemStack stack, AlchemicalPotion potion) {
    var potions = new ArrayList<>(getPotions(stack));
    potions.add(potion);
    AlchemicalPotionUtil.setPotions(stack, potions);
  }

  int getMaxPotionAmount(ItemStack stack);

  int getPotionAmount(ItemStack stack);

  void setPotionAmount(ItemStack stack, int amount);

  default void overridePotion(ItemStack stack, List<AlchemicalPotion> potions, int amount) {
    setPotions(stack, potions);
    setPotionAmount(stack, amount);
  }

  default boolean canExtractPotion(ItemStack stack) {
    var amount = getPotionAmount(stack);
    var potion = getPotions(stack);

    return amount > 0 && !potion.isEmpty();
  }

  default boolean canContainPotion(List<AlchemicalPotion> potion) {
    return true;
  }

  default void onCanNotContainPotion(PlayerEntity player, List<AlchemicalPotion> potion) {
  }
}
