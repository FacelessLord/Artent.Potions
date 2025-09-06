package faceless.artent.potions.brewingApi;

import net.minecraft.item.ItemStack;

import java.util.List;

import static faceless.artent.potions.registry.DataComponentRegistry.CONCENTRATE_AMOUNT;
import static faceless.artent.potions.registry.DataComponentRegistry.POTION_KEY;

public class PotionDataUtil {

  public static List<String> getPotionsKeys(ItemStack stack) {
    return stack.get(POTION_KEY);
  }

  public static void setPotionKeys(ItemStack stack, List<String> keys) {
    stack.set(POTION_KEY, keys);
  }

  public static int getConcentrateAmount(ItemStack stack) {
    var integer = stack.get(CONCENTRATE_AMOUNT);
    return integer == null ? 0 : integer;
  }

  public static void setConcentrateAmount(ItemStack stack, int amount) {
    stack.set(CONCENTRATE_AMOUNT, amount);
  }
}