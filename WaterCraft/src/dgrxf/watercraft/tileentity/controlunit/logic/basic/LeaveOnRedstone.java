package dgrxf.watercraft.tileentity.controlunit.logic.basic;

import dgrxf.watercraft.enumeration.ControlUnitLogicTabs;
import dgrxf.watercraft.tileentity.controlunit.logic.ControlUnitLogic;

public class LeaveOnRedstone extends ControlUnitLogic{

	public LeaveOnRedstone() {
		super(1, ControlUnitLogicTabs.basic);
		logic.add(this);
	}
}
