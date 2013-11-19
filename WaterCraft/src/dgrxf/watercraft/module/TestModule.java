package dgrxf.watercraft.module;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.DumbTask;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;

public class TestModule implements IBoatModule {

	@Override
	public ModuleType getModuleType() {
		return ModuleType.INVENTORY;
	}

	@Override
	public Block getBlockType() {
		return ModBlocks.chest;
	}

	@Override
	public void writeModuleInfoToNBT(NBTTagCompound tag) {
		tag.setString("Module Class", DumbTask.class.getName());
	}

	@Override
	public void addBoatAI(BoatAITaskList list, AbstractBaseBoat boat, float f, Object... params) {
		list.addTask(new DumbTask(boat, f));
	}

}
