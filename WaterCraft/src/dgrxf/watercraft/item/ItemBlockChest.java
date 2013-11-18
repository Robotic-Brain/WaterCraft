package dgrxf.watercraft.item;

import net.minecraft.item.ItemBlock;
import dgrxf.watercraft.Watercraft;

public class ItemBlockChest extends ItemBlock {
    
    public ItemBlockChest(int id) {
        super(id);
        setCreativeTab(Watercraft.miscTab);
        maxStackSize = 64;
    }
}
