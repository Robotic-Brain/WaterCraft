package dgrxf.watercraft.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import dgrxf.watercraft.common.container.slot.OutputSlot;
import dgrxf.watercraft.tileentity.WCTileEntityLockAssembler;

/**
 * 
 * LockAssemblerContainer
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class LockAssemblerContainer extends Container {
	WCTileEntityLockAssembler lock;
	
	public LockAssemblerContainer(InventoryPlayer inv, WCTileEntityLockAssembler te) {
		lock = te;
		
		for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inv, i, 18 + 18 * i, 158));
        }
        
        for (int i = 0; i < 27; i++) {
            addSlotToContainer(new Slot(inv, i + 9, 18 + 18 * (i % 9), 100 + 18 * (i / 9)));
        }
        

        addSlotToContainer(new OutputSlot(te, 3, 131, 21));
        addSlotToContainer(new OutputSlot(te, 4, 152, 21));
	}
	
	public WCTileEntityLockAssembler getTileEntity() {
		return lock;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
	
}
