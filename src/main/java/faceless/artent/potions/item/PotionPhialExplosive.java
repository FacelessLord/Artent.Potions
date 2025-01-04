package faceless.artent.potions.item;

import faceless.artent.core.item.INamed;
import faceless.artent.potions.brewingApi.AlchemicalPotionUtil;
import faceless.artent.potions.entity.ThrowablePotionPhialEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

import static faceless.artent.potions.registry.DataComponentRegistry.POTION_KEY;

public class PotionPhialExplosive extends Item implements INamed {
    public PotionPhialExplosive(Settings settings) {
        super(settings);
    }

    @Override
    public String getId() {
        return "potion_phial_explosive";
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            ThrowablePotionPhialEntity potionEntity = new ThrowablePotionPhialEntity(world, user, itemStack);
            potionEntity.setItem(itemStack);
            potionEntity.setVelocity(user, user.getPitch(), user.getYaw(), -20.0f, 0.5f, 1.0f);
            world.spawnEntity(potionEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public Text getName(ItemStack stack) {
        var key = stack.get(POTION_KEY);
        return Text.translatable("item.potion." + key + ".explosive");
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        AlchemicalPotionUtil.buildTooltip(stack, tooltip, 1.0f, context.getUpdateTickRate());
    }
}