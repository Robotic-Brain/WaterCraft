package dgrxf.watercraft.tileentity.controlunit.logic.basic;

import dgrxf.watercraft.enumeration.ControlUnitLogicTabs;
import dgrxf.watercraft.tileentity.controlunit.logic.ControlUnitLogic;

public class LeaveAfter extends ControlUnitLogic{

	public LeaveAfter() {
		super(0, ControlUnitLogicTabs.basic);
		logic.add(this);
	}
}
