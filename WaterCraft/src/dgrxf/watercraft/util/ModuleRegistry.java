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
import dgrxf.watercraft.module.CustomModule;

public class ModuleRegistry {
	
	private static HashMap<Integer, IBoatModule> modules = new HashMap();
	private static ArrayList<Integer> ids = new ArrayList();
	
	/**
	 * @param item The Item you wish to associate your module with.
	 * @param mod The module you wish to register.
	 * @return True if the module was registered successfully, false if not.
	 */
	public static final boolean registerModule(Item item, IBoatModule mod){
		return registerModule(item.itemID, mod);
	}
	
	/**
	 * @param item The Block you wish to associate your module with.
	 * @param mod The module you wish to register.
	 * @return True if the module was registered successfully, false if not.
	 */
	public static final boolean registerModule(Block block, IBoatModule mod){
		return registerModule(block.blockID, mod);
	}
	
	private static boolean registerModule(int id, IBoatModule mod){
		if(modules.containsKey(id)){
			LogHelper.severe("You are attempting to register a module with to the Item or Block with the id" + id + ", however this Item or Block is already in use and cannot be overwritten.");
			return false;
		}else{
			modules.put(id, mod);
			ids.add(id);
			return true;
		}
	}

	/**
	 * @param item The item you wish to see if it has been associated with a module
	 * @return True if the item has an associated module
	 */
	public static final boolean isItemRegistered(Item item){
		return modules.containsKey(item.itemID);
	}
	
	/**
	 * @param block The block you wish to see if it has been associated with a module
	 * @return True if the block has an associated module
	 */
	public static final boolean isItemRegistered(Block block){
		return modules.containsKey(block.blockID);
	}
	
	/**
	 * @param itemID The Item or Block ID associated with the module you wish to call getModuleType from
	 * @return The getModuleType for the module, returns an empty ModuleType[] if the class is not registered.
	 */
	public static final ModuleType[] getModuleTypes(int itemID){
		ModuleType[] temp = new ModuleType[0];
		for(int id : ids){
			if(itemID == id){
				temp = modules.get(id).getModuleType();
			}
		}
		
		return temp;
	}
	/**
	 * 
	 * @param itemID The Item or Block ID associated with the module you wish to call getBlockType from
	 * @return The getBlockType for the given id, null if the id of the Item/Block is not registered, or the module does not have a block.
	 */
	public static final Block getBlockType(int itemID){
		Block b = null;
		for(int id : ids){
			if(id == itemID){
				b = modules.get(id).getBlockType();
			}
		}
		return b;
	}
	
	/**
	 * @param itemID The Item or Block ID associated with the module you wish to call addBoatAI from.
	 * @param list The class of the AI list you wish to add the AI to.
	 * @param boat The AbstractBaseBoat you wish to add a module to.
	 * @param priority The priority of the AI task you wish to add to the boat.
	 */
	
	public static final void addBoatAI(int itemID, BoatAITaskList list, AbstractBaseBoat boat, float priority){
		for(int id : ids){
			if(itemID == id){
				modules.get(id).addBoatAI(list, boat, priority);
			}
		}
	}
	
	/**
	 * @param set The HashSet that contains the module information you wish to write to the boat.
	 * @param item The ItemStack that contains the boat you wish to write information to.
	 */
	public static final void writeSetToItemStackNBT(HashSet<String> set, ItemStack item) {
		
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
	 * @param mod The Item associated with the module you wish to check to see if it conflicts with other modules
	 * @param temp the HashSet of modules currently on the modular boat
	 * @return True if the modular tasks are in conflict or the module associated with the Item is not registered, this is determined by the catagories of modules for the module you pass in.<br><br>
	 * False if there is no conflict and the module can be placed on the modular boat.
	 */
	public static final boolean doTasksConflict(Item mod, HashSet<String> temp) {
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
	
	/**
	 * @param mod The Block associated with the module you wish to check to see if it conflicts with other modules
	 * @param temp the HashSet of modules currently on the modular boat
	 * @return True if the modular tasks are in conflict or the module associated with the Item is not registered, this is determined by the catagories of modules for the module you pass in.<br><br>
	 * False if there is no conflict and the module can be placed on the modular boat.
	 */
	public static final boolean doTasksConflict(Block mod, HashSet<String> temp) {
		boolean toReturn = false;
			for(String s : temp){
				if(modules.containsKey(mod.blockID)){
					toReturn = doTasksConflict((modules.get(mod.blockID)), modules.get(Integer.parseInt(s)));
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
