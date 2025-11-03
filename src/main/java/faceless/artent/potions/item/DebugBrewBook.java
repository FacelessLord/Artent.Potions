package faceless.artent.potions.item;

import com.mojang.serialization.Codec;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.api.IDebuggableBlock;
import faceless.artent.potions.api.IDebuggableItem;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class DebugBrewBook extends Item {
  // 0 - server
  // 1 - client
  public static final ComponentType<Integer> CLIENT_SERVER_MODE = Registry.register(
      Registries.DATA_COMPONENT_TYPE,
      Identifier.of(ArtentPotions.MODID, "client_server_mode"),
      ComponentType.<Integer>builder().codec(Codec.INT).build());

  public DebugBrewBook(Settings settings) {
    super(settings);
    settings.component(CLIENT_SERVER_MODE, 0);
  }

  @Override
  public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
    super.appendTooltip(stack, context, tooltip, type);
    var isDebuggingClient = stack.getOrDefault(CLIENT_SERVER_MODE, 0) == 1;
    var modeString = isDebuggingClient ? "CLIENT" : "SERVER";
    tooltip.add(Text.literal(modeString));
  }

  @Override
  public ActionResult use(World world, PlayerEntity user, Hand hand) {
    var debugBook = user.getStackInHand(hand);
    var currentMode = debugBook.getOrDefault(CLIENT_SERVER_MODE, 0);
    var isDebuggingClient = currentMode == 1;
    if (user.isSneaking()) {
      debugBook.set(CLIENT_SERVER_MODE, 1 - currentMode);
      return ActionResult.SUCCESS;
    }

    if (user.getWorld().isClient == isDebuggingClient) {
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
    var debugBook = context.getStack();
    var isDebuggingClient = debugBook.getOrDefault(CLIENT_SERVER_MODE, 0) == 1;
    if (world.isClient == isDebuggingClient) {
      if (block.getBlock() instanceof IDebuggableBlock debug) {
        var debugInfo = new ArrayList<String>();
        debugInfo.add(world.isClient ? "CLIENT" : "SERVER");

        debug.fillDebugInfo(world, pos, block, debugInfo);
        var player = context.getPlayer();
        if (player == null) return ActionResult.FAIL;

        for (var line : debugInfo)
          player.sendMessage(Text.literal(line), false);

        return ActionResult.SUCCESS;
      }
    }

    return super.useOnBlock(context);
  }
}
