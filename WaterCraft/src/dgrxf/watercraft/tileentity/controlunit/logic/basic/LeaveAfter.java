package dgrxf.watercraft.tileentity.controlunit.logic.basic;

import net.minecraft.world.World;
import dgrxf.watercraft.enumeration.ControlUnitLogicTabs;
import dgrxf.watercraft.tileentity.controlunit.logic.ControlUnitLogic;

public class LeaveAfter extends ControlUnitLogic{

	private int countDown = 10;
	private int tick = countDown;
	public LeaveAfter() {
		super(0, ControlUnitLogicTabs.basic);
		logic.add(this);
	}
	
	@Override
	public void runLogic(World world, int x, int y, int z) {
		System.out.println("Tick");
		tick--;
        if(tick <= 0){
			System.out.println("Logic Run!");
			tick = countDown;
		}
	}
}
