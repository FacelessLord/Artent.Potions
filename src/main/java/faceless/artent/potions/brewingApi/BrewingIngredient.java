package faceless.artent.potions.brewingApi;

import faceless.artent.core.math.Color;
import net.minecraft.item.Item;

import java.util.Objects;

public record BrewingIngredient(String id, Item item, Color color) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrewingIngredient that = (BrewingIngredient) o;
        return item.equals(that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }
}