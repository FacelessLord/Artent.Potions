package faceless.artent.potions.client.properties;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.item.property.numeric.NumericProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.Nullable;

import static faceless.artent.potions.registry.DataComponentRegistry.POTION_AMOUNT;

public record PotionAmountProperty(int amount) implements NumericProperty {
  public static final MapCodec<PotionAmountProperty> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance
      .group(Codecs.POSITIVE_INT.optionalFieldOf("potion_amount", 0).forGetter(PotionAmountProperty::amount))
      .apply(instance, PotionAmountProperty::new));

  public PotionAmountProperty(int amount) {
    this.amount = amount;
  }

  public float getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity holder, int seed) {
    var integer = stack.get(POTION_AMOUNT);
    return integer == null ? 0 : integer;
  }

  public MapCodec<PotionAmountProperty> getCodec() {
    return CODEC;
  }

  public int amount() {
    return this.amount;
  }
}