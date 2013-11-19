package dgrxf.watercraft.module;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.DumbTask;
import dgrxf.watercraft.enumeration.Alphabet;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;

public class DumbModule implements IBoatModule{

	@Override
	public ModuleType getModuleType() {
		return ModuleType.BOAT;
	}

	@Override
	public Block getBlockType() {
		return null;
	}

	@Override
	public void addBoatAI(BoatAITaskList list, AbstractBaseBoat boat, float f, Object... obj) {
		list.addTask(new DumbTask(boat, f));
	}

	@Override
	public int writeModuleInfoToNBT(NBTTagCompound tag, int startingPos) {
		tag.setString(Alphabet.values()[startingPos].toString(), this.getClass().getName());
		return 1;
	}

}
