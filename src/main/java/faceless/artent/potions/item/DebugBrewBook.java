package faceless.artent.potions.item;

import faceless.artent.potions.api.IDebuggableBlock;
import faceless.artent.potions.api.IDebuggableItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.ArrayList;

public class DebugBrewBook extends Item {
  public DebugBrewBook(Settings settings) {
    super(settings);
  }

  @Override
  public ActionResult use(World world, PlayerEntity user, Hand hand) {
    if (user.getWorld().isClient) {
      var slotToLookAt = (user.getInventory().selectedSlot + 1) % 9;
      var itemToTheRight = user.getInventory().getStack(slotToLookAt);
      if (itemToTheRight.getItem() instanceof IDebuggableItem debug) {
        var debugInfo = new ArrayList<String>();
        debug.fillDebugInfo(itemToTheRight, debugInfo);

        for (var line : debugInfo)
          user.sendMessage(Text.literal(line), false);

        return ActionResult.SUCCESS;
      }
    }

    return super.use(world, user, hand);
  }

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {
    var world = context.getWorld();

    var pos = context.getBlockPos();
    var block = world.getBlockState(pos);

    if (block.getBlock() instanceof IDebuggableBlock debug) {
      var debugInfo = new ArrayList<String>();
      debugInfo.add(world.isClient ? "CLIENT" : "SERVER");

      debug.fillDebugInfo(world, pos, block, debugInfo);
      var player = context.getPlayer();
      if (player == null)
        return ActionResult.FAIL;

      for (var line : debugInfo)
        player.sendMessage(Text.literal(line), false);

      return ActionResult.SUCCESS;
    }


    return super.useOnBlock(context);
  }
}
