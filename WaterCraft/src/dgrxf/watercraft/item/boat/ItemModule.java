package dgrxf.watercraft.item.boat;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import dgrxf.watercraft.Watercraft;

/**
 * 
 * ItemModule
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ItemModule extends Item{

	public ItemModule(int id) {
		super(id);
		setCreativeTab(Watercraft.boatTab);
		hasSubtypes = true;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + stack.getItemDamage(); 
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		itemIcon = register.registerIcon("boat");
	}
	
	@Override	
	public void getSubItems(int val, CreativeTabs tabs, List list) {
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
		list.add(new ItemStack(this, 1, 2));
		list.add(new ItemStack(this, 1, 3));
	}
}
