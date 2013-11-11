package dgrxf.watercraft.client.gui.container;

import dgrxf.watercraft.tileentity.WCTileEntityControlUnitDock;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ControlUnitContainer extends Container {

	private WCTileEntityControlUnitDock unit;
	
	public ControlUnitContainer(InventoryPlayer invPlayer, WCTileEntityControlUnitDock te) {
		unit = te;
		
		for(int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 18 + 18 * x, 194));
		}
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 18 + 18 * x, 136 + y * 18));
			}
		}
		
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return unit.isUseableByPlayer(entityplayer);
	}

}
