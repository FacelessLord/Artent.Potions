package faceless.artent.potions.entity;

import faceless.artent.potions.api.ActionQueue;
import faceless.artent.potions.objects.ModParticles;
import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;

public class FrostedGolem extends SnowGolemEntity implements GeoEntity {
  private static final RawAnimation FLIGHT = RawAnimation.begin().thenPlay("take_off").thenLoop("levitation");
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
        "full",
        5,
        this::handleAnimation);
    this.actionQueue = new ActionQueue(this.age);
  }

  private PlayState handleAnimation(AnimationState<FrostedGolem> state) {
    if (!state.isMoving()) {
      state.getController().forceAnimationReset();
    }
    var animation = state.getController().getCurrentAnimation();
    if (animation != null && Objects.equals(animation.animation().name(), "walk")) {
      state.setControllerSpeed((float) this.getVelocity().length());
    } else {
      state.setControllerSpeed(1);
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
        .add(EntityAttributes.MOVEMENT_SPEED, 0.2F)
        .add(EntityAttributes.FOLLOW_RANGE, 20F)
        .add(EntityAttributes.SAFE_FALL_DISTANCE, 5F);
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

    var isFrozen = this.getDataTracker().get(IS_FROZEN);

    if (isFrozen && !this.goalSelector.getGoals().isEmpty()) {
      this.onFrozen();
    }
    if (this.getWorld() instanceof ServerWorld serverWorld) {
      if (this.hasStatusEffect(StatusEffectsRegistry.FREEZING)) {
        if (!isFrozen) {
          this.getDataTracker().set(IS_FROZEN, true);
          this.markEffectsDirty();
          isFrozen = true;
          this.onFrozen();
        }
      } else if (isFrozen) {
        this.getDataTracker().set(IS_FROZEN, false);
        this.markEffectsDirty();
        isFrozen = false;
        this.initGoals();
      }

      if (isFrozen) {
        var entities = serverWorld.getEntitiesByClass(
            LivingEntity.class,
            Box.of(this.getBlockPos().down(6).toCenterPos(), 9, 12, 9),
            e -> e != this);
        for (var entity : entities) {
          var dist = this.getBlockPos().toCenterPos().subtract(entity.getPos()).length();
          var frozenTime = 100 - (int) (dist * dist);

          entity.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.FREEZING, frozenTime), this);
          entity.setFrozenTicks(entity.getFrozenTicks() + frozenTime / 10);
        }
      }
    } else if (isFrozen) {
      for (int i = 0; i < 40; i++) {
        var world = this.getWorld();
        var angle = world.random.nextDouble() * Math.PI * 2;
        var height = world.random.nextDouble() * 12 - 6;
        var range = Math.sqrt(world.random.nextDouble()) * 6 + 3;

        var x = this.getX() + Math.cos(angle) * range;
        var y = this.getY() + height;
        var z = this.getZ() + Math.sin(angle) * range;

        world.addParticle(
            ModParticles.SNOW_STORM,
            x,
            y,
            z,
            Math.sin(angle) * 0.1 * range,
            0.0,
            -Math.cos(angle) * 0.1 * range);
      }
    }

    if (isFrozen) {
      frozenTick();
    } else {
      var animation = animationController.getCurrentAnimation();
      if (this.getVelocity().length() > 0.05) {
        this.triggerAnim("full", "walk");
      } else if (animation != null && Objects.equals(animation.animation().name(), "walk")) {
        this.stopTriggeredAnim("full", "walk");
      }
    }
  }

  private void frozenTick() {
    var world = this.getWorld();
    var pos = this.getBlockPos();
    var under1 = world.getBlockState(pos.down());
    var under2 = world.getBlockState(pos.down().down());
    var under3 = world.getBlockState(pos.down().down().down());
    if (!under1.isAir() || !under2.isAir() || !under3.isAir()) {
      this.setVelocity(0, 0.125f, 0);
    }
    var animation = animationController.getCurrentAnimation();
    if (animation == null || !Objects.equals(animation.animation().name(), "flight")) {
      this.triggerAnim("full", "flight");
    }
  }

  private void onFrozen() {
    this.goalSelector.clear(goal -> true);
    this.targetSelector.clear(goal -> true);
  }

  public void shootAt(LivingEntity target, float pullProgress) {
    this.animationController.stop();
    this.triggerAnim("full", "attack");
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
            var snowball = new FrostedSnowball(
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
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);
  }

  @Override
  public void readCustomDataFromNbt(NbtCompound nbt) {
    super.readCustomDataFromNbt(nbt);
    this.getDataTracker().set(IS_FROZEN, nbt.getBoolean("isFrozen"));
  }

  @Override
  public void writeCustomDataToNbt(NbtCompound nbt) {
    super.writeCustomDataToNbt(nbt);
    nbt.putBoolean("isFrozen", this.getDataTracker().get(IS_FROZEN));
  }

  @Override
  public void setHeadYaw(float headYaw) {
    super.setHeadYaw(headYaw);
    this.setYaw(headYaw);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    controllerRegistrar.add(animationController
                                .triggerableAnim("attack", ATTACK)
                                .triggerableAnim("walk", WALK)
                                .triggerableAnim("flight", FLIGHT));
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return this.instanceCache;
  }
}
