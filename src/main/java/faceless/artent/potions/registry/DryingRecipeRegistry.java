package faceless.artent.potions.registry;

import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModItems;
import faceless.artent.potions.objects.ModRecipes;
import faceless.artent.potions.recipes.DryingRecipe;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class DryingRecipeRegistry {
  public static final RegistryKey<DryingRecipe> BROWN_MUSHROOM = RegistryKey.of(
      ModRecipes.DRYING_RECIPES_REGISTRY_KEY,
      Identifier.of(ArtentPotions.MODID, "brown_mushroom"));
  public static final RegistryKey<DryingRecipe> RED_MUSHROOM = RegistryKey.of(
      ModRecipes.DRYING_RECIPES_REGISTRY_KEY,
      Identifier.of(ArtentPotions.MODID, "red_mushroom"));
  public static final RegistryKey<DryingRecipe> SHROOM = RegistryKey.of(
      ModRecipes.DRYING_RECIPES_REGISTRY_KEY,
      Identifier.of(ArtentPotions.MODID, "shroom"));

  public static void bootstrap(Registerable<DryingRecipe> dryingRecipeRegistry) {
    dryingRecipeRegistry.register(
        BROWN_MUSHROOM, new DryingRecipe(
            Ingredient.ofItem(Items.BROWN_MUSHROOM),
            new ItemStack(ModItems.DriedBrownMushroom),
            400,
            new ItemStack(ModItems.ShroomSpores),
            0.05f));
    dryingRecipeRegistry.register(
        RED_MUSHROOM, new DryingRecipe(
            Ingredient.ofItem(Items.RED_MUSHROOM),
            new ItemStack(ModItems.DriedRedMushroom),
            400,
            new ItemStack(ModItems.ShroomSpores),
            0.05f));
    dryingRecipeRegistry.register(
        SHROOM, new DryingRecipe(
            Ingredient.ofItem(ModBlocks.ShroomItem),
            new ItemStack(ModItems.DriedShroom),
            400,
            new ItemStack(ModItems.ShroomSpores),
            0.15f));
  }
}
