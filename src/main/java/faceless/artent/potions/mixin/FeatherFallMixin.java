package faceless.artent.potions.mixin;

import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class FeatherFallMixin {
  @Inject(method = "tick", at = @At("RETURN"))
  private void onTick(CallbackInfo ci) {
    var living = (LivingEntity) (Object) this;

    // These effects are here because they need to be able to remove effects and effectQueue can't help here
    if (living.hasStatusEffect(StatusEffectsRegistry.ANTIDOTE)) {
      var potion = living.getStatusEffect(StatusEffectsRegistry.ANTIDOTE);
      if (potion == null) return;
      var level = potion.getAmplifier();
      var duration = potion.getDuration();
      checkAndClearPoison(living, level, duration);
    }
    if (living.hasStatusEffect(StatusEffectsRegistry.FERMENTED_ANTIDOTE)) {
      var potion = living.getStatusEffect(StatusEffectsRegistry.ANTIDOTE);
      if (potion == null) return;
      var level = potion.getAmplifier();
      checkAndClearPoison(living, level + 1, 0);
      living.removeStatusEffect(StatusEffectsRegistry.FERMENTED_ANTIDOTE);
    }
  }

  @Unique
  private void checkAndClearPoison(LivingEntity living, int level, int duration) {
    if (!living.hasStatusEffect(StatusEffects.POISON) || duration % 15 != 0) return;
    var poison = living.getStatusEffect(StatusEffects.POISON);
    if (poison != null && poison.getAmplifier() <= level) {
      living.removeStatusEffect(StatusEffects.POISON);
    }
  }
}