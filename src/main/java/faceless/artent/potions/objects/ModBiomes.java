package faceless.artent.potions.objects;

import faceless.artent.core.math.Color;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.registry.FeatureRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModBiomes {

  public static final Identifier CRIMSON_FOREST_ID = Identifier.of(ArtentPotions.MODID, "crimson_forest");
  public static final RegistryKey<Biome> CRIMSON_FOREST_BIOME_KEY = RegistryKey.of(RegistryKeys.BIOME, ModBiomes.CRIMSON_FOREST_ID);

  public static Biome createCrimsonForest(
      RegistryEntryLookup<PlacedFeature> featureLookup,
      RegistryEntryLookup<ConfiguredCarver<?>> carverLookup) {
    var crimsonColor = new Color(185, 0, 0).asInt();
    GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(
        featureLookup,
        carverLookup);
    addBasicFeatures(lookupBackedBuilder);

    var musicSound = MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_FOREST);
    DefaultBiomeFeatures.addForestFlowers(lookupBackedBuilder);
    DefaultBiomeFeatures.addDefaultOres(lookupBackedBuilder);
    DefaultBiomeFeatures.addDefaultDisks(lookupBackedBuilder);

    lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, FeatureRegistry.CRIMSON_TREES_PLACED_KEY);

    DefaultBiomeFeatures.addDefaultFlowers(lookupBackedBuilder);
    DefaultBiomeFeatures.addForestGrass(lookupBackedBuilder);
    DefaultBiomeFeatures.addDefaultVegetation(lookupBackedBuilder);

    SpawnSettings.Builder spawnSettingsBuilder = new SpawnSettings.Builder();
    DefaultBiomeFeatures.addFarmAnimals(spawnSettingsBuilder);
    DefaultBiomeFeatures.addBatsAndMonsters(spawnSettingsBuilder);

    spawnSettingsBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 5, 4, 4));

    BiomeEffects.Builder biomeEffectsBuilder = new BiomeEffects.Builder()
        .waterColor(4159204)
        .waterFogColor(329011)
        .fogColor(12638463)
        .skyColor(OverworldBiomeCreator.getSkyColor(0.7F))
        .moodSound(BiomeMoodSound.CAVE)
        .music(musicSound)
        .grassColor(crimsonColor)
        .foliageColor(crimsonColor);

    return new Biome.Builder()
        .precipitation(false)
        .temperature(0.7f)
        .downfall(0.8f)
        .effects(biomeEffectsBuilder.build())
        .spawnSettings(spawnSettingsBuilder.build())
        .generationSettings(lookupBackedBuilder.build())
        .build();
  }

  // TODO check when updated
  public static void addBasicFeatures(GenerationSettings.LookupBackedBuilder generationSettings) {
    DefaultBiomeFeatures.addLandCarvers(generationSettings);
    DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
    DefaultBiomeFeatures.addDungeons(generationSettings);
    DefaultBiomeFeatures.addMineables(generationSettings);
    DefaultBiomeFeatures.addSprings(generationSettings);
    DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
  }
}
