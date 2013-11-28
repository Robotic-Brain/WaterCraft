package dgrxf.watercraft.module;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import dgrxf.watercraft.interfaces.IBoatModule;

public class ModuleRegistry {
	
	private static HashMap<Integer, ItemStack> registeredModules = new HashMap();
	private static HashMap<Integer, ModContainer> modByID = new HashMap();
	protected static HashMap<Integer, IBoatModule> modules = new HashMap();
	private static ArrayList<Integer> registeredIDs = new ArrayList();
	
	/**
	 * @param item The Item you wish to associate your module with.
	 * @param mod The module you wish to register.
	 * @return True if the module was registered successfully, false if not.
	 */
	public static final boolean registerModule(ItemStack itemStack, IBoatModule mod, Object modID){
		int temp = isItemRegisteredAndGetID(itemStack);

		ModContainer m = FMLCommonHandler.instance().findContainerFor(modID);
		if(m != null){
			if(temp != -1){
				try {
					throw new RuntimeException("The mod " + m.getName() + " is attempting to register a module with to the item named \"" + itemStack.getDisplayName() + "\", however, this item is already registered by " + getModulesMod(temp).getName() + " and cannot be overwritten.");
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}else{
				modByID.put(getNextID(), m);
				registeredModules.put(getNextID(), itemStack);
				modules.put(getNextID(), mod);
				registeredIDs.add(getNextID());
				return true;
			}
			return false;
		}else{
			try{
				throw new RuntimeException("The class " + modID.getClass().getSimpleName() + " is not a registered Forge Modloader mod container, the module with the item " + itemStack.getDisplayName() + " will not be registered.");
			} catch(RuntimeException e){
				e.printStackTrace();
				return false;
			}
		}
	}
	
	private static int getNextID(){
		return registeredIDs.size();
	}
	
	public static ModContainer getModulesMod(int id){
		return modByID.get(id);
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
