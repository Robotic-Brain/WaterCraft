package dgrxf.watercraft.interfaces;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.BoatAITaskBase;
import dgrxf.watercraft.enumeration.ModuleType;

public interface IBoatModule {

	public ModuleType getModuleType();
	
	/**
	 * If your module type is BLOCK return the block here, if not return null
	 */
	public Block getBlockType();
	
	/**
	 * If your module adds custom AI add it with this.
	 */
	public void addBoatAI(BoatAITaskList list, AbstractBaseBoat boat, float f, Object... obj);
	
	public int writeModuleInfoToNBT(NBTTagCompound tag, int startingPos);
}
