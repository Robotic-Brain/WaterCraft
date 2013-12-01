package dgrxf.watercraft.item.boat;

import dgrxf.watercraft.Watercraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

/**
 * 
 * ItemEngine
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ItemEngine extends Item{

	public ItemEngine(int id) {
		super(id);
		setCreativeTab(Watercraft.boatTab);
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return Item.boat.getIcon(stack, pass);
	}
}
