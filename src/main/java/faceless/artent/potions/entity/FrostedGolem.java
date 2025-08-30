package faceless.artent.potions.entity;

import faceless.artent.potions.api.ActionQueue;
import faceless.artent.potions.block.IceCrystalCluster;
import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModParticles;
import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
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
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Collection;
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

  public FrostedGolem(EntityType<? extends SnowGolemEntity> entityType, World world) {
    super(entityType, world);
    this.instanceCache = GeckoLibUtil.createInstanceCache(this);
    this.animationController = new AnimationController<>(this, "full", 5, this::handleAnimation);
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
        .add(EntityAttributes.MAX_HEALTH, 40.0F)
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
//
//    var currentAnimation = animationController.getCurrentAnimation();
//    if(currentAnimation != null && Objects.equals(currentAnimation.animation().name(), "ground_down")) {
//      this.fallDistance = -3;
//    }

    var isFrozen = getIsFrozen();

    if (isFrozen && !this.goalSelector.getGoals().isEmpty()) {
      this.updateGoalsWhenFrozen();
    }

    if (isFrozen) {
      frozenAnimationTick();

      if (this.getWorld() instanceof ServerWorld serverWorld) {
        freezeNearbyEntities(serverWorld);

        if (serverWorld.getTime() % 20 == 1) {
          generateCrystalClusters(serverWorld);
        }
      } else {
        spawnSnowStormParticles();
      }
    } else {
      var animation = animationController.getCurrentAnimation();
      if (this.getVelocity().length() > 0.05) {
        this.triggerAnim("full", "walk");
      } else if (animation != null && Objects.equals(animation.animation().name(), "walk")) {
        this.stopTriggeredAnim("full", "walk");
      }
    }
  }

  private void spawnSnowStormParticles() {
    for (int i = 0; i < 160; i++) {
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

  private void generateCrystalClusters(ServerWorld serverWorld) {
    var angle = serverWorld.random.nextDouble() * Math.TAU;
    var range = serverWorld.random.nextInt(4) + 3;
    var x = (int) Math.floor(range * Math.sin(angle));
    var z = (int) Math.floor(range * Math.cos(angle));

    var growPos = serverWorld.getTopPosition(
        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
        new BlockPos(
            x + this.getBlockX(),
            this.getBlockY(),
            z + this.getBlockZ())).down();

    var isSnowOnTop = serverWorld.getBlockState(growPos.up()).isIn(BlockTags.SNOW);
    if (isSnowOnTop) {
      growPos = growPos.up();
    }
    var blockState = serverWorld.getBlockState(growPos);

    var canPlaceCluster = ModBlocks.IceCrystalBud_Cluster.canPlaceAt(
        ModBlocks.IceCrystalBud_Small.getDefaultState(),
        serverWorld,
        growPos);

    var targetIsCluster = blockState.getBlock() instanceof IceCrystalCluster;

    if (targetIsCluster) {
      var nextState = IceCrystalCluster.createNextStage(blockState);
      serverWorld.setBlockState(growPos, nextState);
    } else if (!isSnowOnTop) {
      serverWorld.setBlockState(growPos.up(), Blocks.SNOW.getDefaultState());
    } else if (canPlaceCluster) {
      serverWorld.setBlockState(growPos, ModBlocks.IceCrystalBud_Small.getDefaultState());
    }
  }

  private void freezeNearbyEntities(ServerWorld serverWorld) {
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

  private Boolean getIsFrozen() {
    return this.getDataTracker().get(IS_FROZEN);
  }

  private void setIsFrozen(boolean isFrozen) {
    this.getDataTracker().set(IS_FROZEN, isFrozen);
  }

  @Override
  protected void onStatusEffectApplied(StatusEffectInstance effect, @Nullable Entity source) {
    super.onStatusEffectApplied(effect, source);
    var isServer = this.getWorld() instanceof ServerWorld;
    var freezingEffectAdded = effect.getEffectType() == StatusEffectsRegistry.FREEZING;
    var isFrozen = getIsFrozen();

    if (isServer && freezingEffectAdded && !isFrozen) {
      setIsFrozen(true);
      this.markEffectsDirty();
      this.updateGoalsWhenFrozen();
    }
  }

  @Override
  protected void onStatusEffectsRemoved(Collection<StatusEffectInstance> effects) {
    super.onStatusEffectsRemoved(effects);
    var isServer = this.getWorld() instanceof ServerWorld;
    var freezingEffectRemoved = effects
        .stream()
        .anyMatch(effect -> effect.getEffectType() == StatusEffectsRegistry.FREEZING);
    var isFrozen = getIsFrozen();

    if (isServer && freezingEffectRemoved && isFrozen) {
      setIsFrozen(false);
      this.markEffectsDirty();
      this.initGoals();

      this.fallDistance = -4;
      var animation = animationController.getCurrentAnimation();
      if (animation == null || !Objects.equals(animation.animation().name(), "ground_down")) {
        this.triggerAnim("full", "ground_down");
      }
    }
  }

  private void frozenAnimationTick() {
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

  private void updateGoalsWhenFrozen() {
    this.goalSelector.clear(goal -> true);
    this.targetSelector.clear(goal -> true);
  }

  public void shootAt(LivingEntity target, float pullProgress) {
    this.animationController.stop();
    this.triggerAnim("full", "attack");
    this.actionQueue.enqueueAction(
        () -> {
          var handVec = this.getRotationVector(this.getPitch(), this.getHeadYaw()).multiply(3);
          var snowballPos = new Vec3d(this.getX() + handVec.getX(), this.getEyeY(), this.getZ() + handVec.getZ());
          var d = target.getX() - snowballPos.getX();
          var e = target.getEyeY() - snowballPos.getY();
          var f = target.getZ() - snowballPos.getZ();

          var pitch = (float) -(Math.atan2(e, Math.hypot(d, f)) * 180 / Math.PI);
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
            ProjectileEntity.spawn(snowball, serverWorld, itemStack);
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
    setIsFrozen(nbt.getBoolean("isFrozen"));
  }

  @Override
  public void writeCustomDataToNbt(NbtCompound nbt) {
    super.writeCustomDataToNbt(nbt);
    nbt.putBoolean("isFrozen", getIsFrozen());
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
                                .triggerableAnim("flight", FLIGHT)
                                .triggerableAnim("ground_down", GROUND_DOWN));
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return this.instanceCache;
  }
}
