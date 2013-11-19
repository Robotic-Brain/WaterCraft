package dgrxf.watercraft.module;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.entity.boat.ai.tasks.BoatAITaskBase;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;

public class TestModule implements IBoatModule {

	@Override
	public ModuleType getModuleType() {
		return ModuleType.ACCESSORY;
	}

	@Override
	public Block getBlockType() {
		return null;
	}

	@Override
	public void addBoatAI(List<Class<? extends BoatAITaskBase>> list) {

	}

	@Override
	public void writeModuleInfoToNBT(NBTTagCompound tag) {

	}

}
