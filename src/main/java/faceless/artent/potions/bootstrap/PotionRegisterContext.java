package faceless.artent.potions.bootstrap;

import faceless.artent.potions.recipes.PotionEnhancementRecipe;
import faceless.artent.potions.recipes.PotionRecipe;
import net.minecraft.registry.Registerable;

public record PotionRegisterContext(
    Registerable<PotionRecipe> recipeRegistry, Registerable<PotionEnhancementRecipe> enhancementRecipeRegistry
) {
}
