package dgrxf.watercraft.item;

import net.minecraft.item.ItemBlock;
import dgrxf.watercraft.Watercraft;

/**
 * 
 * ItemBlockChest
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ItemBlockChest extends ItemBlock{
    
    public ItemBlockChest(int id) {
        super(id);
        setCreativeTab(Watercraft.miscTab);
        maxStackSize = 64;
    }
}
