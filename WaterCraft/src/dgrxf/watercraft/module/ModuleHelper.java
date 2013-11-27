package dgrxf.watercraft.module;

import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.enumeration.Alphabet;
import dgrxf.watercraft.enumeration.ModuleType;
import dgrxf.watercraft.interfaces.IBoatModule;

public class ModuleHelper extends ModuleRegistry{
	
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
		ModuleType[] mods1, mods2, cMods1, cMods2;
		mods1 = mod1.getModuleType();
		mods2 = mod2.getModuleType();
		cMods1 = mod1.getConflictingModuleTypes();
		cMods2 = mod2.getConflictingModuleTypes();
		
		for(ModuleType m1 : mods1){
			for (ModuleType cM2 : cMods2){
				if(m1 == cM2) return true;
			}
		}
		
		for(ModuleType m2 : mods2){
			for(ModuleType cM1 : cMods1){
				if(m2 == cM1) return true;
			}
		}
		
		return false;
	}

	public static final String getModuleInfo(ItemStack item){
		int temp = isItemRegisteredAndGetID(item);
		return temp == -1 ? "" : modules.get(temp).getModuleInfo();
	}
	
}
