package faceless.artent.potions.api;

import faceless.artent.potions.brewingApi.AlchemicalPotion;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class PotionContainerInterface {
  public abstract boolean isValid();

  public abstract int getMaxPotionAmount();

  public abstract int getPotionAmount();

  public abstract void setPotionAmount(int amount);

  public abstract List<AlchemicalPotion> getPotions();

  public abstract void clear();

  public abstract boolean canExtractPotion();

  public abstract void setPotions(List<AlchemicalPotion> potions);

  public abstract boolean canContainPotion(List<AlchemicalPotion> potion);

  public abstract void onCanNotContainPotion(PlayerEntity player, List<AlchemicalPotion> potion);

  public PotionStack extractPotion(int amount) {
    var potion = new ArrayList<>(getPotions());
    var blockPotionAmount = getPotionAmount();

    if (ListUtils.isNullOrEmpty(potion) || blockPotionAmount == 0 || !canExtractPotion()) return PotionStack.Empty;

    var consumedAmount = Math.min(blockPotionAmount, amount);

    if (blockPotionAmount == consumedAmount) {
      clear();
    } else {
      setPotionAmount(getPotionAmount() - consumedAmount);
    }

    return new PotionStack(consumedAmount, potion);
  }

  public void overridePotion(List<AlchemicalPotion> potions, int amount) {
    setPotions(potions);
    setPotionAmount(amount);
  }

  public void overridePotion(PotionStack stack) {
    setPotions(stack.getPotions());
    setPotionAmount(stack.getAmount());
  }

  public void addPotion(AlchemicalPotion potion) {
    var potions = new ArrayList<>(getPotions());
    potions.add(potion);
    setPotions(potions);
  }
}
