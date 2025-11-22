package faceless.artent.potions.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import net.minecraft.util.dynamic.Codecs;

public record FermentationRecipe(AlchemicalPotion source, AlchemicalPotion result, int seconds) {
  public static final Codec<FermentationRecipe> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          AlchemicalPotion.ENTRY_CODEC.fieldOf("source").forGetter(recipe -> recipe.source),
          AlchemicalPotion.ENTRY_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
          Codecs.NON_NEGATIVE_INT.fieldOf("seconds").forGetter(FermentationRecipe::seconds))
      .apply(instance, FermentationRecipe::new));
}

