package dgrxf.watercraft.item;

import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.ItemInfo;
import net.minecraft.item.Item;

public class ItemKey extends Item {

	public ItemKey(int par1) {
		super(ItemInfo.TELESCOPE_ID);
        setUnlocalizedName(ItemInfo.TELESCOPE_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.miscTab);
	}
	
	

}
