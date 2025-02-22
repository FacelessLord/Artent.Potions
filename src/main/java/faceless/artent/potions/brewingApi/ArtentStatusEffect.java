package faceless.artent.potions.brewingApi;

import faceless.artent.core.math.Color;
import faceless.artent.potions.objects.ModPotionEffects;
import faceless.artent.potions.registry.DamageSourceRegistry;
import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.Heightmap.Type.MOTION_BLOCKING;

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
    if (this == ModPotionEffects.BLEEDING) {
      var damageSource = world.getDamageSources().create(DamageSourceRegistry.BleedingDamageKey);

      var damage = amplifier + 1;
      entity.damage(world, damageSource, damage);

      var vampires = world.getEntitiesByClass(
          LivingEntity.class,
          Box.enclosing(entity.getBlockPos().add(-3, -3, -3), entity.getBlockPos().add(3, 3, 3)),
          (e) -> e.hasStatusEffect(StatusEffectsRegistry.FERMENTED_VAMPIRISM));

      for (var vampire : vampires) {
        vampire.heal(damage * 0.1f);
      }

      return true;
    }
    if (this == ModPotionEffects.SATURATION) {
      if (entity instanceof PlayerEntity playerEntity && world != null) {
        playerEntity.getHungerManager().add(amplifier + 1, 1.0F);
      }
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
  }

  public boolean canApplyUpdateEffect(int duration, int amplifier) {
    return super.canApplyUpdateEffect(duration, amplifier)
           || this == ModPotionEffects.FLIGHT
           || this == ModPotionEffects.FREEZING && duration % 10 == 0
           || this == ModPotionEffects.BERSERK && duration == 600
           || this == ModPotionEffects.BLEEDING && duration % 40 == 0
           || this == ModPotionEffects.SATURATION && (duration % (80 / (amplifier + 1)) == 0);
  }

  @Override
  public boolean isInstant() {
    return isInstant;
  }
}