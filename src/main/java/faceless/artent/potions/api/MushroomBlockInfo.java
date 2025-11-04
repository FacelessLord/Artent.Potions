package faceless.artent.potions.api;

import faceless.artent.potions.block.GrowingMushroom;
import faceless.artent.potions.block.MushroomMycelium;
import net.minecraft.item.Item;

public record MushroomBlockInfo(MushroomMycelium mycelium, Item myceliumItem, GrowingMushroom growingMushroom) {
}
