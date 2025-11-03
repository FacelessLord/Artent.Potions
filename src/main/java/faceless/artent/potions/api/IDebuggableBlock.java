package faceless.artent.potions.api;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface IDebuggableBlock {
  void fillDebugInfo(World world, BlockPos pos, BlockState state, List<String> debugInfo);
}
