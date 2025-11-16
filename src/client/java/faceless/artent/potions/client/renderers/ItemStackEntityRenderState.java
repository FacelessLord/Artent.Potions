package faceless.artent.potions.client.renderers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class ItemStackEntityRenderState extends EntityRenderState {
  public final ItemRenderState itemRenderState = new ItemRenderState();
  public int renderedAmount;
  public int seed;

  public void update(World world, ItemStack stack, ItemModelManager itemModelManager) {
    this.seed = getSeed(stack);
    itemModelManager.update(this.itemRenderState, stack, ModelTransformationMode.GROUND, false, world, null, this.seed);
    this.renderedAmount = getRenderedAmount(stack.getCount());
  }

  public static int getSeed(ItemStack stack) {
    return stack.isEmpty() ? 187 : Item.getRawId(stack.getItem()) + stack.getDamage();
  }

  public static int getRenderedAmount(int count) {
    if (count <= 1) {
      return 1;
    } else if (count <= 16) {
      return 2;
    } else if (count <= 32) {
      return 3;
    } else {
      return count <= 48 ? 4 : 5;
    }
  }
}
