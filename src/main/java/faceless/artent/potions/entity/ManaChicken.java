package faceless.artent.potions.entity;

import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ManaChicken extends ChickenEntity {
  public ManaChicken(
      EntityType<? extends ChickenEntity> entityType, World world) {
    super(entityType, world);
  }

  @Override
  protected void initGoals() {
    super.initGoals();
    this.goalSelector
        .getGoals()
        .stream()
        .filter(goal -> goal != null && goal.getGoal() instanceof TemptGoal)
        .forEach(goal -> this.goalSelector.remove(goal.getGoal()));

    this.goalSelector.add(3, new TemptGoal(this, 1.0, stack -> stack.isOf(ModBlocks.ShroomItem), false));
  }

  @Override
  public boolean isBreedingItem(ItemStack stack) {
    return stack.getItem() == ModBlocks.ShroomItem;
  }

  @Nullable
  public ChickenEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
    return ModEntities.MANA_CHICKEN.create(serverWorld, SpawnReason.BREEDING);
  }
}
