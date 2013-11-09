package dgrxf.watercraft.client.gui.container;

import dgrxf.watercraft.tileentity.WCTileEntityToolBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ToolboxContainer extends Container {

	public ToolboxContainer(InventoryPlayer invPlayer, WCTileEntityToolBox te) {
		
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(te, x, 8 + 18 * x, 16));
		}
		
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 99));
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 41 + y * 18));
			}
		}
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

}
