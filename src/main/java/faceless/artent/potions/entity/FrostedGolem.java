package faceless.artent.potions.entity;

import faceless.artent.potions.api.ActionQueue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FrostedGolem extends SnowGolemEntity implements GeoEntity {
  private static final RawAnimation LEVITATION = RawAnimation.begin().thenLoop("levitation");
  private static final RawAnimation TAKE_OFF = RawAnimation.begin().thenPlay("take_off");
  private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("attack");
  private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
  private static final RawAnimation GROUND_DOWN = RawAnimation.begin().thenPlay("ground_down");
  private static final TrackedData<Boolean> IS_FROZEN;
  private final AnimatableInstanceCache instanceCache;
  private final AnimationController<FrostedGolem> animationController;
  private final ActionQueue actionQueue;


  public FrostedGolem(
      EntityType<? extends SnowGolemEntity> entityType,
      World world) {
    super(entityType, world);
    this.instanceCache = GeckoLibUtil.createInstanceCache(this);
    this.animationController = new AnimationController<>(
        this,
        "animation_controller",
        5,
        this::handleAnimation);
    this.actionQueue = new ActionQueue(this.age);
  }

  private PlayState handleAnimation(AnimationState<FrostedGolem> state) {
    if (!state.isMoving()) {
      state.getController().forceAnimationReset();
    }
    return state.isMoving() ? PlayState.CONTINUE : PlayState.STOP;
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
    this.goalSelector.add(1, new ProjectileAttackGoal(this, 1.25F, 40, 20.0F));
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
  public void tick() {
    super.tick();
    this.actionQueue.tickQueue();
  }

  public void shootAt(LivingEntity target, float pullProgress) {
    this.animationController.stop();
    this.triggerAnim("animation_controller", "attack");
    this.actionQueue.enqueueAction(
        () -> {
          var handVec = this.getRotationVector(this.getPitch(), this.getHeadYaw()).multiply(3);
          var snowballPos = new Vec3d(
              this.getX() + handVec.getX(),
              this.getEyeY(),
              this.getZ() + handVec.getZ());
          var d = target.getX() - snowballPos.getX();
          var e = target.getEyeY() - snowballPos.getY();
          var f = target.getZ() - snowballPos.getZ();

          var pitch = (float) -(Math.atan2(e, Math.hypot(d, f)) * 180 /
                                Math.PI);
          var yaw = (float) -(Math.atan2(f, -d) * 180 / Math.PI - 90);

          this.setYaw(yaw);
          this.setPitch(pitch);


          var world = this.getWorld();
          if (world instanceof ServerWorld serverWorld) {
            var itemStack = new ItemStack(Items.SNOWBALL);
            var snowball = new SnowballEntity(
                serverWorld,
                snowballPos.getX(),
                snowballPos.getY(),
                snowballPos.getZ(),
                itemStack);
            snowball.setOwner(this);
            snowball.setVelocity(this.getRotationVector().multiply(2));
            ProjectileEntity.spawn(
                snowball,
                serverWorld,
                itemStack);
          }

          this.playSound(
              SoundEvents.ENTITY_SNOW_GOLEM_SHOOT,
              1.0F,
              0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        }, 10);
  }

  @Override
  public void setHeadYaw(float headYaw) {
    super.setHeadYaw(headYaw);
    this.setYaw(headYaw);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    controllerRegistrar.add(animationController.triggerableAnim("attack", ATTACK));
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return this.instanceCache;
  }
}
