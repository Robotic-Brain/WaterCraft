package dgrxf.watercraft.tileentity.controlunit.logic;

import java.util.ArrayList;

import dgrxf.watercraft.enumeration.ControlUnitLogicTabs;
import dgrxf.watercraft.tileentity.controlunit.logic.basic.LeaveAfter;

public class ControlUnitLogic {
	
	public static ControlUnitLogic basicLeaveAfter = new LeaveAfter();
	public static ControlUnitLogic basicOnRedstone = new LeaveAfter();
	public static ControlUnitLogic basicOnInteraction = new LeaveAfter();
	
	private int logicID;
	private ControlUnitLogicTabs tab;
	public ArrayList<ControlUnitLogic> logic = new ArrayList<ControlUnitLogic>();
	
	public ControlUnitLogic(int id, ControlUnitLogicTabs tab){
		logicID = id;
		this.tab = tab;
	}
	
	public void runLogic(){
		
	}
	
	public ControlUnitLogic getLogic(int id){
		return logic.get(id);
	}
}
