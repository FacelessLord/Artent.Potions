package faceless.artent_potions.item;

import faceless.artent.brewing.api.AlchemicalPotionUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FilledGoldenBucket extends Item {
    public FilledGoldenBucket(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        AlchemicalPotionUtil.createEffectTooltip(stack, tooltip);
    }
}