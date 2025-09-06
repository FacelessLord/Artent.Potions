package faceless.artent.potions.api;

import faceless.artent.potions.brewingApi.AlchemicalPotion;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class BlockPotionContainerInterface extends PotionContainerInterface {
  private final IPotionContainerBlock container;

  public BlockPotionContainerInterface(IPotionContainerBlock container) {
    this.container = container;
  }

  @Override
  boolean isValid() {
    return container != null;
  }

  @Override
  int getMaxPotionAmount() {
    return container.getMaxPotionAmount();
  }

  @Override
  int getPotionAmount() {
    return container.getPotionAmount();
  }

  @Override
  void setPotionAmount(int amount) {
    container.setPotionAmount(amount);
  }

  @Override
  List<AlchemicalPotion> getPotions() {
    return container.getPotions();
  }

  @Override
  void clear() {
    container.clear();
  }

  @Override
  boolean canExtractPotion() {
    return container.canExtractPotion();
  }

  @Override
  void setPotions(List<AlchemicalPotion> potions) {
    container.setPotions(potions);
  }

  @Override
  boolean canContainPotion(List<AlchemicalPotion> potion) {
    return container.canContainPotion(potion);
  }

  @Override
  void onCanNotContainPotion(PlayerEntity player, List<AlchemicalPotion> potion) {
    container.onCanNotContainPotion(player, potion);
  }
}
