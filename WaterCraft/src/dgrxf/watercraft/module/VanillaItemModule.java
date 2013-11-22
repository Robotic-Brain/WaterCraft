package dgrxf.watercraft.module;

import net.minecraft.block.Block;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.BoatAITaskBase;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;

public class VanillaItemModule implements IBoatModule {

	Block block;
	ModuleType[] moduleTypes;
	Class task;
	Object[] args;
	
	public VanillaItemModule(ModuleType[] moduleTypes, Block block, Class<? extends BoatAITaskBase> task, Object... args){
		this.moduleTypes = moduleTypes;
		this.block = block;
		this.task = task;
		this.args = args;
	}
	
	@Override
	public ModuleType[] getModuleType() {
		return moduleTypes;
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
}
