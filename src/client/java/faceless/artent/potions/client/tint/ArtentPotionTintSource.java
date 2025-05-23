package faceless.artent.potions.client.tint;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
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
    var potion = AlchemicalPotionUtil.getPotion(stack);
    if (potion == null)
      return ColorHelper.zeroAlpha(defaultColor);

    return ColorHelper.fullAlpha(potion.color.asInt());
  }

  @Override
  public MapCodec<ArtentPotionTintSource> getCodec() {
    return CODEC;
  }
}
