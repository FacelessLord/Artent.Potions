package faceless.artent.potions.features;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.gen.feature.FeatureConfig;

public record VegetationBlockFeatureConfig(BlockState block, TagKey<Block> soil) implements FeatureConfig {
  public static final Codec<VegetationBlockFeatureConfig> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
                              BlockState.CODEC.fieldOf("plant_state").forGetter(VegetationBlockFeatureConfig::block),
                              TagKey.codec(RegistryKeys.BLOCK).fieldOf("soil_tag").forGetter(VegetationBlockFeatureConfig::soil)
                                )
                          .apply(instance, VegetationBlockFeatureConfig::new));
}
