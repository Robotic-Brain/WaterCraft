package dgrxf.watercraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.block.ModBlocks;

public class CreativeTabBuoys extends CreativeTabs {
    
    public CreativeTabBuoys(int tabId, String label) {
        super(tabId, label);
    }
    
    /**
     * the itemID for the item to be displayed on the tab
     */
    @Override
    @SideOnly(Side.CLIENT)
    public int getTabIconItemIndex() {
        return ModBlocks.buoy.blockID;
    }
    
}
