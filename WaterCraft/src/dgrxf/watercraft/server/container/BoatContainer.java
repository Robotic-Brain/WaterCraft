package dgrxf.watercraft.server.container;

import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class BoatContainer extends Container{

	public BoatContainer(IInventory player, AbstractBaseBoat boat){
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

}
