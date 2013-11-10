package dgrxf.watercraft.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.tileentity.WCTileEntityBuoy;

public class ItemTelescope extends Item{

	private int tick;
	
	public ItemTelescope() {
		super(ItemInfo.TELESCOPE_ID);
		setUnlocalizedName(ItemInfo.TELESCOPE_UNLOCALIZED_NAME);
		setCreativeTab(Watercraft.creativeTab);
	}

}
