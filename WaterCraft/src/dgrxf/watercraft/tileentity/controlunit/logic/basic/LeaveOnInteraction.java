package dgrxf.watercraft.tileentity.controlunit.logic.basic;

import dgrxf.watercraft.enumeration.ControlUnitLogicTabs;
import dgrxf.watercraft.tileentity.controlunit.logic.ControlUnitLogic;

public class LeaveOnInteraction extends ControlUnitLogic{

	public LeaveOnInteraction() {
		super(2, ControlUnitLogicTabs.basic);
		logic.add(this);
	}
}
