package dgrxf.watercraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.item.ModItems;

public class CreativeTabBoats extends CreativeTabs {
    
	
    public CreativeTabBoats(int tabId, String label) {
        super(tabId, label);
    }
    
    /**
     * TODO This one returns a stack instead of an ID maybe we should use this instead?
     */
    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return ModItems.MODULAR_BOAT.getItem();
    }
    
}
