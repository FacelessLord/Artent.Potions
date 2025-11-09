package faceless.artent.potions.client.renderers;

import faceless.artent.potions.block.DryingRack;
import faceless.artent.potions.blockEntities.DryingRackBlockEntity;
import faceless.artent.potions.objects.ModBlocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.Vec3d;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;

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
      var stack = entity.items[i];
      var byproduct = entity.byproducts[i];
      if (stack == null || stack.isEmpty())
        continue;

      matrices.push();
      matrices.translate(0, -0.25 * (Math.divideExact(i, 2) * 2 - 1), 0);
      matrices.translate(facingVector.multiply(-5 / 16f));
      matrices.translate(localLeftDir.multiply((i % 2) * 2 - 1f).multiply(0.25f));
      matrices.scale(0.75f, 0.75f, 0.75f);

      if (stack.getItem() == Items.BROWN_MUSHROOM
          || stack.getItem() == Items.RED_MUSHROOM
          || stack.getItem() == ModBlocks.SHROOM.item()) {
        matrices.translate(0, 0.125, 0);
        matrices.scale(1.5f, 1.5f, 1.5f);
      }

      var rotation = new AxisAngle4f(-(float) Math.PI / 2 * direction.getHorizontalQuarterTurns(), 0, 1, 0);
      matrices.multiply(new Quaternionf(rotation));

      itemRenderer.renderItem(
          stack,
          ModelTransformationMode.GROUND,
          light,
          overlay,
          matrices,
          vertexConsumers,
          entity.getWorld(),
          (int) entity.getWorld().getTime());

      if (byproduct != null) {
        matrices.translate(0.25, 0.125, 0);
        matrices.scale(0.25f, 0.25f, 0.25f);
        itemRenderer.renderItem(
            byproduct,
            ModelTransformationMode.GROUND,
            light,
            overlay,
            matrices,
            vertexConsumers,
            entity.getWorld(),
            (int) entity.getWorld().getTime());
      }

      matrices.pop();
    }

    matrices.pop();
  }
}