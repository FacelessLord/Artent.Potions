package faceless.artent.potions.api;

import faceless.artent.potions.brewingApi.AlchemicalPotion;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemPotionContainerInterface extends PotionContainerInterface {
  private final ItemStack stack;

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
  public boolean isValid() {
    return getContainer() != null;
  }

  @Override
  public int getMaxPotionAmount() {
    return getContainer().getMaxPotionAmount(stack);
  }

  @Override
  public int getPotionAmount() {
    return getContainer().getPotionAmount(stack);
  }

  @Override
  public void setPotionAmount(int amount) {
    getContainer().setPotionAmount(stack, amount);
  }

  @Override
  public List<AlchemicalPotion> getPotions() {
    return getContainer().getPotions(stack);
  }

  @Override
  public void clear() {
    var container = getContainer();
    container.overridePotion(stack, List.of(), 0);
  }

  @Override
  public boolean canExtractPotion() {
    return getContainer().canExtractPotion(stack);
  }

  @Override
  public void setPotions(List<AlchemicalPotion> potions) {
    getContainer().setPotions(stack, potions);
  }

  @Override
  public boolean canContainPotion(List<AlchemicalPotion> potion) {
    return getContainer().canContainPotion(potion);
  }

  @Override
  public void onCanNotContainPotion(PlayerEntity player, List<AlchemicalPotion> potion) {
    getContainer().onCanNotContainPotion(player, potion);
  }
}
