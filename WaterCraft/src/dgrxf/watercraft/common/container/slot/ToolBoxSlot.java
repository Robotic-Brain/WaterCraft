package dgrxf.watercraft.common.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

/**
 * 
 * ToolBoxSlot
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ToolBoxSlot extends Slot {
    
    public ToolBoxSlot(IInventory inv, int id, int x, int y) {
        super(inv, id, x, y);
    }
    
    @Override
    public boolean isItemValid(ItemStack itemstack) {
        if (itemstack.getItem() instanceof ItemTool) {
            return true;
        } else if (itemstack.getItem() instanceof ItemHoe) {
            return true;
        } else if (itemstack.getItem() instanceof ItemBow) {
            return true;
        } else if (itemstack.getItem() instanceof ItemBucket) {
            return true;
        } else if (itemstack.getItem() instanceof ItemSword) {
            return true;
        } else if (itemstack.getItem() instanceof ItemShears) {
            return true;
        } else if (itemstack.getItem() instanceof ItemFlintAndSteel) {
            return true;
        } else if (itemstack.getItem() instanceof ItemFishingRod) {
            return true;
        }
        return false;
    }
}
