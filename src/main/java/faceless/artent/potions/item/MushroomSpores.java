package faceless.artent.potions.item;

import faceless.artent.potions.api.MushroomType;
import faceless.artent.potions.objects.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class MushroomSpores extends Item {
  private final MushroomType type;

  public MushroomSpores(MushroomType type, Settings settings) {
    super(settings);
    this.type = type;
  }

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {
    var pos = context.getBlockPos();
    var block = context.getWorld().getBlockState(pos).getBlock();
    var player = context.getPlayer();
    if (block == Blocks.DIRT && player != null && context.getWorld().isAir(pos.up())) {
      context.getWorld().setBlockState(pos, ModBlocks.MushroomInfo[this.type.ordinal()].mycelium().getDefaultState());
      return ItemUsage.consumeHeldItem(context.getWorld(), player, context.getHand());
    }

    return super.useOnBlock(context);
  }
}
