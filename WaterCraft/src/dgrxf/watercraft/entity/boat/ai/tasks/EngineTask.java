package dgrxf.watercraft.entity.boat.ai.tasks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;

public class EngineTask extends BoatAITaskBase{

	private int burnTime;
	AbstractBaseBoat boat;
	
	public EngineTask(AbstractBaseBoat boat, Float priority, Object[] obj) {
		super(boat, priority, obj);
		this.boat = boat;
	}
	
	@Override
	public void updateMotion() {
		if(burnTime <= 0){
			System.out.println("out of coal");
		}else{
			burnTime--;
			System.out.println("burnTime| " + burnTime);
			boat.setBoatSpeed(0.3);
		}
	}
}
