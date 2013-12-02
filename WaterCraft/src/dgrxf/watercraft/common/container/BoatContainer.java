package dgrxf.watercraft.common.container;

import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

/**
 * 
 * BoatContainer
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class BoatContainer extends Container{

	public BoatContainer(IInventory player, AbstractBaseBoat boat){
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

}
