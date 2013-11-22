package dgrxf.watercraft.module;

import net.minecraft.block.Block;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.TankTask;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;

public class TankModule implements IBoatModule {
	
	@Override
	public ModuleType[] getModuleType() {
		return new ModuleType[]{ModuleType.INVENTORY, ModuleType.BLOCK};
	}

	@Override
	public Block getBlockType() {
		return ModBlocks.tank;
	}

	@Override
	public void addBoatAI(BoatAITaskList list, AbstractBaseBoat boat, float f) {
		list.addTask(new TankTask(boat, f, 8));
	}

}