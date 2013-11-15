package dgrxf.watercraft.item;

import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.ItemInfo;
import net.minecraft.item.Item;

public class ItemKey extends Item {

	public ItemKey() {
		super(ItemInfo.KEY_ID);
        setUnlocalizedName(ItemInfo.KEY_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.miscTab);
	}
	
	

}
