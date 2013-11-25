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

public class ModuleRegistry {
	
	private static HashMap<Integer, ItemStack> registeredModules = new HashMap();
	private static HashMap<Integer, IBoatModule> modules = new HashMap();
	private static ArrayList<Integer> registeredIDs = new ArrayList();
	
	/**
	 * @param item The Item you wish to associate your module with.
	 * @param mod The module you wish to register.
	 * @return True if the module was registered successfully, false if not.
	 */
	public static final boolean registerModule(ItemStack itemStack, IBoatModule mod){		
		if(modules.containsKey(itemStack)){
			LogHelper.severe("You are attempting to register a module with to the ItemStack containing: " + itemStack.getDisplayName() + ", metadata: " + itemStack.getItemDamage() + "however this ItemStack is already in use and cannot be overwritten.");
			return false;
		}else{
			registeredModules.put(getNextID(), itemStack);
			modules.put(getNextID(), mod);
			registeredIDs.add(getNextID());
			return true;
		}
	}
	
	private static int getNextID(){
		return registeredIDs.size();
	}

	/**
	 * @param item The item you wish to see if it has been associated with a module
	 * @return True if the item has an associated module
	 */
	public static final boolean isItemRegistered(ItemStack item){
		for(Integer i : registeredIDs){
			ItemStack temp = registeredModules.get(i);
			if(temp.isItemEqual(item)){
				return true;
			}
		}
		return false;
	}
	
	private static final int isItemRegisteredAndGetID(ItemStack item){
		for(Integer i : registeredIDs){
			ItemStack temp = registeredModules.get(i);
			if(temp.isItemEqual(item)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * @param itemID The Item or Block ID associated with the module you wish to call getModuleType from
	 * @return The getModuleType for the module, returns an empty ModuleType[] if the class is not registered.
	 */
	public static final ModuleType[] getModuleTypes(ItemStack itemID){
		ModuleType[] temp = new ModuleType[0];
		int tempInt = isItemRegisteredAndGetID(itemID);
		if(tempInt != -1){
			temp = modules.get(tempInt).getModuleType();
		}
		return temp;
	}
	/**
	 * 
	 * @param itemID The Item or Block ID associated with the module you wish to call getBlockType from
	 * @return The getBlockType for the given id, null if the id of the Item/Block is not registered, or the module does not have a block.
	 */
	public static final Block getBlockType(ItemStack itemID){
		Block b = null;
		int tempInt = isItemRegisteredAndGetID(itemID);
		if(tempInt != -1){
			b = modules.get(tempInt).getBlockType();
		}
		return b;
	}
	
	/**
	 * Parses a string and returns an itemstack according to the string's details
	 * @param s The string you wish to convert to an itemstack
	 * @return null if s is null or if it is not the correct format, or returns an itemstack containing the item described in string s.
	 */
	public static final ItemStack parseStringToItemStack(String s){
		if(s == null || !s.contains(":")) return null;
		
		String[] temp = s.split(":");
		int id = Integer.parseInt(temp[0]);
		int meta = Integer.parseInt(temp[1]);
		return new ItemStack(id, 0, meta);
	}
	
	/**
	 * @param itemID The ItemStack associated with the module you wish to call addBoatAI from.
	 * @param list The class of the AI list you wish to add the AI to.
	 * @param boat The AbstractBaseBoat you wish to add a module to.
	 * @param priority The priority of the AI task you wish to add to the boat.
	 */
	
	public static final void addBoatAI(ItemStack itemID, BoatAITaskList list, AbstractBaseBoat boat, float priority){
		int tempInt = isItemRegisteredAndGetID(itemID);
		if(tempInt != -1){
			modules.get(tempInt).addBoatAI(list, boat, priority);
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
	 * @param mod The ItemStack associated with the module you wish to check to see if it conflicts with other modules
	 * @param temp the HashSet of modules currently on the modular boat
	 * @return True if the modular tasks are in conflict or the module associated with the Item is not registered, this is determined by the catagories of modules for the module you pass in.<br><br>
	 * False if there is no conflict and the module can be placed on the modular boat.
	 */
	public static final boolean doTasksConflict(ItemStack mod, HashSet<String> temp) {
		boolean toReturn = false;
			for(String s : temp){
				int tempInt = isItemRegisteredAndGetID(mod);
				int tempInt2 = isItemRegisteredAndGetID(parseStringToItemStack(s));
				if(tempInt != -1 && tempInt2 != -1){
					toReturn = doTasksConflict(modules.get(tempInt), modules.get(tempInt2));
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
