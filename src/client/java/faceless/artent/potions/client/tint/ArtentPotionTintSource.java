package faceless.artent.potions.client.tint;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import faceless.artent.core.math.Color;
import faceless.artent.potions.api.IPotionContainerItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record ArtentPotionTintSource(int defaultColor) implements TintSource {
  public static final MapCodec<ArtentPotionTintSource> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance
          .group(Codecs.RGB.fieldOf("default").forGetter(ArtentPotionTintSource::defaultColor))
          .apply(instance, ArtentPotionTintSource::new)
                                                                                          );

  public ArtentPotionTintSource() {
    this(-13083194);
  }

  @Override
  public int getTint(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user) {
    if (stack.getItem() instanceof IPotionContainerItem potionBottle) {
      if (!potionBottle.hasPotion(stack))
        return ColorHelper.zeroAlpha(defaultColor);
      var potions = potionBottle.getPotions(stack);
      return potions
          .stream()
          .map((potion) -> potion.color)
          .reduce(Color::add)
          .map(Color::asInt)
          .map(ColorHelper::fullAlpha)
          .orElse(defaultColor);
    }
    return ColorHelper.zeroAlpha(defaultColor);
  }

  @Override
  public MapCodec<ArtentPotionTintSource> getCodec() {
    return CODEC;
  }
}
