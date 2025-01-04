package faceless.artent.potions.block;

import faceless.artent.core.item.INamed;
import net.minecraft.block.LeavesBlock;

public class CrimsonwoodLeaves extends LeavesBlock implements INamed {
    public CrimsonwoodLeaves(Settings settings) {
        super(settings);
    }

    @Override
    public String getId() {
        return "crimsonwood_leaves";
    }
}