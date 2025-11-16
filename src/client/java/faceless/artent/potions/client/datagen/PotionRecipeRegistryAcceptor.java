package faceless.artent.potions.client.datagen;

import faceless.artent.potions.bootstrap.PotionRegisterContext;
import faceless.artent.potions.features.WorldGenContext;
import faceless.artent.potions.recipes.PotionEnhancementRecipe;
import faceless.artent.potions.recipes.PotionRecipe;
import net.minecraft.registry.Registerable;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.function.Consumer;

public class PotionRecipeRegistryAcceptor {
  private Registerable<PotionRecipe> recipeRegistry;
  private Registerable<PotionEnhancementRecipe> enhancementRecipeRegistry;

  private final Consumer<PotionRegisterContext> consumer;

  public PotionRecipeRegistryAcceptor(
      Consumer<PotionRegisterContext> consumer) {
    this.consumer = consumer;
  }

  public void acceptPotionRecipeRegistry(
      Registerable<PotionRecipe> recipeRegistry) {
    this.recipeRegistry = recipeRegistry;
    runIfFilled(consumer);
  }

  public void acceptPotionEnhancementRecipeRegistry(
      Registerable<PotionEnhancementRecipe> enhancementRecipeRegistry) {
    this.enhancementRecipeRegistry = enhancementRecipeRegistry;
    runIfFilled(consumer);
  }


  public void runIfFilled(
      Consumer<PotionRegisterContext> consumer) {
    if (recipeRegistry == null || enhancementRecipeRegistry == null) return;

    var ctx = new PotionRegisterContext(recipeRegistry, enhancementRecipeRegistry);
    consumer.accept(ctx);
  }
}
