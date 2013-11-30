package dgrxf.watercraft.tileentity.controlunit.logic.basic;

import net.minecraft.world.World;
import dgrxf.watercraft.enumeration.ControlUnitLogicTabs;
import dgrxf.watercraft.tileentity.controlunit.logic.ControlUnitLogic;

public class LeaveOnRedstone extends ControlUnitLogic{

	public LeaveOnRedstone() {
		super(1, ControlUnitLogicTabs.basic);
		logic.add(this);
	}
	
	@Override
	public void runLogic(World world, int x, int y, int z) {
		if(world.isBlockIndirectlyGettingPowered(x, y, z)){
			//WCTileEntityControlUnitDock tile = (WCTileEntityControlUnitDock) world.getBlockTileEntity(x, y, z);
			//tile.holdBoat = false;
		}
	}
}
