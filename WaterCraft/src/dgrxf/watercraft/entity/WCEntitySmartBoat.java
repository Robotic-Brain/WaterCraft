package dgrxf.watercraft.entity;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.util.Vector2;

public class WCEntitySmartBoat extends WCEntityBoat {

	private int iterator = 0;
	private ForgeDirection[] list;
	
	public WCEntitySmartBoat(World world){
		super(world);
	}
	
	public WCEntitySmartBoat(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public void setTargetLocation(Vector2 target) {
		this.target = target;
	}
	
	public void setList(ForgeDirection[] list){
		this.list = list;
	}
	
	public void iterateList(){
		iterator++;
		if(list != null){
			if(iterator > list.length-1){
				iterator = 0;
			}
		}
		else{
			target = null;
		}
	}
	
	@Override
	public void moveToTarget(){
		if(target != null){
			motionX = target.x;
			motionZ = target.z;
		}
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		
		if(list != null){
			//super.setTargetLocation(list[iterator]);
			setTargetLocation(new Vector2(list[iterator].offsetX/10, list[iterator].offsetZ/10));
		}
		
	}
}
