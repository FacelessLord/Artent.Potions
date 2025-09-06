package faceless.artent.potions.api;

import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class PotionContainerUtil {

  // if b is empty, put max from a
  // else if a not at max, max fill a (if possible. Next otherwise)
  // else if b not at max, max fill b (if possible. Next otherwise)
  public static TransferResult transferBetweenContainers(
      PlayerEntity player,
      PotionContainerInterface a,
      PotionContainerInterface b) {
    if (!a.isValid() || !b.isValid()) return TransferResult.InvalidContainers;

    var aPotion = a.getPotions();
    var aAmount = aPotion.isEmpty() ? 0 : a.getMaxPotionAmount();
    var aSize = a.getMaxPotionAmount();
    var bPotion = b.getPotions();
    var bAmount = bPotion.isEmpty() ? 0 : b.getMaxPotionAmount();
    var bSize = b.getMaxPotionAmount();

    if (bAmount == 0) {
      if (b.canContainPotion(aPotion)) {
        var potionStack = a.extractPotion(b.getMaxPotionAmount());
        if (potionStack != PotionStack.Empty) {
          b.overridePotion(potionStack);
          return TransferResult.MovedToB;
        }
        return TransferResult.AIsEmpty;
      } else {
        b.onCanNotContainPotion(player, aPotion);
        return TransferResult.BCantContain;
      }
    }
    if (aAmount != 0 && !AlchemicalPotionUtil.comparePotions(aPotion, bPotion)) {
      return TransferResult.DifferentPotions;
    }
    if (aAmount < aSize) {
      if (aAmount == 0 && !a.canContainPotion(bPotion)) {
        a.onCanNotContainPotion(player, bPotion);
        return TransferResult.ACantContain;
      }
      var potionToMove = aSize - aAmount;
      if (potionToMove == 0)
        return TransferResult.AIsFull;
      var potionStack = b.extractPotion(potionToMove);
      if (potionStack != PotionStack.Empty) {
        potionStack.setAmount(potionStack.getAmount() + aAmount);
        a.overridePotion(potionStack);
        return TransferResult.MovedToA;
      }
      return TransferResult.BIsEmpty;
    }
    if (bAmount < bSize) {
      var potionToMove = bSize - bAmount;
      if (potionToMove == 0)
        return TransferResult.BIsFull;
      var potionStack = a.extractPotion(potionToMove);
      if (potionStack != PotionStack.Empty) {
        potionStack.setAmount(potionStack.getAmount() + bAmount);
        b.overridePotion(potionStack);
        return TransferResult.MovedToA;
      }
      return TransferResult.AIsEmpty;
    }

    return TransferResult.BothIsFull;
  }

  public static PotionContainerInterface createInterface(ItemStack stack) {
    return new ItemPotionContainerInterface(stack);
  }

  public static PotionContainerInterface createInterface(IPotionContainerBlock blockEntity) {
    return new BlockPotionContainerInterface(blockEntity);
  }

  public enum TransferResult {
    InvalidContainers,
    MovedToB,
    MovedToA,
    AIsEmpty,
    BIsEmpty,
    DifferentPotions,
    ACantContain,
    BCantContain,
    AIsFull,
    BIsFull,
    BothIsFull
  }
}
