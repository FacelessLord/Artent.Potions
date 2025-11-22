package faceless.artent.potions.brewingApi;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import faceless.artent.core.math.Color;
import faceless.artent.potions.api.ObjectWithIdentifier;
import faceless.artent.potions.api.RegistryIdentifierCodec;
import faceless.artent.potions.objects.ModRegistries;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Objects;

public class AlchemicalPotion implements ObjectWithIdentifier {
  public static Codec<AlchemicalPotion> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Color.CODEC.fieldOf("color").forGetter(potion -> potion.color),
          Codec.list(StatusEffectInstance.CODEC).fieldOf("effects").forGetter(potion -> potion.effects))
      .apply(instance, AlchemicalPotion::new));

  public static final Codec<AlchemicalPotion> ENTRY_CODEC = RegistryIdentifierCodec.of(
      ModRegistries.POTION_EFFECTS_REGISTRY_KEY,
      CODEC);

  public Color color;
  public final ArtentStatusEffect[] statusEffects;
  private final ImmutableList<StatusEffectInstance> effects;

  // This thing isn't involved in CODEC, so you can set it after register
  private Identifier id;

  public void setId(Identifier id) {
    this.id = id;
  }

  public String getId() {
    return id.toString();
  }

  @Override
  public Identifier getIdentifier() {
    return this.id;
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
    return "AlchemicalPotion(" + this.getId() + ')';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AlchemicalPotion that = (AlchemicalPotion) o;
    return this.getId().equals(that.getId()) && color.equals(that.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), color);
  }
}