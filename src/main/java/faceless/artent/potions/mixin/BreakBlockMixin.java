package faceless.artent.potions.mixin;

import faceless.artent.potions.registry.StatusEffectsRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class BreakBlockMixin {
  @Inject(at = @At("TAIL"), method = "calcBlockBreakingDelta", cancellable = true)
  public void calcBlockBreakingDelta(
      BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
    if (player.hasStatusEffect(StatusEffectsRegistry.LUMBERJACK)) {
      var potion = player.getStatusEffect(StatusEffectsRegistry.LUMBERJACK);
      if(potion != null){
        if (state.isIn(BlockTags.LOGS)) {
          var modifier = 6 << potion.getAmplifier();
          cir.setReturnValue(cir.getReturnValue() * modifier);
        }
        if (state.isIn(BlockTags.LEAVES)) {
          var modifier = 6 << potion.getAmplifier();
          cir.setReturnValue(cir.getReturnValue() * modifier);
        }
      }
    }
  }
}