package dgrxf.watercraft.util;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.enumeration.Alphabet;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;

public class ModuleHelper {
	
	private static IBoatModule[] modules = new IBoatModule[0];
	
	
	/**
	 * Register your boat modules with this method.
	 */
	public static void registerModule(IBoatModule module){
		IBoatModule[] temp = modules.clone();
		IBoatModule[] tempArray = new IBoatModule[temp.length+1];
		for(int i = 0; i < temp.length; i++){
			tempArray[i] = temp[i];
		}
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
	 * @return the amount of registered modules
	 */
	public static int getModuleListLength(){
		return modules.length;
	}
	
	/**
	 * @return an array of classes containing the registered modules
	 */
	public static Class<? extends IBoatModule>[] getRegisteredModules(){
		Class<? extends IBoatModule>[] toReturn = new Class[modules.length];
		
		for(int i = 0; i < modules.length; ++i){
			toReturn[i] = modules[i].getClass();
		}
		
		return toReturn;
	}
	
	/**
	 * @param clazz is the class of the module you're looking for.
	 * @return the class of the module associated with the class you pass in, or null if that class is not registered.
	 */
	private static IBoatModule getModule(Class<? extends IBoatModule> clazz){
		
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
	 * @param clazz the class of the module you wish to call addBoatAI from
	 * @param list the class of the AI list you wish to add the AI to.
	 */
	
	public static void addBoatAI(Class<? extends IBoatModule> clazz, BoatAITaskList list, AbstractBaseBoat boat, float f){

		for(int i = 0; i < modules.length; ++i){
			if(modules[i].getClass() == clazz){
				modules[i].addBoatAI(list, boat, f);
			}
		}
	}
	/**
	 * @param clazz the class of the module you wish to call writeModuleInfoToNBT from
	 * @param tag the NBT tag compound you wish to write to
	 */
	public static int writeModuleInforToNBT(Class<? extends IBoatModule> clazz, NBTTagCompound tag, int startingPos){
		for(int x = 0; x < modules.length; x++){
			if(modules[x].getClass() == clazz){
				tag.setString(Alphabet.values()[startingPos].toString(), modules[x].getClass().getName());
				return 1;
			}
		}
		return 0;
	}
	
}
