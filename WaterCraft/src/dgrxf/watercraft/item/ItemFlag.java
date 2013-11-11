package dgrxf.watercraft.item;

import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.ItemInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemFlag extends Item{
	public ItemFlag() {
		super(ItemInfo.FLAG_ID);
		setUnlocalizedName(ItemInfo.FLAG_UNLOCALIZED_NAME);
		setCreativeTab(Watercraft.creativeTab);
		hasSubtypes = true;
		maxStackSize = 1;
	}	
	
	
}
