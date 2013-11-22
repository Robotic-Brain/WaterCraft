package dgrxf.watercraft.interfaces;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.enumeration.ModuleType;

public interface IBoatModule {
	
	/**
	 * @return the types of modules you're adding
	 */
	public ModuleType[] getModuleType();
	
	/**
	 * If your module type is BLOCK return the block here, if not return null
	 */
	public Block getBlockType();
	
	/**
	 * If your module adds custom AI add it with this.
	 */
	public void addBoatAI(BoatAITaskList list, AbstractBaseBoat boat, float f);
}
