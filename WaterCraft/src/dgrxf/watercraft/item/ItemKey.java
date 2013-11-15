package dgrxf.watercraft.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.interfaces.ILockableBlock;
import dgrxf.watercraft.lib.ItemInfo;

public class ItemKey extends Item {

	public ItemKey() {
		super(ItemInfo.KEY_ID);
        setUnlocalizedName(ItemInfo.KEY_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.miscTab);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (player.isSneaking()) {
				TileEntity te = world.getBlockTileEntity(x, y, z);
				
				if (te instanceof ILockableBlock) {
					int code = ((ILockableBlock)te).getCode();
					
					if (code == stack.getItemDamage()) {
						 ((ILockableBlock)te).setLocked(false);
						 ((ILockableBlock)te).setCode(-1);
						 world.markBlockForUpdate(x, y, z);

						 EntityItem entityitem = new EntityItem(world, x, y + 1, z, new ItemStack(ModItems.padlock, 1, code));
				         entityitem.delayBeforeCanPickup = 10;
				         world.spawnEntityInWorld(entityitem);
					}
				}
			}
		}
		
		return true;
	}
}
