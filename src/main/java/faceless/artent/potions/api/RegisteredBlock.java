package faceless.artent.potions.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public record RegisteredBlock<T extends Block>(T block, Item item) {
}
