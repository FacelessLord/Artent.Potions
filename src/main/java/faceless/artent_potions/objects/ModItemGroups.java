package faceless.artent_potions.objects;

import faceless.artent_potions.brewingApi.item.group.ArtentItemGroupBuilder;
import faceless.artent_potions.brewing.api.AlchemicalPotionUtil;
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
