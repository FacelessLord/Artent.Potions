package faceless.artent.potions.brewingApi;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import faceless.artent.core.math.Color;
import faceless.artent.potions.objects.ModRegistries;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.dynamic.Codecs;

import java.util.Objects;

public final class BrewingIngredient {
  public static final Codec<BrewingIngredient> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Item.ENTRY_CODEC.fieldOf("item").forGetter(ingredient -> ingredient.item.getRegistryEntry()),
          Codecs.POSITIVE_INT.fieldOf("meta").forGetter(ingredient -> ingredient.meta),
          Color.CODEC.fieldOf("color").forGetter(ingredient -> ingredient.color))
      .apply(instance, (item, meta, color) -> new BrewingIngredient(item.value(), meta, color)));
  public static final Codec<RegistryEntry<BrewingIngredient>> ENTRY_CODEC = RegistryElementCodec.of(
      ModRegistries.POTION_INGREDIENT_REGISTRY_KEY,
      CODEC);

  public final Item item;
  public final int meta;
  public final Color color;

  public BrewingIngredient(Item item, int meta, Color color) {
    this.item = item;
    this.meta = meta;
    this.color = color;
  }

  // This thing isn't involved in CODEC, so you can set it after register
  private RegistryEntry<BrewingIngredient> registryEntry;

  public void setRegistryEntry(RegistryEntry<BrewingIngredient> registryEntry) {
    this.registryEntry = registryEntry;
  }

  public RegistryEntry<BrewingIngredient> getRegistryEntry() {
    return this.registryEntry;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BrewingIngredient that = (BrewingIngredient) o;
    return meta == that.meta && item.equals(that.item);
  }

  @Override
  public int hashCode() {
    return Objects.hash(item, meta);
  }

  @Override
  public String toString() {
    return "BrewingIngredient[" + "item=" + item + ", " + "meta=" + meta + ", " + "color=" + color + ']';
  }
}