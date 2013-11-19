package dgrxf.watercraft.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.block.Block;
import dgrxf.watercraft.entity.boat.ai.tasks.BoatAITaskBase;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;

public class ModuleHelper {
	
	private static IBoatModule[] modules = new IBoatModule[0];
	
	
	/**
	 * Register your boat modules with this method.
	 */
	public static void registrterModuler(IBoatModule module){
		IBoatModule[] temp = modules.clone();
		IBoatModule[] tempArray = new IBoatModule[temp.length+1];
		tempArray[tempArray.length-1] = module;
		modules = tempArray;
	}
	/**
	 * @param clazz is the class of the module you're looking for.
	 * @return the id of the module associated with the class you pass in, or -1 if that class is not registered.
	 */
	public static int getModuleID(Class<? extends IBoatModule> clazz){
		for(int i = 0; i < modules.length; ++i){
			if(modules[i].getClass() == clazz){
				return i;
			}
		}
		return -1;
	}
	
	
	/**
	 * @param clazz is the class of the module you're looking for.
	 * @return the class of the module associated with the class you pass in, or null if that class is not registered.
	 */
	public static IBoatModule getModuleClass(Class<? extends IBoatModule> clazz){
		
		return getModuleInstance(getModuleID(clazz));
	}
	
	/**
	 * @param moduleID is the id of the module you're looking for.
	 * @return the class of the module associated with the id you pass in, or null if that class is not registered.
	 */
	public static IBoatModule getModuleInstance(int moduleID){
		
		if(moduleID < modules.length && moduleID > 0){
			return modules[moduleID];
		}
		
		return null;
	}
	/**
	 * @param clazz the class of the module you wish to call getModuleType from
	 * @return the getModuleType for the class, null if the class is not registered.
	 */
	public static ModuleType getModuleType(Class<? extends IBoatModule> clazz){
		int x = 0;
		for(IBoatModule mods : modules){
			if(modules[x].getClass() == clazz){
				return modules[x].getModuleType();
			}
			x++;
		}
		return null;
	}
	/**
	 * 
	 * @param clazz the class of the module you wish to call getBlockType from
	 * @return the getBlockType for the class, null if the class is not registered, or the module does not have a block.
	 */
	public static Block getBlockType(Class<? extends IBoatModule> clazz){
		int x = 0;
		for(IBoatModule mods : modules){
			if(modules[x].getClass() == clazz){
				return modules[x].getBlockType();
			}
			x++;
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param clazz clazz the class of the module you wish to call addBoatAI from
	 * @param list the class of the AI list you wish to add the AI to.
	 */
	
	public static void addBoatAI(Class<? extends IBoatModule> clazz, List<Class<? extends BoatAITaskBase>> list){
		int x = 0;
		for(IBoatModule mods : modules){
			if(modules[x].getClass() == clazz){
				modules[x].addBoatAI(list);
				break;
			}
			if(x == modules.length){
				return;
			}
			x++;
		}
	}
	
}
