package faceless.artent.potions.client.renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import faceless.artent.potions.block.DryingRack;
import faceless.artent.potions.blockEntities.BrewingCauldronBlockEntity;
import faceless.artent.potions.blockEntities.DryingRackBlockEntity;
import faceless.artent.potions.objects.ModBlocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.RotationCalculator;
import net.minecraft.util.math.Vec3d;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.lwjgl.opengl.GL11;

public class DryingRackRenderer implements BlockEntityRenderer<DryingRackBlockEntity> {

  public DryingRackRenderer(BlockEntityRendererFactory.Context ctx) {
  }

  @Override
  public void render(
      DryingRackBlockEntity entity,
      float tickDelta,
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light,
      int overlay) {
    var itemRenderer = MinecraftClient.getInstance().getItemRenderer();

    var pos = entity.getPos();
    var world = entity.getWorld();
    if (world == null || world.isAir(pos))
      return;
    var state = world.getBlockState(pos);

    var direction = state.get(DryingRack.FACING);
    var facingVector = direction.getDoubleVector();
    var localLeftDir = facingVector.crossProduct(new Vec3d(0, -1, 0));

    matrices.push();
    matrices.translate(0.5, 0.559, 0.5);
    for (int i = 0; i < entity.getInventorySize(); i++) {
      matrices.push();
      matrices.translate(0, -0.25 * (Math.divideExact(i, 2) * 2 - 1), 0);
      // TODO special item translation for normal mushrooms good view
      matrices.translate(facingVector.multiply(-5 / 16f));
      matrices.translate(localLeftDir.multiply((i % 2) * 2 - 1f).multiply(0.25f));
      matrices.scale(0.75f, 0.75f, 0.75f);

      var rotation = new AxisAngle4f((float) Math.PI / 2 * direction.getHorizontalQuarterTurns(), 0, 1, 0);
      matrices.multiply(new Quaternionf(rotation));
      itemRenderer.renderItem(
          entity.items[i],
          ModelTransformationMode.GROUND,
          light,
          overlay,
          matrices,
          vertexConsumers,
          entity.getWorld(),
          (int) entity.getWorld().getTime());

      matrices.pop();
    }

    matrices.pop();
  }
}