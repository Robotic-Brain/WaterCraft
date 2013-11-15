package dgrxf.watercraft.client.gui.container;

import dgrxf.watercraft.tileentity.WCTileEntityFreezer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class FreezerContainer extends Container {
	WCTileEntityFreezer freezer;
	
	public FreezerContainer(WCTileEntityFreezer te) {
		freezer = te;
	}
	
	public WCTileEntityFreezer getTileEntity() {
		return freezer;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
	
}
