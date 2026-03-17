package faceless.artent.potions.block;

import faceless.artent.core.math.Color;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.TrailParticleEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;

public class LocatorBlock extends Block {
  public LocatorBlock(Settings settings) {
    super(settings);
  }

  @Override
  protected ActionResult onUse(BlockState state,
                               World world,
                               BlockPos pos,
                               PlayerEntity player,
                               BlockHitResult hit) {
    var posBelow = pos.down();
    var exampleBlockState = world.getBlockState(posBelow);
    var positions = new ArrayList<BlockPos>();
    if (!world.isClient)
      return ActionResult.SUCCESS;

    for (int i = -64; i < 64; i++) {
      for (int j = -64; j < 64; j++) {
        for (int k = -64; k < 64; k++) {
          var testPos = pos.add(i, j, k);
          var testBlockState = world.getBlockState(testPos);
          if (testBlockState.getBlock() == exampleBlockState.getBlock()) {
            positions.add(testPos);
          }
        }
      }
    }

    for (var position : positions) {
      var center = pos.toCenterPos();
      var offset = position.subtract(pos);
      var velocity = new Vec3d(offset.getX(), offset.getY(), offset.getZ()).normalize();
      for (int i = 0; i < 10; i++) {
        world.addParticle(
            new TrailParticleEffect(
                position.toCenterPos(),
                Color.White.toHex(),
                40
            ),
            center.getX() + world.random.nextDouble() - 0.5,
            center.getY() + 1 + world.random.nextDouble() - 0.5,
            center.getZ() + world.random.nextDouble() - 0.5,
            velocity.getX(),
            velocity.getY(),
            velocity.getZ()
        );
      }
    }

    return super.onUse(state, world, pos, player, hit);
  }
}
