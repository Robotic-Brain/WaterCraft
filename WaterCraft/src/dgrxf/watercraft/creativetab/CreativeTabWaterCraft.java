package dgrxf.watercraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.block.ModBlocks;

/**
 * Class Made By: Robotic-Brain
 * 
 * Class Last Edited By: Robotic-Brain
 * Class Last Edited On: 08/11/2013
 *                       DD/MM/YYYY
 * 
 */

public class CreativeTabWaterCraft extends CreativeTabs {
    
    public CreativeTabWaterCraft(int tabId, String label) {
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
