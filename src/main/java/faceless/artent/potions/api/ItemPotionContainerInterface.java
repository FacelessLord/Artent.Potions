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
  public boolean isInvalid() {
    return getContainer() == null;
  }


  @Override
  public int getMaxPotionAmount() {
    var container = getContainer();
    assert container != null;
    return container.getMaxPotionAmount(stack);
  }

  @Override
  public int getPotionAmount() {
    var container = getContainer();
    assert container != null;
    return container.getPotionAmount(stack);
  }

  @Override
  public void setPotionAmount(int amount) {
    var container = getContainer();
    assert container != null;
    container.setPotionAmount(stack, amount);
  }

  @Override
  public List<AlchemicalPotion> getPotions() {
    var container = getContainer();
    assert container != null;
    return container.getPotions(stack);
  }

  @Override
  public void clear() {
    var container = getContainer();
    assert container != null;
    container.overridePotion(stack, List.of(), 0);
  }

  @Override
  public boolean canExtractPotion() {
    var container = getContainer();
    assert container != null;
    return container.canExtractPotion(stack);
  }

  @Override
  public void setPotions(List<AlchemicalPotion> potions) {
    var container = getContainer();
    assert container != null;
    container.setPotions(stack, potions);
  }

  @Override
  public boolean canContainPotion(List<AlchemicalPotion> potion) {
    var container = getContainer();
    assert container != null;
    return container.canContainPotion(potion);
  }

  @Override
  public void onCanNotContainPotion(PlayerEntity player, List<AlchemicalPotion> potion) {
    var container = getContainer();
    assert container != null;
    container.onCanNotContainPotion(player, potion);
  }
}
