package dgrxf.watercraft.interfaces;

import java.util.HashSet;

import net.minecraft.item.ItemStack;

/**
 * 
 * IModularBoat
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public interface IModularBoat {

	public HashSet getModuleList(ItemStack stack);
	
}
