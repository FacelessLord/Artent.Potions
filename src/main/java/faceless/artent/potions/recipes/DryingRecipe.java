package faceless.artent.potions.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.Optional;

public record DryingRecipe(Ingredient source, ItemStack result, int time, ItemStack byproduct, float byproductChance) {
  public static final Codec<DryingRecipe> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Ingredient.CODEC.fieldOf("source").forGetter(recipe -> recipe.source),
          ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
          Codecs.POSITIVE_INT.fieldOf("seconds").forGetter(recipe -> recipe.time),
          ItemStack.CODEC
              .fieldOf("byproduct")
              .forGetter(recipe -> recipe.byproduct != null ? recipe.byproduct : ItemStack.EMPTY),
          Codecs.POSITIVE_FLOAT.fieldOf("byproductChance").forGetter(recipe -> recipe.byproductChance))
      .apply(instance, DryingRecipe::new));

  public boolean matches(DryingRecipeInput input, World world) {
    return Ingredient.matches(Optional.of(this.source), input.source());
  }
}
