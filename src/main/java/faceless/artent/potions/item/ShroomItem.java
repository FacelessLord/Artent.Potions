package faceless.artent.potions.item;

import faceless.artent.potions.entity.ManaChicken;
import faceless.artent.potions.objects.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class ShroomItem extends BlockItem {
  public ShroomItem(Block block, Settings settings) {
    super(block, settings);
  }

  public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
    var world = entity.getWorld();
    if (entity instanceof ChickenEntity chicken
        && !(chicken instanceof ManaChicken)
        && world != null
        && !world.isClient) {
      var manaChicken = ModEntities.MANA_CHICKEN.create(world, SpawnReason.TRIGGERED);
      if (manaChicken != null) {
        // It is possible to transfer HP, but it can be handwaved like that:
        // during transformation, the entire body of the chicken is rebuilt, so it immediately feels like new
        manaChicken.getAttributes().setFrom(chicken.getAttributes());
        if (chicken.hasCustomName()) {
          manaChicken.setCustomName(chicken.getCustomName());
        }
        manaChicken.setBaby(chicken.isBaby());
        manaChicken.setPosition(chicken.getPos());
        manaChicken.setYaw(chicken.getYaw());
        manaChicken.setPitch(chicken.getPitch());
        world.spawnEntity(manaChicken);
        chicken.discard();
      }
    }
    return ActionResult.PASS;
  }
}
