package dgrxf.watercraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.item.ModItems;

/**
 * 
 * CreativeTabMisc
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class CreativeTabMisc extends CreativeTabs {
    
    public CreativeTabMisc(int tabId, String label) {
        super(tabId, label);
    }
    
    /**
     * the itemID for the item to be displayed on the tab
     * TODO: see CreativeTabBoats
     */
    @Override
    @SideOnly(Side.CLIENT)
    public int getTabIconItemIndex() {
        return ModItems.TELESCOPE.getShifted();
    }
    
}
