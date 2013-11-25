package dgrxf.watercraft.module;

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
import dgrxf.watercraft.util.LogHelper;

public class ModuleRegistry {
	
	private static HashMap<Integer, ItemStack> registeredModules = new HashMap();
	protected static HashMap<Integer, IBoatModule> modules = new HashMap();
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
	
	public static final int isItemRegisteredAndGetID(ItemStack item){
		for(Integer i : registeredIDs){
			ItemStack temp = registeredModules.get(i);
			if(temp.isItemEqual(item)){
				return i;
			}
		}
		return -1;
	}
	

}
