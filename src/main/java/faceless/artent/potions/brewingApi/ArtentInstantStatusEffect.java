package faceless.artent.potions.brewingApi;

import faceless.artent.core.math.Color;
import faceless.artent.core.math.VectorUtils;
import faceless.artent.potions.objects.ModPotionEffects;
import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.TeleportTarget;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static net.minecraft.world.Heightmap.Type.MOTION_BLOCKING;

public class ArtentInstantStatusEffect extends StatusEffect {
  public ArtentInstantStatusEffect(StatusEffectCategory category, Color color) {
    super(category, color.toHex());
  }

  @Override
  public void applyInstantEffect(
      ServerWorld world,
      @Nullable Entity effectEntity,
      @Nullable Entity attacker,
      LivingEntity target,
      int amplifier,
      double proximity) {
    if (this == ModPotionEffects.LIQUID_MEAT) {
      if (target instanceof PlayerEntity player) player.getHungerManager().add(4 * (amplifier + 1), 5);
    }
    if (this == ModPotionEffects.BLINK) {
      var entityDirection = VectorUtils.vectorFromEulerAngles(target.getHeadYaw(), target.getPitch());
      var blinkVector = entityDirection.mult(16 * (amplifier + 1));
      var resultPos = target.getPos().add(blinkVector.toVec3d());
      target.teleportTo(new TeleportTarget(
          world,
          resultPos,
          target.getVelocity(),
          0.0f,
          0.0f,
          PositionFlag.combine(PositionFlag.ROT, PositionFlag.DELTA),
          TeleportTarget.NO_OP));
    }
    if (this == ModPotionEffects.SURFACE_TELEPORTATION) {
      var pos = target.getBlockPos();
      if (world == null) return;

      var topPosition = world.getTopPosition(MOTION_BLOCKING, pos);
      var diff = topPosition.subtract(pos).getY();
      if (diff > 0 && diff < 64 * (amplifier + 1)) {
        target.teleport(topPosition.getX() + 0.5f, topPosition.getY() + 1, topPosition.getZ() + 0.5f, false);
      }
    }
    if (this == ModPotionEffects.INSTANT_HEALING) {
      var damage = 8 * (amplifier + 1);
      target.heal(damage);
    }
    if (this == ModPotionEffects.CLEANSING) {
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
                                                     || key == StatusEffectsRegistry.VAMPIRE_BARON)) {

          effectsToRemove.add(effect.getKey());
        }
      }
      effectsToRemove.forEach(target::removeStatusEffect);
    }
  }

  public boolean canApplyUpdateEffect(int duration, int amplifier) {
    return super.canApplyUpdateEffect(duration, amplifier);
  }

  @Override
  public boolean isInstant() {
    return true;
  }
}