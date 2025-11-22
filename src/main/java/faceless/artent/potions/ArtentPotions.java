package faceless.artent.potions;

import com.mojang.serialization.Lifecycle;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import faceless.artent.potions.network.ArtentServerHook;
import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModItems;
import faceless.artent.potions.objects.ModParticles;
import faceless.artent.potions.objects.ModRegistries;
import faceless.artent.potions.recipes.DryingRecipe;
import faceless.artent.potions.recipes.FermentationRecipe;
import faceless.artent.potions.recipes.PotionEnhancementRecipe;
import faceless.artent.potions.recipes.PotionRecipe;
import faceless.artent.potions.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.*;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
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
  public static final CommandRegistry Commands = new CommandRegistry();
  public static final StatusEffectsRegistry StatusEffects = new StatusEffectsRegistry();
  public static final DataComponentRegistry DataComponents = new DataComponentRegistry();
  public static final FeatureRegistry Features = new FeatureRegistry();
  public static final ArtentLootTableModifiers LootTableModifiers = new ArtentLootTableModifiers();
  public static final ModParticles Particles = new ModParticles();

  public static Registry<BrewingIngredient> INGREDIENT_REGISTRY;
  public static Registry<AlchemicalPotion> POTION_REGISTRY;

  @Override
  public void onInitialize() {
    DynamicRegistries.registerSynced(
        ModRegistries.POTION_FERMENTATION_RECIPE_REGISTRY_KEY,
        FermentationRecipe.CODEC,
        FermentationRecipe.CODEC);
    DynamicRegistries.registerSynced(
        ModRegistries.POTION_ENHANCEMENT_RECIPE_REGISTRY_KEY,
        PotionEnhancementRecipe.CODEC,
        PotionEnhancementRecipe.CODEC);
    DynamicRegistries.registerSynced(
        ModRegistries.POTION_RECIPES_REGISTRY_KEY,
        PotionRecipe.CODEC,
        PotionRecipe.CODEC);
    DynamicRegistries.registerSynced(
        ModRegistries.POTION_INGREDIENT_REGISTRY_KEY,
        BrewingIngredient.CODEC,
        BrewingIngredient.CODEC);
    DynamicRegistries.registerSynced(
        ModRegistries.POTION_EFFECTS_REGISTRY_KEY,
        AlchemicalPotion.CODEC,
        AlchemicalPotion.CODEC);
    DynamicRegistries.registerSynced(
        ModRegistries.DRYING_RECIPES_REGISTRY_KEY,
        DryingRecipe.CODEC,
        DryingRecipe.CODEC);

    INGREDIENT_REGISTRY = FabricRegistryBuilder
        .from(new SimpleRegistry<>(ModRegistries.POTION_INGREDIENT_REGISTRY_KEY, Lifecycle.stable(), true))
        .attribute(
            RegistryAttribute.SYNCED)
        .buildAndRegister();
    POTION_REGISTRY = FabricRegistryBuilder
        .from(new SimpleRegistry<>(ModRegistries.POTION_EFFECTS_REGISTRY_KEY, Lifecycle.stable(), true)).attribute(
            RegistryAttribute.SYNCED).buildAndRegister();

    DynamicRegistrySetupCallback.EVENT.register((DynamicRegistryView registryView) -> {
      registryView.registerEntryAdded(
          ModRegistries.POTION_INGREDIENT_REGISTRY_KEY, (rawId, identifier, object) -> {
            LOGGER.info("ingredient: {}", identifier.toString());
            object.setId(identifier);
          });
      registryView.registerEntryAdded(
          ModRegistries.POTION_EFFECTS_REGISTRY_KEY, (rawId, identifier, object) -> {
            LOGGER.info("potion: {}", identifier.toString());
            object.setId(identifier);
          });
    });

    Blocks.register();
    Items.register();
    StatusEffects.register();
    BlockEntities.register();
    Entities.register();
    Features.register();
    Particles.register();

    LootTableModifiers.modifyLootTables();

    Commands.register();
    ServerHook.load();
    ItemGroups.register();
  }
}
