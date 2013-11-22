package dgrxf.watercraft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.enumeration.Alphabet;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;
import dgrxf.watercraft.module.VanillaItemModule;

public class ModuleRegistry {
	
	private static HashMap<Integer, IBoatModule> modules = new HashMap();
	private static ArrayList<Integer> ids = new ArrayList();
	
	public static boolean registerModule(Item item, IBoatModule mod){
		if(modules.containsKey(item.itemID)){
			LogHelper.severe("You are attempting to register a module with to the Item " + item.getUnlocalizedName() + ", however this Item is already in use and cannot be overwritten.");
			return false;
		}else{
			modules.put(item.itemID, mod);
			ids.add(item.itemID);
			return true;
		}
	}
	
	public static boolean registerModule(Block block, IBoatModule mod){
		if(modules.containsKey(block.blockID)){
			LogHelper.severe("You are attempting to register a module with to the Block " + block.getUnlocalizedName() + ", however this Item is already in use and cannot be overwritten.");
			return false;
		}else{
			modules.put(block.blockID, mod);
			ids.add(block.blockID);
			return true;
		}
	}
	
	public static boolean isItemRegistered(Item item){
		return modules.containsKey(item.itemID);
	}
	
	/**
	 * @return the amount of registered modules
	 */
	public static int getNextModuleID(){
		return modules.size();
	}
	
	/**
	 * @param clazz the class of the module you wish to call getModuleType from
	 * @return the getModuleType for the class, null if the class is not registered.
	 */
	public static ModuleType[] getModuleTypes(int itemID){
		ModuleType[] temp = null;
		for(int id : ids){
			if(itemID == id){
				temp = modules.get(id).getModuleType();
			}
		}
		
		return temp;
	}
	/**
	 * 
	 * @param clazz the class of the module you wish to call getBlockType from
	 * @return the getBlockType for the class, null if the class is not registered, or the module does not have a block.
	 */
	public static Block getBlockType(int itemID){
		Block b = null;
		for(int id : ids){
			if(id == itemID){
				b = modules.get(id).getBlockType();
			}
		}
		return b;
	}
	
	/**
	 * 
	 * @param clazz the class of the module you wish to call addBoatAI from
	 * @param list the class of the AI list you wish to add the AI to.
	 */
	
	public static void addBoatAI(int itemID, BoatAITaskList list, AbstractBaseBoat boat, float f){
		for(int id : ids){
			if(itemID == id){
				modules.get(id).addBoatAI(list, boat, f);
			}
		}
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
			for(String s : temp){
				if(modules.containsKey(mod.itemID)){
					toReturn = doTasksConflict((modules.get(mod.itemID)), modules.get(Integer.parseInt(s)));
					if(toReturn){
						break;
					}
				}else{
					toReturn = true;
				}
		}
			
		return toReturn;
	}
	
	private static boolean doTasksConflict(IBoatModule mod1, IBoatModule mod2){
		ModuleType[] mods1, mods2;
		mods1 = mod1.getModuleType();
		mods2 = mod2.getModuleType();
		
		for(ModuleType m1 : mods1){
			for(ModuleType m2 : mods2){
				if(m1 == m2){
					return true;
				}
			}
		}
		
		return false;
	}

}
