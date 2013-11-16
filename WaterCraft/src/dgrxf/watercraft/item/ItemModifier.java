package dgrxf.watercraft.item;

import net.minecraft.item.Item;
import dgrxf.watercraft.enumeration.ModifierType;

public abstract class ItemModifier extends Item {
    public ModifierType modType;
    
    public ItemModifier(int par1, ModifierType type) {
        super(par1);
        modType = type;
    }
    
}
