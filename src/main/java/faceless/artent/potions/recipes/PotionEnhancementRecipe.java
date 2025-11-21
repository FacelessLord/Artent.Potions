package faceless.artent.potions.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;

public record PotionEnhancementRecipe(
    AlchemicalPotion sourcePotion,
    BrewingIngredient ingredient,
    AlchemicalPotion resultPotion
) {
  public static final Codec<PotionEnhancementRecipe> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          AlchemicalPotion.ENTRY_CODEC.fieldOf("source").forGetter(recipe -> recipe.sourcePotion.getRegistryEntry()),
          BrewingIngredient.ENTRY_CODEC
              .fieldOf("ingredients")
              .forGetter(recipe -> recipe.ingredient.getRegistryEntry()),
          AlchemicalPotion.ENTRY_CODEC.fieldOf("result").forGetter(recipe -> recipe.resultPotion.getRegistryEntry()))
      .apply(instance,
             (sourceEntry, ingredientEntry, resultEntry) -> new PotionEnhancementRecipe(
                 sourceEntry.value(),
                 ingredientEntry.value(),
                 resultEntry.value())));
}
