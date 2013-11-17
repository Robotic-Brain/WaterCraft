package dgrxf.watercraft.client.gui.container;

import dgrxf.watercraft.tileentity.WCTileEntityFreezer;
import dgrxf.watercraft.tileentity.WCTileEntityLockAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

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
	}
	
	public WCTileEntityLockAssembler getTileEntity() {
		return lock;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
	
}
