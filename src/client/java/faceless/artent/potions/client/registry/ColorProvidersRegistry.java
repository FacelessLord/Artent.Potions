package faceless.artent.potions.client.registry;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.blockEntities.BrewingCauldronBlockEntity;
import faceless.artent.potions.objects.ModBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class ColorProvidersRegistry implements IRegistry {
  @Override
  public void register() {
    ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
      if (view == null) return 0;
      var be = view.getBlockEntity(pos);
      if (be instanceof BrewingCauldronBlockEntity cauldron) {
        return cauldron.color.toHex();
      }
      return 0;
    }, ModBlocks.CauldronFluid);

    // TODO
//    Color.ITEM.register(
//        (ItemStack stack, int tintIndex) -> {
//          if (tintIndex == 0) return -1;
//          return AlchemicalPotionUtil.getColor(stack);
//        },
//        ModItems.PotionPhial,
//        ModItems.PotionPhialExplosive,
//        ModItems.GoldenBucketFilled,
//        ModItems.SmallConcentrate,
//        ModItems.MediumConcentrate,
//        ModItems.BigConcentrate);

  }
}
