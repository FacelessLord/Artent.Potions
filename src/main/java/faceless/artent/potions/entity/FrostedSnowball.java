package faceless.artent.potions.entity;

import faceless.artent.potions.objects.ModEntities;
import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class FrostedSnowball extends SnowballEntity {
  public FrostedSnowball(
      EntityType<? extends SnowballEntity> entityType,
      World world) {
    super(entityType, world);
  }

  public FrostedSnowball(World world, double x, double y, double z, ItemStack stack) {
    super(ModEntities.FROSTED_SNOWBALL, world);
    this.setItem(stack);
    this.setPosition(x, y, z);
  }


  protected void onEntityHit(EntityHitResult entityHitResult) {
    super.onEntityHit(entityHitResult);
    var entity = entityHitResult.getEntity();
    var isFrostEntity = entity instanceof SnowGolemEntity || entity instanceof FrostedGolem;
    var damage = isFrostEntity ? 2 : 4;
    World var4 = this.getWorld();
    if (var4 instanceof ServerWorld serverWorld) {
      entity.damage(serverWorld, this.getDamageSources().thrown(this, this.getOwner()), (float) damage);
    }
    if (!isFrostEntity && entity instanceof LivingEntity living) {
      living.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.FREEZING, 40), this.getOwner());
      living.setFrozenTicks(40);
    }
  }
}
