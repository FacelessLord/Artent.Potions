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
  public boolean isInvalid() {
    return container == null;
  }

  @Override
  public int getMaxPotionAmount() {
    return container.getMaxPotionAmount();
  }

  @Override
  public int getPotionAmount() {
    return container.getPotionAmount();
  }

  @Override
  public void setPotionAmount(int amount) {
    container.setPotionAmount(amount);
  }

  @Override
  public List<AlchemicalPotion> getPotions() {
    return container.getPotions();
  }

  @Override
  public void clear() {
    container.clear();
  }

  @Override
  public boolean canExtractPotion() {
    return container.canExtractPotion();
  }

  @Override
  public void setPotions(List<AlchemicalPotion> potions) {
    container.setPotions(potions);
  }

  @Override
  public boolean canContainPotion(List<AlchemicalPotion> potion) {
    return container.canContainPotion(potion);
  }

  @Override
  public void onCanNotContainPotion(PlayerEntity player, List<AlchemicalPotion> potion) {
    container.onCanNotContainPotion(player, potion);
  }
}
