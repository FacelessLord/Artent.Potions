package faceless.artent.potions.objects;

import faceless.artent.core.item.group.ArtentItemGroupBuilder;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ArtentPotionsItemGroups {
  public static final ArtentItemGroupBuilder POTIONS = new ArtentItemGroupBuilder(
      () -> {
        var stack = new ItemStack(ModItems.SMALL_BOTTLE);
        AlchemicalPotionUtil.setPotion(stack, AlchemicalPotions.FLIGHT);
        return stack;
      },
      ArtentPotions.MODID + ".potions");
}
