package faceless.artent_potions.registry;

import faceless.artent_potions.objects.ModItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemGroupRegistry implements IRegistry {

    @Override
    public void register() {
        Registry.register(Registries.ITEM_GROUP, new Identifier("artent", "potions"), ModItemGroups.Potions.build());
    }

}
