package faceless.artent.potions.mixin;

import faceless.artent.potions.objects.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public class StrippingMixin {
  @Inject(method = "getStrippedState", at=@At("HEAD"), cancellable = true)
  private void getStrippedState(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir){
    if( state.getBlock() == ModBlocks.CRIMSONWOOD_LOG.block()){
      var result = ModBlocks.CRIMSONWOOD_LOG_STRIPPED.block().getDefaultState().with(
          PillarBlock.AXIS, state.get(PillarBlock.AXIS));
      cir.setReturnValue(Optional.of(result));
    }
  }
}
