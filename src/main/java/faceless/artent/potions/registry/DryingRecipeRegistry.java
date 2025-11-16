package faceless.artent.potions.registry;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModItems;
import faceless.artent.potions.objects.ModRegistries;
import faceless.artent.potions.recipes.DryingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class DryingRecipeRegistry {
  public static final RegistryKey<DryingRecipe> BROWN_MUSHROOM = RegistryKey.of(
      ModRegistries.DRYING_RECIPES_REGISTRY_KEY,
      Identifier.of(ArtentPotions.MODID, "brown_mushroom"));
  public static final RegistryKey<DryingRecipe> RED_MUSHROOM = RegistryKey.of(
      ModRegistries.DRYING_RECIPES_REGISTRY_KEY,
      Identifier.of(ArtentPotions.MODID, "red_mushroom"));
  public static final RegistryKey<DryingRecipe> SHROOM = RegistryKey.of(
      ModRegistries.DRYING_RECIPES_REGISTRY_KEY,
      Identifier.of(ArtentPotions.MODID, "shroom"));

  public static void bootstrap(Registerable<DryingRecipe> dryingRecipeRegistry) {
    dryingRecipeRegistry.register(
        BROWN_MUSHROOM, new DryingRecipe(
            Ingredient.ofItem(Items.BROWN_MUSHROOM),
            new ItemStack(ModItems.DRIED_BROWN_MUSHROOM),
            400,
            new ItemStack(ModItems.SHROOM_SPORES),
            0.05f));
    dryingRecipeRegistry.register(
        RED_MUSHROOM, new DryingRecipe(
            Ingredient.ofItem(Items.RED_MUSHROOM),
            new ItemStack(ModItems.DRIED_RED_MUSHROOM),
            400,
            new ItemStack(ModItems.SHROOM_SPORES),
            0.05f));
    dryingRecipeRegistry.register(
        SHROOM, new DryingRecipe(
            Ingredient.ofItem(ModBlocks.SHROOM.item()),
            new ItemStack(ModItems.DRIED_SHROOM),
            400,
            new ItemStack(ModItems.SHROOM_SPORES),
            0.15f));
  }
}
