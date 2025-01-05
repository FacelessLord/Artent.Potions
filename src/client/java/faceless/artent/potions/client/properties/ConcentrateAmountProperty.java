package faceless.artent.potions.client.properties;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.item.property.numeric.NumericProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.Nullable;

import static faceless.artent.potions.registry.DataComponentRegistry.CONCENTRATE_AMOUNT;

public record ConcentrateAmountProperty(int amount) implements NumericProperty {
  public static final MapCodec<ConcentrateAmountProperty> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance
      .group(Codecs.POSITIVE_INT.optionalFieldOf("concentrate_amount", 0).forGetter(ConcentrateAmountProperty::amount))
      .apply(instance, ConcentrateAmountProperty::new));

  public ConcentrateAmountProperty(int amount) {
    this.amount = amount;
  }

  public float getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity holder, int seed) {
    var integer = stack.get(CONCENTRATE_AMOUNT);
    return integer == null ? 0 : integer;
  }

  public MapCodec<ConcentrateAmountProperty> getCodec() {
    return CODEC;
  }

  public int amount() {
    return this.amount;
  }
}