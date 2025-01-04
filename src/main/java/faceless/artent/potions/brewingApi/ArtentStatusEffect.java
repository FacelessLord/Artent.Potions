package faceless.artent.potions.brewingApi;

import faceless.artent.core.math.Color;
import faceless.artent.potions.objects.ModPotionEffects;
import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.Heightmap.Type.MOTION_BLOCKING_NO_LEAVES;

public class ArtentStatusEffect extends StatusEffect {
  private boolean isInstant = false;

  public ArtentStatusEffect(StatusEffectCategory category, Color color) {
    super(category, color.toHex());
  }

  public ArtentStatusEffect(StatusEffectCategory category, Color color, boolean isInstant) {
    super(category, color.toHex());
    this.isInstant = isInstant;
  }

  //TODO
  public void onEffectRemoved(LivingEntity entity, int amplifier, List<StatusEffectInstance> statusEffectQueue) {
    if (!(entity instanceof ServerPlayerEntity player)) return;

    if (this == ModPotionEffects.FLIGHT && !player.isCreative()) {
      player.interactionManager.getGameMode().setAbilities(player.getAbilities());
      player.sendAbilitiesUpdate();
      player.getAbilities().allowFlying = false;
      player.sendAbilitiesUpdate();
    }
    if (this == ModPotionEffects.BERSERK) {
      onRemoved(entity.getAttributes());
      statusEffectQueue.add(new StatusEffectInstance(
          StatusEffectsRegistry.BERSERK_RECOIL,
          30 * 20,
          amplifier,
          true,
          true));
    }
  }

  @Override
  public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
    if (entity == null) return false;
    if (this == ModPotionEffects.FLIGHT && entity instanceof ServerPlayerEntity player && !player.isCreative()) {
      player.getAbilities().allowFlying = true;
      player.sendAbilitiesUpdate();
      return true;
    }
    if (this == ModPotionEffects.BERSERK) {
      super.applyUpdateEffect(world, entity, amplifier);
      return true;
    }
    if (this == ModPotionEffects.FREEZING) {
      entity.extinguish();
      return true;
    }
    if (this == ModPotionEffects.FAST_SWIMMING) {
      if (entity.isTouchingWater()) onApplied(entity.getAttributes(), amplifier);
      else onRemoved(entity.getAttributes());
      return true;
    }
    if (entity.hasStatusEffect(StatusEffectsRegistry.FEATHER_FALLING)) {
      entity.fallDistance = 0;
      return true;
    }
    return false;
  }

  public void applyInstantEffect(
      ServerWorld world,
      @Nullable Entity effectEntity,
      @Nullable Entity attacker,
      LivingEntity target,
      int amplifier,
      double proximity) {
    if (this == ModPotionEffects.SURFACE_TELEPORTATION) {
      var pos = target.getBlockPos();
      if (world == null) return;

      var topPosition = world.getTopPosition(MOTION_BLOCKING_NO_LEAVES, pos);
      var diff = topPosition.subtract(pos).getY();
      if (diff > 0 && diff < 64 * (amplifier + 1)) {
        target.teleport(topPosition.getX(), topPosition.getY() + 1, topPosition.getZ(), false);
      }
    }
  }

  public boolean canApplyUpdateEffect(int duration, int amplifier) {
    return super.canApplyUpdateEffect(duration, amplifier)
           || this == ModPotionEffects.FLIGHT
           || this == ModPotionEffects.FREEZING && duration % 10 == 0
           || this == ModPotionEffects.BERSERK && duration == 600
           || this == ModPotionEffects.FAST_SWIMMING && (duration % 10 == 0);
  }

  @Override
  public boolean isInstant() {
    return isInstant;
  }
}