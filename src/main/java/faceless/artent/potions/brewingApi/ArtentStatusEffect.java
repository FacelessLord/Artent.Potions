package faceless.artent.potions.brewingApi;

import faceless.artent.core.math.Color;
import faceless.artent.potions.entity.FrostedGolem;
import faceless.artent.potions.objects.ModPotionEffects;
import faceless.artent.potions.registry.DamageSourceRegistry;
import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.List;

public class ArtentStatusEffect extends StatusEffect {
  public ArtentStatusEffect(StatusEffectCategory category, Color color) {
    super(category, color.toHex());
  }

  public void onEffectRemoved(LivingEntity entity, int amplifier, List<StatusEffectInstance> statusEffectQueue) {
    if (!(entity instanceof ServerPlayerEntity player)) return;

    if (this == ModPotionEffects.FLIGHT && !player.isCreative()) {
      player.getAbilities().allowFlying = false;
      player.interactionManager.getGameMode().setAbilities(player.getAbilities());
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
    if (this == ModPotionEffects.HOT_CHOCOLATE) {
      var currentFreezingTicks = entity.getFrozenTicks();
      entity.setFrozenTicks(currentFreezingTicks - (amplifier + 1) * 10);
      return true;
    }
    if (this == ModPotionEffects.BERSERK) {
      super.applyUpdateEffect(world, entity, amplifier);
      return true;
    }
    if (this == ModPotionEffects.FREEZING) {
      entity.extinguish();
      if (!(entity instanceof FrostedGolem))
        entity.setFrozenTicks(Math.min(entity.getFrozenTicks() + amplifier * 2, 20 * 10 * (1 + amplifier)));
      return true;
    }
    if (this == ModPotionEffects.SANCTITY) {
      if (entity.getStatusEffect(StatusEffectsRegistry.CLEANSING) != null) {
        return true;
      }
      var damage = collectHolyWaterDamage(entity, amplifier);
      if (damage > 0) {
        entity.damage(world, world.getDamageSources().magic(), damage);
        cutVampirismDuration(entity, 2 * 20 * damage);
      }
      return true;
    }
    if (this == ModPotionEffects.BLEEDING) {
      var damageSource = world.getDamageSources().create(DamageSourceRegistry.BleedingDamageKey);

      var damage = amplifier + 1;
      entity.damage(world, damageSource, damage);

      var vampires = world.getEntitiesByClass(
          LivingEntity.class,
          Box.enclosing(entity.getBlockPos().add(-3, -3, -3), entity.getBlockPos().add(3, 3, 3)),
          (e) -> e.hasStatusEffect(StatusEffectsRegistry.VAMPIRE_BARON));

      for (var vampire : vampires) {
        vampire.heal(damage * 0.5f);
      }

      return true;
    }
    if (this == ModPotionEffects.SATURATION) {
      if (entity instanceof PlayerEntity playerEntity && world != null) {
        var currentSaturation = playerEntity.getHungerManager().getSaturationLevel();
        if (currentSaturation < 20) {
          var saturationSpeed = 2 + amplifier;
          playerEntity.getHungerManager().setSaturationLevel(Math.min(currentSaturation + saturationSpeed, 20));
        }
        var currentFood = playerEntity.getHungerManager().getFoodLevel();
        if (currentFood < 20) {
          var foodSpeed = 2 + amplifier;
          playerEntity.getHungerManager().setFoodLevel(Math.min(currentFood + foodSpeed, 20));
        }
      }
      return true;
    }
    return false;
  }

  public static int collectHolyWaterDamage(LivingEntity entity, int amplifier) {
    var damage = 0;
    if (entity.getType().isIn(EntityTypeTags.UNDEAD)) {
      damage += 2 * (amplifier + 1);
    }
    if (entity.hasStatusEffect(StatusEffectsRegistry.VAMPIRISM)) {
      damage += 2 * (amplifier + 1);
    }
    if (entity.hasStatusEffect(StatusEffectsRegistry.VAMPIRE_BARON)) {
      damage += 4 * (amplifier + 1);
    }
    return damage;
  }

  private static void cutVampirismDuration(LivingEntity entity, int duration) {
    cutEffectDuration(entity, StatusEffectsRegistry.VAMPIRISM, duration);
    cutEffectDuration(entity, StatusEffectsRegistry.VAMPIRE_BARON, duration);
  }

  private static void cutEffectDuration(
      LivingEntity entity,
      RegistryEntry.Reference<StatusEffect> effectType,
      int duration) {
    var effect = entity.getStatusEffect(effectType);
    if (effect == null) return;

    var newEffect = new StatusEffectInstance(
        StatusEffectsRegistry.VAMPIRISM,
        effect.getDuration() - duration, effect.getAmplifier());

    entity.removeStatusEffect(StatusEffectsRegistry.VAMPIRISM);
    entity.addStatusEffect(newEffect);
  }

  @Override
  public void onApplied(LivingEntity entity, int amplifier) {
    if (this == ModPotionEffects.FLAMING_SOUL) {
      entity.setFireTicks(200 * (amplifier + 1));
    }
    if (this == ModPotionEffects.HOT_CHOCOLATE) {
      var currentFreezingTicks = entity.getFrozenTicks();
      entity.setFrozenTicks(currentFreezingTicks - (amplifier + 1) * 100);
    }
  }

  public boolean canApplyUpdateEffect(int duration, int amplifier) {
    return super.canApplyUpdateEffect(duration, amplifier)
           || this == ModPotionEffects.FLIGHT
           || this == ModPotionEffects.HOT_CHOCOLATE
           || this == ModPotionEffects.FREEZING
           || this == ModPotionEffects.BERSERK && duration == 600
           || this == ModPotionEffects.BLEEDING && duration % 40 == 0
           || this == ModPotionEffects.SANCTITY && duration % 40 == 0
           || this == ModPotionEffects.SATURATION && (duration % (80 / (amplifier + 1)) == 0);
  }
}