package dgrxf.watercraft.item;

import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.ItemInfo;
import net.minecraft.item.Item;

public class Engine extends Item {

	public Engine(int id) {
		super(id);
		setUnlocalizedName(ItemInfo.ENGINE_UNLOCALIZED_NAME);
		setCreativeTab(Watercraft.creativeTab);
	}
	
	
	
}
