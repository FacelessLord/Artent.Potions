package faceless.artent.potions.mixin;

import com.mojang.datafixers.util.Pair;
import faceless.artent.potions.objects.ModBiomes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(VanillaBiomeParameters.class)
public class VanillaBiomeParametersMixin {

  @Shadow
  private void writeBiomeParameters(
      Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters,
      MultiNoiseUtil.ParameterRange temperature,
      MultiNoiseUtil.ParameterRange humidity,
      MultiNoiseUtil.ParameterRange continentalness,
      MultiNoiseUtil.ParameterRange erosion,
      MultiNoiseUtil.ParameterRange weirdness,
      float offset,
      RegistryKey<Biome> biome) {
  }

  @Shadow
  private final MultiNoiseUtil.ParameterRange[] temperatureParameters = new MultiNoiseUtil.ParameterRange[]{MultiNoiseUtil.ParameterRange.of(
      -1.0F,
      -0.45F), MultiNoiseUtil.ParameterRange.of(
      -0.45F,
      -0.15F), MultiNoiseUtil.ParameterRange.of(
      -0.15F,
      0.2F), MultiNoiseUtil.ParameterRange.of(
      0.2F,
      0.55F), MultiNoiseUtil.ParameterRange.of(
      0.55F,
      1.0F)};
  @Shadow
  private final MultiNoiseUtil.ParameterRange[] humidityParameters = new MultiNoiseUtil.ParameterRange[]{MultiNoiseUtil.ParameterRange.of(
      -1.0F,
      -0.35F), MultiNoiseUtil.ParameterRange.of(
      -0.35F,
      -0.1F), MultiNoiseUtil.ParameterRange.of(
      -0.1F,
      0.1F), MultiNoiseUtil.ParameterRange.of(
      0.1F,
      0.3F), MultiNoiseUtil.ParameterRange.of(
      0.3F,
      1.0F)};
  @Shadow
  private final MultiNoiseUtil.ParameterRange defaultParameter = MultiNoiseUtil.ParameterRange.of(-1.0F, 1.0F);

  @Shadow
  private final MultiNoiseUtil.ParameterRange[] erosionParameters = new MultiNoiseUtil.ParameterRange[]{MultiNoiseUtil.ParameterRange.of(
      -1.0F,
      -0.78F), MultiNoiseUtil.ParameterRange.of(
      -0.78F,
      -0.375F), MultiNoiseUtil.ParameterRange.of(
      -0.375F,
      -0.2225F), MultiNoiseUtil.ParameterRange.of(
      -0.2225F,
      0.05F), MultiNoiseUtil.ParameterRange.of(
      0.05F,
      0.45F), MultiNoiseUtil.ParameterRange.of(
      0.45F,
      0.55F), MultiNoiseUtil.ParameterRange.of(
      0.55F,
      1.0F)};
  @Shadow
  private final MultiNoiseUtil.ParameterRange nearInlandContinentalness = MultiNoiseUtil.ParameterRange.of(
      -0.11F,
      0.03F);
  @Shadow
  private final MultiNoiseUtil.ParameterRange midInlandContinentalness = MultiNoiseUtil.ParameterRange.of(0.03F, 0.3F);
  @Shadow
  private final MultiNoiseUtil.ParameterRange farInlandContinentalness = MultiNoiseUtil.ParameterRange.of(0.3F, 1.0F);
  @Shadow
  private final MultiNoiseUtil.ParameterRange coastContinentalness = MultiNoiseUtil.ParameterRange.of(-0.19F, -0.11F);

  @Inject(method = "writeMidBiomes", at = @At("HEAD"))
  private void writeMidBiomes(
      Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters,
      MultiNoiseUtil.ParameterRange weirdness,
      CallbackInfo ci) {
    System.out.println("Adding Artent.Potions biomes");
    // TODO biomes
//    NetherBiomes.addNetherBiome();
//    BiomeModifications.create(null).add(ModificationPhase.ADDITIONS, )
    // BootstrapContext<Biome>
    // https://github.com/FabricMC/fabric/blob/1.21.11/fabric-biome-api-v1/src/testmod/java/net/fabricmc/fabric/test/biome/TestBiomes.java
    // DATAGEN
//    @Override
//    public void buildRegistry(RegistrySetBuilder registryBuilder) {
//      registryBuilder.add(Registries.BIOME, TestBiomes::bootstrap);
//    }

    var parameterRange = this.temperatureParameters[1];
    var parameterRange2 = this.humidityParameters[2];

    this.writeBiomeParameters(
        parameters,
        parameterRange,
        parameterRange2,
        this.nearInlandContinentalness,
        this.erosionParameters[2],
        weirdness,
        0.0F,
        ModBiomes.CRIMSON_FOREST_BIOME_KEY);

    RegistryKey<Biome> registryKey = ModBiomes.CRIMSON_FOREST_BIOME_KEY;
    this.writeBiomeParameters(
        parameters,
        parameterRange,
        parameterRange2,
        this.nearInlandContinentalness,
        this.erosionParameters[2],
        weirdness,
        0.0F,
        registryKey);
    if (weirdness.max() < 0L) {
      this.writeBiomeParameters(
          parameters,
          parameterRange,
          parameterRange2,
          MultiNoiseUtil.ParameterRange.combine(
              this.nearInlandContinentalness,
              this.farInlandContinentalness),
          this.erosionParameters[4],
          weirdness,
          0.0F,
          registryKey);
    } else {
      this.writeBiomeParameters(
          parameters,
          parameterRange,
          parameterRange2,
          MultiNoiseUtil.ParameterRange.combine(
              this.coastContinentalness,
              this.farInlandContinentalness),
          this.erosionParameters[4],
          weirdness,
          0.0F,
          registryKey);
    }
  }
}
