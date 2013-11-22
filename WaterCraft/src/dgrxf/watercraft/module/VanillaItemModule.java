package dgrxf.watercraft.module;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.block.Block;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.BoatAITaskBase;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;
import dgrxf.watercraft.util.LogHelper;

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
		BoatAITaskBase b = null;
		try{
			switch(args.length){
			case 0:
				b = (BoatAITaskBase) task.getDeclaredConstructor(AbstractBaseBoat.class, float.class).newInstance(boat, f);
				list.addTask(b);
				break;
			case 1:
				b = (BoatAITaskBase) task.getDeclaredConstructor(AbstractBaseBoat.class, float.class, args[0].getClass()).newInstance(boat, f, args[0]);
				list.addTask(b);
				break;
			case 2:
				b = (BoatAITaskBase) task.getDeclaredConstructor(AbstractBaseBoat.class, float.class, args[0].getClass(), args[1].getClass()).newInstance(boat, f, args[0], args[1]);
				list.addTask(b);
				break;
			case 3:
				b = (BoatAITaskBase) task.getDeclaredConstructor(AbstractBaseBoat.class, float.class, args[0].getClass(), args[1].getClass(), args[2].getClass()).newInstance(boat, f, args[0], args[1], args[2]);
				list.addTask(b);
				break;
			case 4:
				b = (BoatAITaskBase) task.getDeclaredConstructor(AbstractBaseBoat.class, float.class, args[0].getClass(), args[1].getClass(), args[2].getClass(), args[3].getClass()).newInstance(boat, f, args[0], args[1], args[2], args[3]);
				list.addTask(b);
				break;
			case 5:
				b = (BoatAITaskBase) task.getDeclaredConstructor(AbstractBaseBoat.class, float.class, args[0].getClass(), args[1].getClass(), args[2].getClass(), args[3].getClass(), args[4].getClass()).newInstance(boat, f, args[0], args[1], args[2], args[3], args[4]);
				list.addTask(b);
				break;
			default:
				LogHelper.severe("You attemped to create a VanillaItemModule with more than 5 parameters, this is not allowed.");
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
