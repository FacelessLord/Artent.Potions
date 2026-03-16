package faceless.artent.potions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class SpecialFlower extends FlowerBlock {
  private Block allowedBaseBlock;
  private TagKey<Block> allowedBaseTag;

  public SpecialFlower(
      RegistryEntry<StatusEffect> stewEffect,
      float effectLengthInSeconds,
      Block allowedBaseBlock,
      Settings settings) {
    super(stewEffect, effectLengthInSeconds, settings);
    this.allowedBaseBlock = allowedBaseBlock;
  }

  public SpecialFlower(
      RegistryEntry<StatusEffect> stewEffect,
      float effectLengthInSeconds,
      TagKey<Block> allowedBaseTag,
      Settings settings) {
    super(stewEffect, effectLengthInSeconds, settings);
    this.allowedBaseTag = allowedBaseTag;
  }

  @Override
  protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
    return this.allowedBaseTag != null && floor.isIn(this.allowedBaseTag)
           || this.allowedBaseBlock != null && floor.isOf(this.allowedBaseBlock);
  }
}
