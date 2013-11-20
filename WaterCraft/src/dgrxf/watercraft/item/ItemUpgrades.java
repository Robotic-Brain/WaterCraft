package dgrxf.watercraft.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.tileentity.controlunit.WCTileEntityControlUnitDock;

public class ItemUpgrades extends Item{

	public ItemUpgrades() {
		super(ItemInfo.UPGRADE_ID);
		setUnlocalizedName(ItemInfo.UPGRADE_UNLOCALIZED_NAME);
		setCreativeTab(Watercraft.buoyTab);
		setMaxStackSize(16);
		setHasSubtypes(true);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return false;
		if(world.getBlockId(x, y, z) == BlockInfo.CONTROL_UNIT_DOCK_ID){			
			WCTileEntityControlUnitDock tile = (WCTileEntityControlUnitDock) world.getBlockTileEntity(x, y, z);
			if(tile.canBeUpgraded(stack)){
				if(!player.capabilities.isCreativeMode){
					stack.stackSize--;
				}
			}
		}
		return true;
	}
	
	@Override
	public void getSubItems(int val, CreativeTabs creativeTab, List list) {
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
		list.add(new ItemStack(this, 1, 2));
	}
}
