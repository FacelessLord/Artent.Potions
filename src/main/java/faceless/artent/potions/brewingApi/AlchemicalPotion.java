package faceless.artent.potions.brewingApi;

import com.google.common.collect.ImmutableList;
import faceless.artent.core.math.Color;
import faceless.artent.potions.ArtentPotions;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AlchemicalPotion {
    public String id;
    public Color color;
    public ArtentStatusEffect[] statusEffects;
    private final ImmutableList<StatusEffectInstance> effects;

    public AlchemicalPotion(String id, StatusEffectInstance... effects) {
        this.id = ArtentPotions.MODID + "." + id;
        this.color = Color.Red;
        this.statusEffects = getArtentStatusEffects(effects);
        this.effects = ImmutableList.copyOf(effects);
    }

    private ArtentStatusEffect[] getArtentStatusEffects(StatusEffectInstance[] effects) {
        return Arrays.stream(effects)
                     .map(StatusEffectInstance::getEffectType)
                     .filter(e -> e instanceof ArtentStatusEffect)
                     .map(e -> (ArtentStatusEffect) e)
                     .toList()
                     .toArray(ArtentStatusEffect[]::new);
    }

    public List<StatusEffectInstance> getEffects() {
        return this.effects;
    }

    public boolean hasInstantEffect() {
        if (!this.effects.isEmpty()) {
            for (StatusEffectInstance statusEffectInstance : this.effects) {
                if (!statusEffectInstance.getEffectType().value().isInstant()) continue;
                return true;
            }
        }
        return false;
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