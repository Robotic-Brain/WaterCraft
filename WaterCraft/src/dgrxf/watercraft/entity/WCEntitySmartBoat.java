package dgrxf.watercraft.entity;

import net.minecraft.world.World;
import dgrxf.watercraft.util.Vector2;

public class WCEntitySmartBoat extends WCEntityBoat {

	private int iterator = 0;
	private Vector2[] list = null;
	
	public WCEntitySmartBoat(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public void setList(Vector2[] list){
		this.list = list;
	}
	
	public void iterateList(){
		iterator++;
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		
		if(list != null){
			super.setTargetLocation(list[iterator]);
		}
		
	}
}
