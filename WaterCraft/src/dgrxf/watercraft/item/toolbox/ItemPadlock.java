package dgrxf.watercraft.item.toolbox;

import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.interfaces.ILockableBlock;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By:Drunk Mafia Class Last Edited On:14/06/2013 MM/DD/YYYYY
 * 
 */

public class ItemPadlock extends Item{

	public ItemPadlock() {
		super(ItemInfo.PADLOCK_ID);
		setUnlocalizedName(ItemInfo.PADLOCK_UNLOCALIZED_NAME);
		setCreativeTab(Watercraft.miscTab);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return false;
		System.out.println("Right click");
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if(tile instanceof ILockableBlock && !((ILockableBlock)tile).isLocked()){
			//System.out.println("Toolbox found, placing padlock");
			((ILockableBlock)tile).setLocked(true);
			world.markBlockForUpdate(x, y, z);
			
			if (!player.capabilities.isCreativeMode) {
				stack.stackSize--;
			}
		}

		return true;
	}
}
