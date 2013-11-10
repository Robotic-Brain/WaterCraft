package dgrxf.watercraft.entity;

import net.minecraft.world.World;
import dgrxf.watercraft.util.Vector2;

public class WCEntitySmartBoat extends WCEntityBoat {

	private int iterator = 0;
	private Vector2[] list;
	
	public WCEntitySmartBoat(World world) {
		super(world);
	}

	public void setList(Vector2[] list){
		this.list = list;
	}
	
	public void iterateList(){
		iterator++;
	}
	
	
}
