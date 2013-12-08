package dgrxf.watercraft.module;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import dgrxf.watercraft.interfaces.IBoatModule;
import dgrxf.watercraft.util.TranslationHelper;

/**
 * 
 * ModuleRegistry
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
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
					throw new RuntimeException(TH.translate(TH.MODULE_DUPLICATE_EXCEPTION, m.getName(), itemStack.getDisplayName(), getModulesMod(temp).getName()));
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
				throw new RuntimeException(TH.translate(TH.MODULE_INVALID_MOD_EXCEPTION, modID.getClass().getSimpleName(), itemStack.getDisplayName()));
			} catch(RuntimeException e){
				e.printStackTrace();
				return false;
			}
		}
	}
	
	private static int getNextID(){
		return registeredIDs.size();
	}
	
	public static final ModContainer getModulesMod(ItemStack item){
		int temp = isItemRegisteredAndGetID(item);
		return temp == -1 ? null : getModulesMod(temp);
	}
	
	public static final ModContainer getModulesMod(int id){
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

	public static final ItemStack[] getRegisteredItemStacks(){
		ArrayList<ItemStack> temp = new ArrayList();
		for(Integer i : registeredIDs){
			temp.add(registeredModules.get(i));
		}
		return temp.toArray(new ItemStack[temp.size()]);
	}
	
}
