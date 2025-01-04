package faceless.artent.potions.objects;

import faceless.artent.core.item.group.ArtentItemGroupBuilder;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
    public static final ArtentItemGroupBuilder Potions = new ArtentItemGroupBuilder(
      () -> {
          var stack = new ItemStack(ModItems.PotionPhialExplosive);
          AlchemicalPotionUtil.setPotion(stack, AlchemicalPotions.FLIGHT);
          return stack;
      },
      "potions");
}
