package faceless.artent.potions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.InstancedAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;

public class FrostedGolem extends SnowGolemEntity implements GeoAnimatable {
  private static final TrackedData<Boolean> IS_FROZEN;
  private final AnimatableInstanceCache instanceCache;

  public FrostedGolem(
      EntityType<? extends SnowGolemEntity> entityType,
      World world) {
    super(entityType, world);
    this.instanceCache = new InstancedAnimatableInstanceCache(this);
  }

  protected void initDataTracker(DataTracker.Builder builder) {
    super.initDataTracker(builder);
    builder.add(IS_FROZEN, false);
  }

  public static DefaultAttributeContainer.Builder createFrostedGolemAttributes() {
    return MobEntity
        .createMobAttributes()
        .add(EntityAttributes.MAX_HEALTH, 4.0F)
        .add(EntityAttributes.MOVEMENT_SPEED, 0.2F);
  }

  protected void initGoals() {
    this.goalSelector.add(1, new ProjectileAttackGoal(this, 1.25F, 20, 10.0F));
    this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0F, 1.0000001E-5F));
    this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
    this.goalSelector.add(4, new LookAroundGoal(this));
    this.targetSelector.add(
        1,
        new ActiveTargetGoal<>(
            this,
            MobEntity.class,
            10,
            true,
            false,
            (entity, serverWorld) -> !(entity instanceof FrostedGolem)));
  }

  static {
    IS_FROZEN = DataTracker.registerData(FrostedGolem.class, TrackedDataHandlerRegistry.BOOLEAN);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return this.instanceCache;
  }

  @Override
  public double getTick(Object o) {
    return 0;
  }
}
