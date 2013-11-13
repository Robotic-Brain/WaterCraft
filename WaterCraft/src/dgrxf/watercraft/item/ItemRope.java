package dgrxf.watercraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.ItemInfo;

public class ItemRope extends Item {
	public ItemRope() {
        super(ItemInfo.ROPE_ID);
        setUnlocalizedName(ItemInfo.ROPE_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.creativeTab);
    }

}

