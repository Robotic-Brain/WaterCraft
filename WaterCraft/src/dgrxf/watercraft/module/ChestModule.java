package dgrxf.watercraft.module;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.DumbTask;
import dgrxf.watercraft.entity.boat.ai.tasks.InventoryTask;
import dgrxf.watercraft.enumeration.Alphabet;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;

public class ChestModule implements IBoatModule {

	@Override
	public ModuleType getModuleType() {
		return ModuleType.INVENTORY;
	}

	@Override
	public Block getBlockType() {
		return ModBlocks.chest;
	}

	@Override
	public int writeModuleInfoToNBT(NBTTagCompound tag, int startingPos) {
		tag.setString(Alphabet.values()[startingPos].toString(), this.getClass().getName());
		return 1;
	}

	@Override
	public void addBoatAI(BoatAITaskList list, AbstractBaseBoat boat, float f, Object... params) {
		list.addTask(new InventoryTask(boat, f, (Integer)params[0], params[1], (Integer)params[2]));
	}

}
