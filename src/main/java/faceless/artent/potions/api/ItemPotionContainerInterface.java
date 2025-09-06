package faceless.artent.potions.api;

import faceless.artent.potions.brewingApi.AlchemicalPotion;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemPotionContainerInterface extends PotionContainerInterface {
  private ItemStack stack;

  public ItemPotionContainerInterface(ItemStack stack) {
    this.stack = stack;
  }

  private IPotionContainerItem getContainer() {
    if (stack.getItem() instanceof IPotionContainerItem item) {
      return item;
    }
    return null;
  }

  @Override
  boolean isValid() {
    return getContainer() != null;
  }

  @Override
  int getMaxPotionAmount() {
    return getContainer().getMaxPotionAmount(stack);
  }

  @Override
  int getPotionAmount() {
    return getContainer().getPotionAmount(stack);
  }

  @Override
  void setPotionAmount(int amount) {
    getContainer().setPotionAmount(stack, amount);
  }

  @Override
  List<AlchemicalPotion> getPotions() {
    return getContainer().getPotions(stack);
  }

  @Override
  void clear() {
    var container = getContainer();
    container.overridePotion(stack, List.of(), 0);
  }

  @Override
  boolean canExtractPotion() {
    return getContainer().canExtractPotion(stack);
  }

  @Override
  void setPotions(List<AlchemicalPotion> potions) {
    getContainer().setPotions(stack, potions);
  }

  @Override
  boolean canContainPotion(List<AlchemicalPotion> potion) {
    return getContainer().canContainPotion(potion);
  }

  @Override
  void onCanNotContainPotion(PlayerEntity player, List<AlchemicalPotion> potion) {
    getContainer().onCanNotContainPotion(player, potion);
  }
}
