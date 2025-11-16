package faceless.artent.potions.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import faceless.artent.potions.brewingApi.AlchemicalPotion;
import faceless.artent.potions.brewingApi.BrewingIngredient;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;

public record PotionRecipe(List<BrewingIngredient> ingredients, AlchemicalPotion potion) {
  public static final Codec<PotionRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
      Codec
          .list(BrewingIngredient.ENTRY_CODEC)
          .fieldOf("ingredients")
          .forGetter(recipe -> recipe.ingredients.stream().map(BrewingIngredient::getRegistryEntry).toList()),
      AlchemicalPotion.ENTRY_CODEC.fieldOf("result").forGetter(recipe -> recipe.potion.getRegistryEntry())).apply(
      instance,
      (ingredients, potion) -> new PotionRecipe(
          ingredients.stream().map(RegistryEntry::value).toList(),
          potion.value())));
}
