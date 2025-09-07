package faceless.artent.potions.api;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IDebuggableItem {
  void fillDebugInfo(ItemStack stack, List<String> debugInfo);
}
