package dgrxf.watercraft.tileentity.controlunit.logic.basic;

import net.minecraft.world.World;
import dgrxf.watercraft.enumeration.ControlUnitLogicTabs;
import dgrxf.watercraft.tileentity.controlunit.logic.ControlUnitLogic;

/**
 * 
 * LeaveOnInteraction
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class LeaveOnInteraction extends ControlUnitLogic{

	public LeaveOnInteraction() {
		super(2, ControlUnitLogicTabs.basic);
		logic.add(this);
	}
	
	@Override
	public void runLogic(World world, int x, int y, int z) {
		/*WCTileEntityControlUnitDock tile = (WCTileEntityControlUnitDock) world.getBlockTileEntity(x, y, z);
		if(tile.dockedBoat != null && tile.dockedBoat.playerHasInteractedWith){
			tile.holdBoat = false;
		}*/
	}
}
