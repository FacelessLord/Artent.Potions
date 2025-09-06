package faceless.artent.potions.api;

import faceless.artent.potions.brewingApi.AlchemicalPotion;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public interface IPotionContainerBlock {
  int getMaxPotionAmount();

  int getPotionAmount();

  void setPotionAmount(int amount);

  List<AlchemicalPotion> getPotions();

  void clear();

  boolean canExtractPotion();

  default PotionStack extractPotion(int amount) {
    var potion = getPotions();
    var blockPotionAmount = getPotionAmount();

    if (ListUtils.isNullOrEmpty(potion) || blockPotionAmount == 0 || !canExtractPotion())
      return PotionStack.Empty;

    var consumedAmount = Math.min(blockPotionAmount, amount);

    if (consumedAmount < amount) {
      clear();
    } else {
      setPotionAmount(getPotionAmount() - consumedAmount);
    }

    return new PotionStack(consumedAmount, potion);
  }

  void setPotions(List<AlchemicalPotion> potions);

  default void overridePotion(List<AlchemicalPotion> potions, int amount) {
    setPotions(potions);
    setPotionAmount(amount);
  }

  default void addPotion(AlchemicalPotion potion) {
    var potions = new ArrayList<>(getPotions());
    potions.add(potion);
    setPotions(potions);
  }

  default boolean canContainPotion(List<AlchemicalPotion> potion) {
    return true;
  }

  default void onCanNotContainPotion(PlayerEntity player, List<AlchemicalPotion> potion) {
  }
}
