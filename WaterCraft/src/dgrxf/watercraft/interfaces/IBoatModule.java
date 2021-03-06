package dgrxf.watercraft.interfaces;

import net.minecraft.block.Block;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.enumeration.ModuleType;

/**
 * 
 * IBoatModule
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public interface IBoatModule {
	
	/**
	 * @return the types of modules you're adding
	 */
	public ModuleType[] getModuleType();
	
	/**
	 * @return the types of conflicting modules
	 */
	public ModuleType[] getConflictingModuleTypes();
	
	/**
	 * If your module type is BLOCK return the block here, if not return null
	 */
	public Block getBlockType();
	
	/**
	 * If your module adds custom AI add it with this.
	 */
	public void addBoatAI(BoatAITaskList list, AbstractBaseBoat boat, float f);

	public ICraneAction[] getCraneActions();
	
	public String getModuleInfo();
}
