package dgrxf.watercraft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.enumeration.Alphabet;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;
import dgrxf.watercraft.interfaces.IItemModule;
import dgrxf.watercraft.module.VanillaItemModule;

public class ModuleRegistry {
	
	private static HashSet<IBoatModule> modules = new HashSet();
	private static HashMap<Integer, VanillaItemModule> vModules = new HashMap();
	private static ArrayList<Integer> ids = new ArrayList();
	
	/**
	 * Register your boat modules with this method. Returns false if that module is already registered
	 */
	public static boolean registerModule(IBoatModule module){
		return modules.add(module);
	}
	
	public static boolean registerVanillaModule(VanillaItemModule mod, int itemID){
		if(vModules.containsKey(itemID)){
			LogHelper.severe("You are attempting to register a vanilla module with the item ID of " + itemID + ", however this item is already in use and cannot be overwritten.");
			return false;
		}else{
			vModules.put(itemID, mod);
			ids.add(itemID);
			return true;
		}
	}
	
	public static boolean isItemAVanillaModule(Item item){
		return vModules.containsKey(item.itemID);
	}
	
	private static boolean isClassVanillaModule(Class<? extends IBoatModule> clazz){
		return clazz == VanillaItemModule.class;
	}
	
	/**
	 * returns true if the module associated with the class given is registered
	 * returns false otherwise
	 */
	public static boolean isModuleRegistered(Class<? extends IBoatModule> clazz){
		if(!isClassVanillaModule(clazz)){
			for(IBoatModule mods : modules){
				if(mods.getClass() == clazz){
					return true;
				}
			}
		}else{
			for(Integer i : ids){
				if(vModules.get(i).getClass() == clazz){
					return true;
				}
			}
		}
		return false;
	}
	
	public static Class getVanillaModuleFromID(Item item){
		if(vModules.containsKey(item.itemID)){
			return vModules.get(item.itemID).getClass();
		}
		return null;
	}
	
	/**
	 * @return the amount of registered modules
	 */
	public static int getRegisteredModuleCount(){
		return modules.size();
	}
	
	/**
	 * @return an array of classes containing the registered modules
	 */
	public static Class<? extends IBoatModule>[] getRegisteredModules(){
		ArrayList l = new ArrayList();
		for(IBoatModule mods : modules){
			l.add(mods.getClass());
		}
		for(Integer i : ids){
			l.add(vModules.get(i));
		}
		
		return (Class<? extends IBoatModule>[]) l.toArray();
	}
	
	/**
	 * @param clazz is the class of the module you're looking for.
	 * @return the class of the module associated with the class you pass in, or null if that class is not registered.
	 */
	private static IBoatModule getModule(Class<? extends IBoatModule> clazz){
		IBoatModule temp = null;
		if(!isClassVanillaModule(clazz)){
			for(IBoatModule mods : modules){
				if(mods.getClass() == clazz){
					temp = mods;
				}
			}
		}else{
			for(Integer i : ids){
				if(vModules.get(i).getClass() == clazz){
					temp = vModules.get(i);
				}
			}
		}
		return temp;
	}
	
	/**
	 * @param clazz the class of the module you wish to call getModuleType from
	 * @return the getModuleType for the class, null if the class is not registered.
	 */
	public static ModuleType[] getModuleTypes(Class<? extends IBoatModule> clazz){
		ModuleType[] temp = null;
		if(!isClassVanillaModule(clazz)){
			for(IBoatModule mods : modules){
				if(mods.getClass() == clazz){
					temp = mods.getModuleType();
				}
			}
		}else{
			for(Integer i : ids){
				if(vModules.get(i).getClass() == clazz){
					temp = vModules.get(i).getModuleType();
				}
			}
		}
		return temp;
	}
	/**
	 * 
	 * @param clazz the class of the module you wish to call getBlockType from
	 * @return the getBlockType for the class, null if the class is not registered, or the module does not have a block.
	 */
	public static Block getBlockType(Class<? extends IBoatModule> clazz){
		Block b = null;
		if(!isClassVanillaModule(clazz)){
			for(IBoatModule mods : modules){
				if(mods.getClass() == clazz){
					b = mods.getBlockType();
				}
			}
		}else{
			for(Integer i : ids){
				if(vModules.get(i).getClass() == clazz){
					b = vModules.get(i).getBlockType();
				}
			}
		}
		return b;
	}
	
	/**
	 * 
	 * @param clazz the class of the module you wish to call addBoatAI from
	 * @param list the class of the AI list you wish to add the AI to.
	 */
	
	public static void addBoatAI(Class<? extends IBoatModule> clazz, BoatAITaskList list, AbstractBaseBoat boat, float f){
		if(!isClassVanillaModule(clazz)){
			for(IBoatModule mods : modules){
				if(mods.getClass() == clazz){
					mods.addBoatAI(list, boat, f);
				}
			}
		}else{
			for(Integer i : ids){
				if(vModules.get(i).getClass() == clazz){
					vModules.get(i).addBoatAI(list, boat, f);
				}
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
		boolean temp = false;
		if(!isClassVanillaModule(clazz)){
			for(IBoatModule mods : modules){
				if(mods.getClass() == clazz){
					tag.setString(Alphabet.values()[startingPos].toString(), mods.getClass().getName());
					temp = true;
				}
			}
		}else{
			for(Integer i : ids){
				if(vModules.get(i).getClass() == clazz){
					tag.setString(Alphabet.values()[startingPos].toString(), vModules.get(i).getClass().getName());
					temp = true;
				}
			}
		}
		return temp;
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
	public static boolean doTasksConflict(Item mod, HashSet<String> temp) {
		boolean toReturn = false;
		if(!isClassVanillaModule((Class<? extends IBoatModule>) mod.getClass())){
			for(String s : temp){
				if(isModuleRegistered(((IItemModule)mod).getBoatModule())){
					try {
						ModuleType[] mods1 = getModuleTypes((Class<? extends IBoatModule>) Class.forName(s));
						ModuleType[] mods2 = getModuleTypes(((IItemModule)mod).getBoatModule());
						for(ModuleType mod1 : mods1){
							for(ModuleType mod2 : mods2){
								if(mod1 == mod2){
									toReturn =  true;
								}
							}
						}
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				else{
					toReturn = true;
				}
			}
		}else{
			for(String s : temp){
				if(vModules.containsKey(mod.itemID)){
					try {
						ModuleType[] mods1 = getModuleTypes((Class<? extends IBoatModule>) Class.forName(s));
						ModuleType[] mods2 = getModuleTypes(vModules.get(mod.itemID).getClass());
						for(ModuleType mod1 : mods1){
							for(ModuleType mod2 : mods2){
								if(mod1 == mod2){
									toReturn =  true;
								}
							}
						}
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return toReturn;
	}
	
}
