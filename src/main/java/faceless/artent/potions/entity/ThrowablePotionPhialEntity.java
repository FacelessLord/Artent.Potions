package faceless.artent.potions.entity;

import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.objects.ModEntities;
import faceless.artent.potions.objects.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class ThrowablePotionPhialEntity extends ThrownItemEntity {
  public static final Predicate<LivingEntity> WATER_HURTS = LivingEntity::hurtByWater;

  public ThrowablePotionPhialEntity(EntityType<? extends ThrowablePotionPhialEntity> entityType, World world) {
    super(entityType, world);
  }

  public ThrowablePotionPhialEntity(World world, LivingEntity owner, ItemStack phialStack) {
    super(ModEntities.POTION_PHIAL, owner, world, phialStack);
  }

  public ThrowablePotionPhialEntity(World world, double x, double y, double z, ItemStack phialStack) {
    super(ModEntities.POTION_PHIAL, x, y, z, world, phialStack);
  }

  @Override
  protected Item getDefaultItem() {
    return ModItems.PotionPhialExplosive;
  }

  @Override
  protected double getGravity() {
    return 0.05f;
  }

  @Override
  protected void onBlockHit(BlockHitResult blockHitResult) {
    super.onBlockHit(blockHitResult);
  }

  @Override
  protected void onCollision(HitResult hitResult) {
    super.onCollision(hitResult);
    if (this.getWorld().isClient || !(this.getWorld() instanceof ServerWorld serverWorld)) {
      return;
    }
    ItemStack itemStack = this.getStack();
    var potion = AlchemicalPotionUtil.getPotion(itemStack);
    List<StatusEffectInstance> list = AlchemicalPotionUtil.getPotionEffects(itemStack);
    if (list.isEmpty()) {
      this.damageEntitiesHurtByWater(serverWorld);
    } else {
      this.applySplashPotion(serverWorld, list,
                             hitResult.getType() ==
                                 HitResult.Type.ENTITY ? ((EntityHitResult) hitResult).getEntity() : null);
    }
    int i = potion.hasInstantEffect() ? WorldEvents.INSTANT_SPLASH_POTION_SPLASHED : WorldEvents.SPLASH_POTION_SPLASHED;
    this.getWorld().syncWorldEvent(i, this.getBlockPos(), AlchemicalPotionUtil.getColor(itemStack));
    this.discard();
  }

  private void damageEntitiesHurtByWater(ServerWorld serverWorld) {
    Box box = this.getBoundingBox().expand(4.0, 2.0, 4.0);
    List<LivingEntity> list = this.getWorld().getEntitiesByClass(LivingEntity.class, box, WATER_HURTS);
    if (!list.isEmpty()) {
      for (LivingEntity livingEntity : list) {
        double d = this.squaredDistanceTo(livingEntity);
        if (!(d < 16.0) || !livingEntity.hurtByWater()) continue;
        livingEntity.damage(serverWorld, livingEntity.getDamageSources().magic(), 1.0f);
      }
    }
    List<AxolotlEntity> list2 = this.getWorld().getNonSpectatingEntities(AxolotlEntity.class, box);
    for (AxolotlEntity d : list2) {
      d.hydrateFromPotion();
    }
  }

  private void applySplashPotion(ServerWorld serverWorld, List<StatusEffectInstance> statusEffects, @Nullable Entity entity) {
    Box box = this.getBoundingBox().expand(4.0, 2.0, 4.0);
    List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
    if (!list.isEmpty()) {
      Entity entity2 = this.getEffectCause();
      for (LivingEntity livingEntity : list) {
        double d;
        if (!livingEntity.isAffectedBySplashPotions() || !((d = this.squaredDistanceTo(livingEntity)) < 16.0))
          continue;
        double e = 1.0 - Math.sqrt(d) / 4.0;
        if (livingEntity == entity) {
          e = 1.0;
        }
        for (StatusEffectInstance statusEffectInstance : statusEffects) {
          var statusEffect = statusEffectInstance.getEffectType();
          if (statusEffect.value().isInstant()) {
            statusEffect.value().applyInstantEffect(serverWorld, this,
                                                    this.getOwner(),
                                                    livingEntity,
                                                    statusEffectInstance.getAmplifier(),
                                                    e);
            continue;
          }
          int i = (int) (e * (double) statusEffectInstance.getDuration() + 0.5);
          if (i <= 20) continue;
          livingEntity.addStatusEffect(
              new StatusEffectInstance(
                  statusEffect,
                  i,
                  statusEffectInstance.getAmplifier(),
                  statusEffectInstance.isAmbient(),
                  statusEffectInstance.shouldShowParticles()),
              entity2);
        }
      }
    }
  }
}