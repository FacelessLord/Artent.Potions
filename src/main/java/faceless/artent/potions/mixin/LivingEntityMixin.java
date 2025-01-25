package faceless.artent.potions.mixin;

import faceless.artent.potions.brewingApi.ArtentStatusEffect;
import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
  // No need to serialize this value
  @Unique
  private final List<StatusEffectInstance> statusEffectQueue = new ArrayList<>();

  @Inject(at = @At("HEAD"), method = "tickStatusEffects")
  protected void tickStatusEffects(CallbackInfo ci) {
    var living = get();
    if (living.getWorld() != null && !living.getWorld().isClient) {
      for (StatusEffectInstance effect : statusEffectQueue) {
        living.addStatusEffect(effect);
      }
      statusEffectQueue.clear();
    }
  }

  @Inject(at = @At("HEAD"), method = "onStatusEffectsRemoved")
  protected void onStatusEffectRemoved(Collection<StatusEffectInstance> effects, CallbackInfo ci) {
    var living = get();
    if (!living.getWorld().isClient) {
      for (var effect : effects) {
        var type = effect.getEffectType();
        if (type instanceof ArtentStatusEffect artentStatusEffect) {
          artentStatusEffect.onEffectRemoved(living, effect.getAmplifier(), statusEffectQueue);
          this.updateAttributes();
        }
      }
    }
  }

  @Inject(at = @At("HEAD"), method = "onStatusEffectUpgraded")
  protected void onStatusEffectUpgraded(
      StatusEffectInstance effect, boolean reapplyEffect, @Nullable Entity source, CallbackInfo ci) {
    var living = get();
    if (reapplyEffect && !living.getWorld().isClient) {
      var type = effect.getEffectType();
      if (!(type instanceof ArtentStatusEffect artentStatusEffect)) {
        return;
      }
      artentStatusEffect.onEffectRemoved(living, effect.getAmplifier(), statusEffectQueue);
      this.updateAttributes();
    }
  }


  @Inject(at = @At("HEAD"), method = "applyDamage")
  protected void applyDamage(ServerWorld world, DamageSource source, float amount, CallbackInfo ci) {
    if (source.getAttacker() instanceof LivingEntity attacker) {
      var target = this.get();
      if (source.isDirect()) {
        if (!target.getType().isIn(EntityTypeTags.UNDEAD)) {
          var vampirism = attacker.getStatusEffect(StatusEffectsRegistry.VAMPIRISM);
          if (vampirism != null) attacker.heal(amount * (vampirism.getAmplifier() + 1) / 10);
        } else {
          var holyWater = attacker.getStatusEffect(StatusEffectsRegistry.HOLY_WATER);
          if (holyWater != null && !source.isOf(DamageTypes.INDIRECT_MAGIC) && !source.isOf(DamageTypes.ON_FIRE)) {
            target.timeUntilRegen = 5;
            target.damage(
                world,
                world.getDamageSources().create(DamageTypes.INDIRECT_MAGIC, attacker),
                amount * (holyWater.getAmplifier() + 1) / 10);
            target.timeUntilRegen = 25;
            this.lastDamageTaken = amount;
          }
        }
        var liquidFlame = attacker.getStatusEffect(StatusEffectsRegistry.LIQUID_FLAME);
        if (liquidFlame != null && !source.isOf(DamageTypes.ON_FIRE) && !source.isOf(DamageTypes.INDIRECT_MAGIC)) {
          target.timeUntilRegen = 5;
          target.damage(
              world,
              world.getDamageSources().create(DamageTypes.ON_FIRE, attacker),
              amount * (liquidFlame.getAmplifier() + 1) / 10);
          target.setOnFireForTicks((liquidFlame.getAmplifier() + 1) * 10);
          target.timeUntilRegen = 20;
          this.lastDamageTaken = amount;
        }
      }
    }
  }

  @Shadow
  protected float lastDamageTaken;

  @Shadow
  private void updateAttributes() {
  }

  @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
  public void damage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
    var target = this.get();
    if (source.isOf(DamageTypes.ON_FIRE) || source.isOf(DamageTypes.IN_FIRE)) {
      if (target.hasStatusEffect(StatusEffectsRegistry.FERMENTED_LIQUID_FLAME)) {
        var fermentedLiquidFlame = target.getStatusEffect(StatusEffectsRegistry.FERMENTED_LIQUID_FLAME);
        if (fermentedLiquidFlame != null) {
          if (target.timeUntilRegen < 10) {
              target.heal(amount);
            target.timeUntilRegen = 20;
          }
          cir.setReturnValue(false);
          cir.cancel();
        }
      }
    }
  }

  @Unique
  private LivingEntity get() {
    return (LivingEntity) (Object) this;
  }
}
