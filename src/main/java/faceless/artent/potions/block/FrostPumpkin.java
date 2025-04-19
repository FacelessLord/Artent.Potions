package faceless.artent.potions.block;

import faceless.artent.potions.objects.ModBlocks;
import faceless.artent.potions.objects.ModEntities;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FrostPumpkin extends CarvedPumpkinBlock {
  @Nullable
  private BlockPattern frostedGolemPattern;

  public FrostPumpkin(Settings settings) {
    super(settings);
  }

  protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
    if (!oldState.isOf(state.getBlock())) {
      this.trySpawnEntity(world, pos);
    }
  }

  private void trySpawnEntity(World world, BlockPos pos) {
    BlockPattern.Result result = this.getFrostedGolemPattern().searchAround(world, pos);
    if (result != null) {
      var snowGolemEntity = ModEntities.FROSTED_GOLEM.create(world, SpawnReason.TRIGGERED);
      if (snowGolemEntity != null) {
        spawnEntity(world, result, snowGolemEntity, result.translate(0, 4, 0).getBlockPos());
      }
    }
  }


  private static void spawnEntity(World world, BlockPattern.Result patternResult, Entity entity, BlockPos pos) {
    breakPatternBlocks(world, patternResult);
    entity.refreshPositionAndAngles(
        (double) pos.getX() + (double) 0.5F,
        (double) pos.getY() + 0.05,
        (double) pos.getZ() + (double) 0.5F,
        0.0F,
        0.0F);
    world.spawnEntity(entity);

    for (ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(
        ServerPlayerEntity.class,
        entity.getBoundingBox().expand(5.0F))) {
      Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, entity);
    }

    updatePatternBlocks(world, patternResult);
  }

  private BlockPattern getFrostedGolemPattern() {
    if (this.frostedGolemPattern == null) {
      this.frostedGolemPattern = BlockPatternBuilder
          .start()
          .aisle("^", "#", "#", "#", "#")
          .where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(ModBlocks.FrostPumpkin)))
          .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.OBSIDIAN)))
          .build();
    }

    return this.frostedGolemPattern;
  }
}
