package faceless.artent.potions.objects;

import faceless.artent.core.item.group.ArtentItemGroupBuilder;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
  public static final ArtentItemGroupBuilder BASE = new ArtentItemGroupBuilder(
      () -> new ItemStack(ModBlocks.BREWING_CAULDRON.item()),
      "base");
  public static final ArtentItemGroupBuilder POTIONS = new ArtentItemGroupBuilder(
      () -> {
        var stack = new ItemStack(ModItems.SMALL_BOTTLE);
        AlchemicalPotionUtil.setPotion(stack, AlchemicalPotions.FLIGHT);
        return stack;
      },
      "potions");
}
