package faceless.artent.potions.client.renderers;

import faceless.artent.potions.block.Shelf;
import faceless.artent.potions.blockEntities.ShelfBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;

public class ShelfRenderer implements BlockEntityRenderer<ShelfBlockEntity> {
  public ShelfRenderer(BlockEntityRendererFactory.Context ctx) {
  }

  @Override
  public void render(
      ShelfBlockEntity entity,
      float tickDelta,
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light,
      int overlay) {
    var itemModelManager = MinecraftClient.getInstance().getItemModelManager();

    var pos = entity.getPos();
    var world = entity.getWorld();
    if (world == null || world.isAir(pos)) return;
    var state = world.getBlockState(pos);

    var direction = state.get(Shelf.FACING);
    var facingVector = direction.getDoubleVector();
    var localLeftDir = facingVector.crossProduct(new Vec3d(0, -1, 0));

    matrices.push();
    matrices.translate(0.5, 0.50, 0.5);
    for (int i = 0; i < entity.getInventorySize(); i++) {
      var stack = entity.items[i];

      matrices.push();
      matrices.translate(0, -0.25 * (Math.divideExact(i, 2) * 2 - 1), 0);
      matrices.translate(facingVector.multiply(-5 / 16f));
      matrices.translate(localLeftDir.multiply((i % 2) * 2 - 1f).multiply(0.25f));
      matrices.translate(0, -0.1, 0);
      matrices.scale(0.75f, 0.75f, 0.75f);

      var rotation = new AxisAngle4f((float) Math.PI - (float) Math.PI / 2 * direction.getHorizontalQuarterTurns(), 0, 1, 0);
      matrices.multiply(new Quaternionf(rotation));

      var itemState = new ItemStackEntityRenderState();
      itemState.update(world, stack, itemModelManager);
      renderStack(
          matrices,
          vertexConsumers,
          light,
          itemState,
          world.getRandom());

      matrices.pop();
    }

    matrices.pop();
  }

  public static void renderStack(
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light,
      ItemStackEntityRenderState state,
      Random random) {
    random.setSeed(state.seed);
    int i = state.renderedAmount;
    ItemRenderState itemRenderState = state.itemRenderState;
    boolean bl = itemRenderState.hasDepth();
    float f = itemRenderState.getTransformation().scale.x();
    float g = itemRenderState.getTransformation().scale.y();
    float h = itemRenderState.getTransformation().scale.z();
    if (!bl) {
      float j = -0.0F * (float) (i - 1) * 0.5F * f;
      float k = -0.0F * (float) (i - 1) * 0.5F * g;
      float l = -0.09375F * (float) (i - 1) * 0.5F * h;
      matrices.translate(j, k, l);
    }

    for (int m = 0; m < i; m++) {
      matrices.push();
      if (m > 0) {
        if (bl) {
          float k = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
          float l = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
          float n = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
          matrices.translate(k, l, n);
        } else {
          float k = (random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
          float l = (random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
          matrices.translate(k, l, 0.0F);
        }
      }

      itemRenderState.render(matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);
      matrices.pop();
      if (!bl) {
        matrices.translate(0.0F * f, 0.0F * g, 0.09375F * h);
      }
    }
  }
}