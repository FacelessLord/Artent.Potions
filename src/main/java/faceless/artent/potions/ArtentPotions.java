package faceless.artent.potions;

import faceless.artent.potions.network.ArtentServerHook;
import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModItems;
import faceless.artent.potions.objects.ModParticles;
import faceless.artent.potions.objects.ModRecipes;
import faceless.artent.potions.recipes.DryingRecipe;
import faceless.artent.potions.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.minecraft.data.DynamicRegistriesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtentPotions implements ModInitializer {
  public static final String MODID = "artent_potions";
  // This logger is used to write text to the console and the log file.
  // It is considered best practice to use your mod id as the logger's name.
  // That way, it's clear which mod wrote info, warnings, and errors.
  public static final Logger LOGGER = LoggerFactory.getLogger("artent_potions");

  public static final BlockEntityRegistry BlockEntities = new BlockEntityRegistry();
  public static final ItemGroupRegistry ItemGroups = new ItemGroupRegistry();
  public static final ArtentServerHook ServerHook = new ArtentServerHook();

  public static final ModItems Items = new ModItems();
  public static final ModBlocks Blocks = new ModBlocks();
  public static final EntityRegistry Entities = new EntityRegistry();
  public static final AlchemicalPotionRegistry Potions = new AlchemicalPotionRegistry();
  public static final FermentationRegistry FermentedPotions = new FermentationRegistry();
  public static final BrewingRegistry Brewing = new BrewingRegistry();
  public static final CommandRegistry Commands = new CommandRegistry();
  public static final StatusEffectsRegistry StatusEffects = new StatusEffectsRegistry();
  public static final DataComponentRegistry DataComponents = new DataComponentRegistry();
  public static final FeatureRegistry Features = new FeatureRegistry();
  public static final ArtentLootTableModifiers LootTableModifiers = new ArtentLootTableModifiers();
  public static final ModParticles Particles = new ModParticles();

  @Override
  public void onInitialize() {
    DynamicRegistries.registerSynced(ModRecipes.DRYING_RECIPES_REGISTRY_KEY, DryingRecipe.Serializer.CODEC, DryingRecipe.Serializer.CODEC);

    Potions.register();
    FermentedPotions.register();

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
