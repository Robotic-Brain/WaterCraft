package dgrxf.watercraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

/**
 * Class Created By: Drunk Mafia (TDM) Class Last Modified By: Drunk Mafia (TDM)
 * 
 * Class Last Modified On: 11/09/2013 MM/DD/YYYY
 * 
 */

public class ToolBoxBlock extends WCBlock {

	public ToolBoxBlock() {
		super(BlockInfo.TOOLBOX_ID, Material.iron);
		setCreativeTab(Watercraft.creativeTab);
		setBlockBounds(0.1F, 0F, 0.35F, 0.9F, 0.5F, 0.65F);
	}
	
	@Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }   
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
    	if(world.isRemote) return;
    	world.markBlockForUpdate(x, y, z);
    }
    
    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) return true;

		if(!player.isSneaking()) FMLNetworkHandler.openGui(player, Watercraft.instance, GuiHandler.TOOLBOX_GUI_ID, world, x, y, z); else{
			if(player.getCurrentEquippedItem() == null) pickUpToolBox(world, x, y, z, player);
		}
		return true;
	}
    
    private void pickUpToolBox(World world, int x, int y, int z, EntityPlayer player) {
    	WCTileEntityToolBox tile = (WCTileEntityToolBox) world.getBlockTileEntity(x, y, z);
    	
    	world.destroyBlock(x, y, z, false);
    	
    	int size = tile.getSizeInventory();
    	ItemStack[] tempInv = new ItemStack[size];
    	
    	for(int i = 0; i < size; i++){
    		if(tile.getStackInSlot(i) != null)
    			tempInv[i] = tile.getStackInSlot(i);
    	}
    	
    	ItemStack toolBox = new ItemStack(this);
    	NBTTagCompound compound = new NBTTagCompound();
    	NBTTagList items = new NBTTagList();
		
		for (int i = 0; i < tempInv.length; i++) {		
			ItemStack slot = tempInv[i];
			
			if (slot != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);
				slot.writeToNBT(item);
				items.appendTag(item);
			}
		}
		compound.setTag("Items", items);
		toolBox.setTagCompound(compound);
		
		player.inventory.mainInventory[player.inventory.currentItem] = toolBox;
	}

	@Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return RenderInfo.TOOLBOX_RENDER_ID;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new WCTileEntityToolBox();
    }

}
