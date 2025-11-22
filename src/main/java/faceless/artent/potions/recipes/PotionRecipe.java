package faceless.artent.potions.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;

import java.util.List;

public record PotionRecipe(List<BrewingIngredient> ingredients, AlchemicalPotion potion) {
  public static final Codec<PotionRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
      Codec
          .list(BrewingIngredient.ENTRY_CODEC)
          .fieldOf("ingredients")
          .forGetter(recipe -> recipe.ingredients),
      AlchemicalPotion.ENTRY_CODEC.fieldOf("result").forGetter(recipe -> recipe.potion)).apply(
      instance,
      PotionRecipe::new));
}
