package faceless.artent.potions.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;

public record BerryBushFeatureConfig(int berryTypeCount, int maxHeight) implements FeatureConfig {
  public static final Codec<BerryBushFeatureConfig> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
                              // you can add as many of these as you want, one for each parameter
                              Codecs.POSITIVE_INT.fieldOf("berry_type_count").forGetter(BerryBushFeatureConfig::berryTypeCount),
                              Codecs.POSITIVE_INT.fieldOf("max_height").forGetter(BerryBushFeatureConfig::maxHeight))
                          .apply(instance, BerryBushFeatureConfig::new));
}
