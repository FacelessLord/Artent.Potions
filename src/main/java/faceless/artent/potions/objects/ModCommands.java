package faceless.artent.potions.objects;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import faceless.artent.potions.api.IPotionContainerItem;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class ModCommands {
  public static final LiteralArgumentBuilder<ServerCommandSource> ArtentPotionsCommand = CommandManager
      .literal("artent")
      .requires(source -> source.hasPermissionLevel(2))
      .then(CommandManager
                .literal("replenish")
                .then(CommandManager
                          .argument("player", EntityArgumentType.players())
                          .executes(ModCommands::replenishPlayer)));

  private static int replenishPlayer(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
    var players = EntityArgumentType.getPlayers(ctx, "player");
    for (var player : players) {
      var inventory = player.getInventory().main;
      for (net.minecraft.item.ItemStack stack : inventory) {
        if (!(stack.getItem() instanceof IPotionContainerItem potionContainer)
            || !potionContainer.hasPotion(stack)) {
          continue;
        }
        potionContainer.setPotionAmount(stack, potionContainer.getMaxPotionAmount(stack));
      }
    }
    return 0;
  }
}
