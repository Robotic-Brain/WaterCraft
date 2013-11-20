package dgrxf.watercraft.tileentity.controlunit.logic;

import java.util.ArrayList;

import net.minecraft.world.World;
import dgrxf.watercraft.enumeration.ControlUnitLogicTabs;
import dgrxf.watercraft.tileentity.controlunit.logic.basic.LeaveAfter;
import dgrxf.watercraft.tileentity.controlunit.logic.basic.LeaveOnRedstone;

public class ControlUnitLogic {
	
	public static ControlUnitLogic basicLeaveAfter = new LeaveAfter();
	public static ControlUnitLogic basicOnRedstone = new LeaveOnRedstone();
	public static ControlUnitLogic basicOnInteraction = new LeaveAfter();
	
	private int logicID;
	private ControlUnitLogicTabs tab;
	public ArrayList<ControlUnitLogic> logic = new ArrayList<ControlUnitLogic>();
	
	public ControlUnitLogic(int id, ControlUnitLogicTabs tab){
		logicID = id;
		this.tab = tab;
	}
	
	public void runLogic(World world, int x, int y, int z){
		
	}
	
	public ControlUnitLogic getLogic(int id){
		return logic.get(id);
	}
}
