package faceless.artent.potions.registry;

import faceless.artent.core.registries.IRegistry;
import faceless.artent.potions.ArtentPotions;
import faceless.artent.potions.objects.ModItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemGroupRegistry implements IRegistry {

    @Override
    public void register() {
        Registry.register(Registries.ITEM_GROUP, Identifier.of(ArtentPotions.MODID, "potions"), ModItemGroups.Potions.build());
    }

}
