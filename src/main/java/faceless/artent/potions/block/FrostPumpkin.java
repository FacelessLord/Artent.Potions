package faceless.artent.potions.block;

import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class FrostPumpkin extends PumpkinBlock {
  public FrostPumpkin(Settings settings) {
    super(settings);
  }

  @Override
  protected ActionResult onUseWithItem(
      ItemStack stack,
      BlockState state,
      World world,
      BlockPos pos,
      PlayerEntity player,
      Hand hand,
      BlockHitResult hit) {
    if (!stack.isOf(Items.SHEARS)) {
      return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
    if (world.isClient) {
      return ActionResult.SUCCESS;
    }
    Direction direction = hit.getSide();
    Direction direction2 = direction.getAxis() == Direction.Axis.Y
        ? player.getHorizontalFacing().getOpposite()
        : direction;
    world.playSound(null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0f, 1.0f);
    world.setBlockState(
        pos,
        ModBlocks.FrostPumpkinCarved.block().getDefaultState().with(CarvedPumpkinBlock.FACING, direction2),
        Block.NOTIFY_ALL_AND_REDRAW);
    ItemEntity itemEntity = new ItemEntity(
        world,
        (double) pos.getX() + 0.5 + (double) direction2.getOffsetX() * 0.65,
        (double) pos.getY() + 0.1,
        (double) pos.getZ() + 0.5 + (double) direction2.getOffsetZ() * 0.65,
        new ItemStack(ModItems.FrostPumpkinSeeds, 4));
    itemEntity.setVelocity(
        0.05 * (double) direction2.getOffsetX() + world.random.nextDouble() * 0.02,
        0.05,
        0.05 * (double) direction2.getOffsetZ() + world.random.nextDouble() * 0.02);
    world.spawnEntity(itemEntity);
    stack.damage(1, player, LivingEntity.getSlotForHand(hand));
    world.emitGameEvent(player, GameEvent.SHEAR, pos);
    player.incrementStat(Stats.USED.getOrCreateStat(Items.SHEARS));
    return ActionResult.SUCCESS;
  }
}
