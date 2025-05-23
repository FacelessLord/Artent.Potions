package faceless.artent.potions.brewingApi;

import faceless.artent.core.math.Color;
import faceless.artent.potions.objects.ModPotionEffects;
import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ConcentrateStatusEffect extends StatusEffect {

  private boolean isInstant = true;

  public ConcentrateStatusEffect(StatusEffectCategory category, Color color) {
    super(category, color.toHex());
  }

  public ConcentrateStatusEffect(StatusEffectCategory category, Color color, boolean isInstant) {
    super(category, color.toHex());
    this.isInstant = isInstant;
  }

  @Override
  public void applyInstantEffect(
      ServerWorld world,
      @Nullable Entity effectEntity,
      @Nullable Entity attacker,
      LivingEntity target,
      int amplifier,
      double proximity) {
    if (this == ModPotionEffects.FERMENTED_SATURATION) {
      if (target instanceof PlayerEntity player) player.getHungerManager().add(20 * (amplifier + 1), 5);
    }
    if (this == ModPotionEffects.FERMENTED_HOLY_WATER) {
      var damage = ArtentStatusEffect.collectHolyWaterDamage(target, amplifier);
      if (damage > 0) {
        target.damage(world, world.getDamageSources().magic(), damage * 2);
      }

      var effectsToRemove = new ArrayList<RegistryEntry<StatusEffect>>();
      var effects = target.getActiveStatusEffects();
      for (var effect : effects.entrySet()) {
        var instance = effect.getValue();
        var key = effect.getKey();
        if (instance.getAmplifier() <= amplifier && (key.value().getCategory() == StatusEffectCategory.HARMFUL
                                                     || key == StatusEffectsRegistry.VAMPIRISM
                                                     || key == StatusEffectsRegistry.FERMENTED_VAMPIRISM)) {

          effectsToRemove.add(effect.getKey());
        }
      }
      effectsToRemove.forEach(target::removeStatusEffect);
    }
  }

  @Override
  public void onApplied(LivingEntity entity, int amplifier) {
    if (this == ModPotionEffects.FERMENTED_LIQUID_FLAME) {
      entity.setFireTicks(200 * (amplifier + 1));
    }
  }

  public boolean canApplyUpdateEffect(int duration, int amplifier) {
    return super.canApplyUpdateEffect(duration, amplifier);
  }

  @Override
  public boolean isInstant() {
    return isInstant;
  }
}