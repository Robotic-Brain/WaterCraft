package dgrxf.watercraft.util;

import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.enumeration.Alphabet;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;
import dgrxf.watercraft.interfaces.IItemModule;

public class ModuleRegistry {
	
	private static HashSet<IBoatModule> modules = new HashSet();
	
	
	/**
	 * Register your boat modules with this method. Returns false if that module is already registered
	 */
	public static boolean registerModule(IBoatModule module){
		return modules.add(module);
	}
	
	/**
	 * returns true if the module associated with the class given is registered
	 * returns false otherwise
	 */
	public static boolean isModuleRegistered(Class<? extends IBoatModule> clazz){
		for(IBoatModule mods : modules){
			if(mods.getClass() == clazz){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return the amount of registered modules
	 */
	public static int getModuleListLength(){
		return modules.size();
	}
	
	/**
	 * @return an array of classes containing the registered modules
	 */
	public static Class<? extends IBoatModule>[] getRegisteredModules(){
		Class<? extends IBoatModule>[] toReturn = new Class[modules.size()];
		int i = 0;
		for(IBoatModule mods : modules){
			toReturn[i] = mods.getClass();
			i++;
		}
		
		return toReturn;
	}
	
	/**
	 * @param clazz is the class of the module you're looking for.
	 * @return the class of the module associated with the class you pass in, or null if that class is not registered.
	 */
	private static IBoatModule getModule(Class<? extends IBoatModule> clazz){
		for(IBoatModule mods : modules){
			if(mods.getClass() == clazz){
				return mods;
			}
		}
		return null;
	}
	
	/**
	 * @param clazz the class of the module you wish to call getModuleType from
	 * @return the getModuleType for the class, null if the class is not registered.
	 */
	public static ModuleType[] getModuleTypes(Class<? extends IBoatModule> clazz){
		for(IBoatModule mods : modules){
			if(mods.getClass() == clazz){
				return mods.getModuleType();
			}
		}
		return null;
	}
	/**
	 * 
	 * @param clazz the class of the module you wish to call getBlockType from
	 * @return the getBlockType for the class, null if the class is not registered, or the module does not have a block.
	 */
	public static Block getBlockType(Class<? extends IBoatModule> clazz){
		for(IBoatModule mods : modules){
			if(mods.getClass() == clazz){
				return mods.getBlockType();
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param clazz the class of the module you wish to call addBoatAI from
	 * @param list the class of the AI list you wish to add the AI to.
	 */
	
	public static void addBoatAI(Class<? extends IBoatModule> clazz, BoatAITaskList list, AbstractBaseBoat boat, float f){
		for(IBoatModule mods : modules){
			if(mods.getClass() == clazz){
				mods.addBoatAI(list, boat, f);
			}
		}
	}
	/**
	 * @param clazz the class of the module you wish to call writeModuleInfoToNBT from
	 * @param tag the NBT tag compound you wish to write to
	 * @param startingPos the value you wish to use for your nbt key.
	 * 
	 * @return true if the tag was written, false if not.
	 */
	public static boolean writeSetInfoToNBT(Class<? extends IBoatModule> clazz, NBTTagCompound tag, int startingPos){
		for(IBoatModule mods : modules){
			if(mods.getClass() == clazz){
				tag.setString(Alphabet.values()[startingPos].toString(), mods.getClass().getName());
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param set The HashSet that contains the module information you wish to write to the boat.
	 * @param item The ItemStack that contains the boat you wish to write information to.
	 */
	public static void writeSetToItemStackNBT(HashSet<String> set, ItemStack item) {
		
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagCompound innerTag = new NBTTagCompound();
		int i = 0;
		for(String s : set){
			innerTag.setString(Alphabet.values()[i].toString(), s);
			i++;
		}
		tag.setCompoundTag("Modules", innerTag);
		item.setTagCompound(tag);
	}

	
	/**
	 * @param mod The module you wish to check to see if it conflicts with other modules
	 * @param temp the HashSet of modules currently on the modular boat
	 * @return True if the modular tasks are in conflict or the IItemModules module class is not registered, this is determined by the catagories of modules for the module you pass in.<br><br>
	 * False if there is no conflict and the module can be placed on the modular boat.
	 */
	public static boolean doTasksConflict(IItemModule mod, HashSet<String> temp) {
		for(String s : temp){
			if(isModuleRegistered(mod.getBoatModule())){
				try {
					ModuleType[] mods1 = getModuleTypes((Class<? extends IBoatModule>) Class.forName(s));
					ModuleType[] mods2 = getModuleTypes(mod.getBoatModule());
					for(ModuleType mod1 : mods1){
						for(ModuleType mod2 : mods2){
							if(mod1 == mod2){
								return true;
							}
						}
					}
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			else{
				return true;
			}
		}
		
		return false;
	}
	
}
