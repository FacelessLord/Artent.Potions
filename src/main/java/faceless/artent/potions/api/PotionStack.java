package faceless.artent.potions.api;

import faceless.artent.potions.brewingApi.AlchemicalPotion;

import java.util.List;

public class PotionStack {
  private List<AlchemicalPotion> potions;
  private int amount;

  public PotionStack(int amount, List<AlchemicalPotion> potions) {
    this.amount = amount;
    this.potions = potions;
  }

  public List<AlchemicalPotion> getPotions() {
    return potions;
  }

  public void setPotions(List<AlchemicalPotion> potions) {
    this.potions = potions;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public static final PotionStack Empty = new PotionStack(0, List.of());
}
