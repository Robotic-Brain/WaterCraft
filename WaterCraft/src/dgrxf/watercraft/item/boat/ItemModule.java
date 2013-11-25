package dgrxf.watercraft.item.boat;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.ItemInfo;

public class ItemModule extends Item{

	public ItemModule() {
		super(ItemInfo.BOAT_HULL_ID);
		setCreativeTab(Watercraft.boatTab);
		hasSubtypes = true;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ItemInfo.BOAT_HULL_UNLOCALIZED_NAME + stack.getItemDamage(); 
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
