package faceless.artent.potions.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record DryingRecipeInput(ItemStack source) implements RecipeInput {
  @Override
  public ItemStack getStackInSlot(int slot) {
    if(slot == 0)
      return source;
    throw new IllegalArgumentException("Recipe does not contain slot " + slot);
  }

  @Override
  public int size() {
    return 1;
  }
}
