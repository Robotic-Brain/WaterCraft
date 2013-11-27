package dgrxf.watercraft.module;

import java.util.Arrays;
import java.util.HashSet;

import net.minecraft.block.Block;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.BoatAITaskBase;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;

public class CustomModule implements IBoatModule {

	Block block;
	HashSet<ModuleType> moduleTypes = new HashSet<ModuleType>();
	HashSet<ModuleType> confModuleTypes = new HashSet<ModuleType>();
	Class task;
	String info = "";
	Object[] args;
	
	/**
	 * @param moduleTypes An array of ModuleTypes (if any) you wish your module to be of. Can be null.
	 * @param conModuleTypes An array of ModuleTypes (if any) you wish your module to conflict with. Can be null.
	 * @param block The block (if any) you wish to have render inside the boat. Can be null. If this does not equal null ModuleType.BLOCK will automatically be added to moduleTypes, if not already present.
	 * @param task The BoatAITaskBase class you wish for your module to have. Can be null.
	 * @param args The extra arguments for the AI Task you wish to have, everything after the priority. Can be empty or null.
	 */
	public CustomModule(ModuleType[] moduleTypes, ModuleType[] confModuleTypes, Block block, String info, Class<? extends BoatAITaskBase> task, Object... args){
		boolean declaresBlock = false;
		this.moduleTypes.addAll(Arrays.asList(moduleTypes));
		this.confModuleTypes.addAll(Arrays.asList(confModuleTypes));
		this.block = block;
		this.task = task;
		this.args = args;
		this.info = info;
		if(block != null){
			for(ModuleType m : this.moduleTypes){
				if(m == ModuleType.BLOCK){
					declaresBlock = true;
				}
			}
			if(!declaresBlock){
				this.moduleTypes.add(ModuleType.BLOCK);
				this.confModuleTypes.add(ModuleType.BLOCK);
			}
		}
	}
	
	@Override
	public ModuleType[] getModuleType() {
		return moduleTypes.toArray(new ModuleType[moduleTypes.size()]);
	}

	@Override
	public ModuleType[] getConflictingModuleTypes() {
		return confModuleTypes.toArray(new ModuleType[confModuleTypes.size()]);
	}
	
	@Override
	public Block getBlockType() {
		return block;
	}

	@Override
	public void addBoatAI(BoatAITaskList list, AbstractBaseBoat boat, float f) {
		if(task != null){
			BoatAITaskBase b = null;
			try{
				b = (BoatAITaskBase) task.getDeclaredConstructor(AbstractBaseBoat.class, Float.class, args.getClass()).newInstance(boat, f, args);
				list.addTask(b);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getModuleInfo() {
		return info;
	}

}
