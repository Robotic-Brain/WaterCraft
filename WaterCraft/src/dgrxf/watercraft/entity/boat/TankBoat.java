package dgrxf.watercraft.entity.boat;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.DumbTask;
import dgrxf.watercraft.entity.boat.ai.tasks.TankTask;
import dgrxf.watercraft.lib.EntityInfo;

public class TankBoat extends AbstractBaseBoat{

	public TankBoat(World world){
		super(world);
	}
	
	public TankBoat(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(EntityInfo.DATAWATCHER_TANK_AMOUNT, new Integer(0));
		dataWatcher.addObject(EntityInfo.DATAWATCHER_LIQUID_NAME, "none");
	}

	@Override
	protected void updateBoatAI(BoatAITaskList list) {
		list.addTask(new DumbTask(this, 0.0F));
		list.addTask(new TankTask(this, 1.0F, 8));
	}
	
	@Override
	public Block getDisplayTile() {
		return ModBlocks.TANK.getBlock();
	}
}
