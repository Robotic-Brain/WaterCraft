package dgrxf.watercraft.item;

import dgrxf.watercraft.Watercraft;
import net.minecraft.item.ItemBlock;

public class ItemBlockToolBox extends ItemBlock {

	public ItemBlockToolBox(int id) {
		super(id);
		this.setCreativeTab(Watercraft.tab);
	}
}
