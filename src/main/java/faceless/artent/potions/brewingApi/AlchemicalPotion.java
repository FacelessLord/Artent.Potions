package faceless.artent.potions.brewingApi;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import faceless.artent.core.math.Color;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModRegistries;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;
import java.util.Objects;

public class AlchemicalPotion {
  public static Codec<AlchemicalPotion> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Color.CODEC.fieldOf("color").forGetter(potion -> potion.color),
          Codec.list(StatusEffectInstance.CODEC).fieldOf("effects").forGetter(potion -> potion.effects))
      .apply(instance, AlchemicalPotion::new));

  public static final Codec<RegistryEntry<AlchemicalPotion>> ENTRY_CODEC = RegistryElementCodec.of(
      ModRegistries.POTION_EFFECTS_REGISTRY_KEY,
      CODEC);

  @Deprecated
  public String id;
  public Color color;
  public final ArtentStatusEffect[] statusEffects;
  private final ImmutableList<StatusEffectInstance> effects;

  // This thing isn't involved in CODEC, so you can set it after register
  private RegistryEntry<AlchemicalPotion> registryEntry;

  public void setRegistryEntry(RegistryEntry<AlchemicalPotion> registryEntry) {
    this.registryEntry = registryEntry;
  }

  public RegistryEntry<AlchemicalPotion> getRegistryEntry() {
    return this.registryEntry;
  }

  public AlchemicalPotion(Color color, List<StatusEffectInstance> effects) {
    this.color = color;
    this.statusEffects = getArtentStatusEffects(effects);
    this.effects = ImmutableList.copyOf(effects);
  }

  public AlchemicalPotion(StatusEffectInstance... effects) {
    this.color = Color.Red;
    this.effects = ImmutableList.copyOf(effects);
    this.statusEffects = getArtentStatusEffects(this.effects);
  }

  @Deprecated
  public AlchemicalPotion(String id, StatusEffectInstance... effects) {
    this.id = ArtentPotions.MODID + "." + id;
    this.color = Color.Red;
    this.effects = ImmutableList.copyOf(effects);
    this.statusEffects = getArtentStatusEffects(this.effects);
  }

  private ArtentStatusEffect[] getArtentStatusEffects(List<StatusEffectInstance> effects) {
    return effects
        .stream()
        .map(StatusEffectInstance::getEffectType)
        .filter(e -> e instanceof ArtentStatusEffect)
        .map(e -> (ArtentStatusEffect) e)
        .toList()
        .toArray(ArtentStatusEffect[]::new);
  }

  public List<StatusEffectInstance> getEffects() {
    return this.effects;
  }

  @Override
  public String toString() {
    return "AlchemicalPotion(" + id + ')';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AlchemicalPotion that = (AlchemicalPotion) o;
    return id.equals(that.id) && color.equals(that.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, color);
  }
}