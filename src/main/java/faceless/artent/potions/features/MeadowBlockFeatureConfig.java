package faceless.artent.potions.features;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;

public record MeadowBlockFeatureConfig(BlockState block, int radius, int attemptsCount) implements FeatureConfig {
  public static final Codec<MeadowBlockFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          BlockState.CODEC.fieldOf("plant_state").forGetter(MeadowBlockFeatureConfig::block),
          Codecs.POSITIVE_INT.fieldOf("radius").forGetter(MeadowBlockFeatureConfig::radius),
          Codecs.POSITIVE_INT.fieldOf("attemptsCount").forGetter(MeadowBlockFeatureConfig::attemptsCount))
      .apply(instance, MeadowBlockFeatureConfig::new));
}
