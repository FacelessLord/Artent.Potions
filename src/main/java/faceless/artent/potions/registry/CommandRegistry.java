package faceless.artent.potions.registry;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.objects.ModCommands;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandRegistry implements IRegistry {
  @Override
  public void register() {
    CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
        ModCommands.ArtentPotionsCommand));
  }
}
