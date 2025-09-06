package faceless.artent.potions.client.registry;

import faceless.artent.core.math.Color;
import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.block.BerryBush;
import faceless.artent.potions.blockEntities.BrewingCauldronBlockEntity;
import faceless.artent.potions.objects.ModBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

public class ColorProvidersRegistry implements IRegistry {
  @Override
  public void register() {
    ColorProviderRegistry.BLOCK.register(
        (state, view, pos, tintIndex) -> {
          if (view == null) return 0;
          var be = view.getBlockEntity(pos);
          if (be instanceof BrewingCauldronBlockEntity cauldron) {
            return cauldron.color.toHex();
          }
          return 0;
        }, ModBlocks.CauldronFluid);

    var grassColorTinter0 = getGrassColorTintForIndex(0);
    var grassColorTinter1 = getGrassColorTintForIndex(1);

    ColorProviderRegistry.BLOCK.register(
        grassColorTinter0,
        ModBlocks.Shadowveil,
        ModBlocks.SlimeBerry,
        ModBlocks.BlazingMarigold);

    ColorProviderRegistry.BLOCK.register(
        (BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) -> {
          if (tintIndex == 0) return grassColorTinter0.getColor(state, world, pos, tintIndex);
          if (tintIndex == 1) {
            var age = state.get(BerryBush.AGE);
            if (age == 0) return Color.White.asInt();
            if (age == 1) {
              var grassColor = grassColorTinter1.getColor(state, world, pos, tintIndex);
              var greenBerryColorInitial = new Color(92, 242, 0);
              return greenBerryColorInitial.add(Color.fromInt(grassColor)).asInt();
            }
            // age == 2
            var block = state.getBlock();
            var type = -1;
            for (int i = 0; i < ModBlocks.berryBush.length; i++) {
              if (block == ModBlocks.berryBush[i]) {
                type = i;
                break;
              }
            }

            return switch (type) {
              case -1 -> new Color(0, 128, 0).asInt();
              case 0 -> new Color(50, 0, 100).asInt();
              case 1 -> new Color(0, 50, 255).asInt();
              case 2 -> new Color(255, 255, 0).asInt();
              case 3 -> new Color(255, 0, 0).asInt();
              default -> 0;
            };
          }
          return Color.White.asInt();
        }, ModBlocks.berryBush[0], ModBlocks.berryBush[1], ModBlocks.berryBush[2], ModBlocks.berryBush[3]);
  }

  public static BlockColorProvider getGrassColorTintForIndex(int index) {
    return (BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) -> {
      if (tintIndex != index || world == null || pos == null) {
        return Color.White.asInt();
      }
      var biome = world.getBiomeFabric(pos);
      if (biome == null) return new Color(0, 50, 0).asInt();
      var grassColor = biome.value().getGrassColorAt(pos.getX(), pos.getZ());
      var artentColor = Color.fromInt(grassColor);
      float[] hsv = new float[3];
      java.awt.Color.RGBtoHSB(artentColor.red, artentColor.green, artentColor.blue, hsv);
      hsv[1] *= 1.5f;

      return java.awt.Color.HSBtoRGB(hsv[0], hsv[2], hsv[2]);
    };
  }
}
