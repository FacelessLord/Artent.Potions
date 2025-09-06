package faceless.artent.potions.api;

import faceless.artent.potions.brewingApi.AlchemicalPotion;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class PotionContainerInterface {
  abstract boolean isValid();

  abstract int getMaxPotionAmount();

  abstract int getPotionAmount();

  abstract void setPotionAmount(int amount);

  abstract List<AlchemicalPotion> getPotions();

  abstract void clear();

  abstract boolean canExtractPotion();

  abstract void setPotions(List<AlchemicalPotion> potions);

  abstract boolean canContainPotion(List<AlchemicalPotion> potion);

  abstract void onCanNotContainPotion(PlayerEntity player, List<AlchemicalPotion> potion);

  PotionStack extractPotion(int amount) {
    var potion = getPotions();
    var blockPotionAmount = getPotionAmount();

    if (ListUtils.isNullOrEmpty(potion) || blockPotionAmount == 0 || !canExtractPotion()) return PotionStack.Empty;

    var consumedAmount = Math.min(blockPotionAmount, amount);

    if (consumedAmount < amount) {
      clear();
    } else {
      setPotionAmount(getPotionAmount() - consumedAmount);
    }

    return new PotionStack(consumedAmount, potion);
  }

  void overridePotion(List<AlchemicalPotion> potions, int amount) {
    setPotions(potions);
    setPotionAmount(amount);
  }

  void overridePotion(PotionStack stack) {
    setPotions(stack.getPotions());
    setPotionAmount(stack.getAmount());
  }

  void addPotion(AlchemicalPotion potion) {
    var potions = new ArrayList<>(getPotions());
    potions.add(potion);
    setPotions(potions);
  }
}
