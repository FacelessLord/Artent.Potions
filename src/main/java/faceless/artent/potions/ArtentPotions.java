package faceless.artent.potions;

import faceless.artent.potions.network.ArtentServerHook;
import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModItems;
import faceless.artent.potions.objects.ModParticles;
import faceless.artent.potions.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtentPotions implements ModInitializer {
  public static final String MODID = "artent_potions";
  // This logger is used to write text to the console and the log file.
  // It is considered best practice to use your mod id as the logger's name.
  // That way, it's clear which mod wrote info, warnings, and errors.
  public static final Logger LOGGER = LoggerFactory.getLogger("artent_potions");

  public static BlockEntityRegistry BlockEntities = new BlockEntityRegistry();
  public static ItemGroupRegistry ItemGroups = new ItemGroupRegistry();
  public static ArtentServerHook ServerHook = new ArtentServerHook();

  public static ModItems Items = new ModItems();
  public static ModBlocks Blocks = new ModBlocks();
  public static EntityRegistry Entities = new EntityRegistry();
  public static AlchemicalPotionRegistry Potions = new AlchemicalPotionRegistry();
  public static BrewingRegistry Brewing = new BrewingRegistry();
  public static CommandRegistry Commands = new CommandRegistry();
  public static StatusEffectsRegistry StatusEffects = new StatusEffectsRegistry();
  public static DataComponentRegistry DataComponents = new DataComponentRegistry();
  public static FeatureRegistry Features = new FeatureRegistry();
  public static ArtentLootTableModifiers LootTableModifiers = new ArtentLootTableModifiers();
  public static ModParticles Particles = new ModParticles();

  @Override
  public void onInitialize() {
    Potions.register();

    Blocks.register();
    Items.register();
    BlockEntities.register();
    Entities.register();
    Features.register();
    Particles.register();

    LootTableModifiers.modifyLootTables();
    Brewing.register();

    Commands.register();
    ServerHook.load();
    ItemGroups.register();
  }
}
