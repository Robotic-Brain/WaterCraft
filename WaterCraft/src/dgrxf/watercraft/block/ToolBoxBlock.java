package dgrxf.watercraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
<<<<<<< HEAD
=======
import net.minecraft.world.IBlockAccess;
>>>>>>> Commit
import net.minecraft.world.World;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.client.sound.Sounds;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

/**
 * Class Created By: Drunk Mafia (TDM) Class Last Modified By: Drunk Mafia (TDM)
 * 
 * Class Last Modified On: 11/09/2013 MM/DD/YYYY
 * 
 */

public class ToolBoxBlock extends WCBlock {
<<<<<<< HEAD
    
    public ToolBoxBlock() {
        super(BlockInfo.TOOLBOX_ID, Material.iron);
        setCreativeTab(Watercraft.creativeTab);
        setUnlocalizedName(BlockInfo.TOOLBOX_UNLOCALIZED_NAME);
        setBlockBounds(0.1F, 0F, 0.35F, 0.9F, 0.5F, 0.65F);
    }
    
    @Override
=======

	public ToolBoxBlock() {
		super(BlockInfo.TOOLBOX_ID, Material.iron);
		setCreativeTab(Watercraft.creativeTab);
		setUnlocalizedName(BlockInfo.TOOLBOX_UNLOCALIZED_NAME);
	}
	
	@Override
>>>>>>> Commit
    public boolean hasTileEntity(int metadata) {
        return true;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (world.isRemote)
            return;
        world.markBlockForUpdate(x, y, z);
    }
    
    /**
     * This can't access the metadata of the block, so It gets it from the tile entity
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
    	WCTileEntityToolBox tile = (WCTileEntityToolBox) world.getBlockTileEntity(x, y, z);
    	if(tile.blockMetadata == 2 || tile.blockMetadata == 3)
    		setBlockBounds(0.1F, 0F, 0.35F, 0.9F, 0.5F, 0.65F);
    	if(tile.blockMetadata == 4 || tile.blockMetadata == 5)
    		setBlockBounds(0.345F, 0F, 0.1F, 0.645F, 0.5F, 0.9F);
    }

    
    @Override
<<<<<<< HEAD
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote)
            return true;
        
        if (!player.isSneaking()) {
            FMLNetworkHandler.openGui(player, Watercraft.instance, GuiHandler.TOOLBOX_GUI_ID, world, x, y, z);
        } else {
            if (player.getCurrentEquippedItem() == null)
                pickUpToolBox(world, x, y, z, player);
        }
        return true;
    }
    
=======
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) return true;
		
		if(!player.isSneaking()){
			Sounds.TOOLBOX_OPENING.play(x, y, z, 2, 1);
			FMLNetworkHandler.openGui(player, Watercraft.instance, GuiHandler.TOOLBOX_GUI_ID, world, x, y, z); 
		}else{
			if(player.getCurrentEquippedItem() == null) pickUpToolBox(world, x, y, z, player);
		}
		return true;
	}

>>>>>>> Commit
    private void pickUpToolBox(World world, int x, int y, int z, EntityPlayer player) {
        WCTileEntityToolBox tile = (WCTileEntityToolBox) world.getBlockTileEntity(x, y, z);
        
        int size = tile.getSizeInventory();
        ItemStack[] tempInv = new ItemStack[size];
        
        for (int i = 0; i < size; i++) {
            if (tile.getStackInSlot(i) != null) {
                tempInv[i] = tile.getStackInSlot(i);
                tile.setInventorySlotContents(i, null);
            }
        }
        
        ItemStack toolBox = new ItemStack(this);
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList items = new NBTTagList();
        
        for (int i = 0; i < tempInv.length; i++) {
            ItemStack slot = tempInv[i];
            
            if (slot != null) {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);
                slot.writeToNBT(item);
                items.appendTag(item);
            }
        }
        compound.setTag("Items", items);
        compound.setString("playerName", tile.getPlayerName());
        toolBox.setTagCompound(compound);
        
        world.setBlockToAir(x, y, z);
        
        player.inventory.mainInventory[player.inventory.currentItem] = toolBox;
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
<<<<<<< HEAD
        TileEntity te = world.getBlockTileEntity(x, y, z);
        if (te != null && te instanceof IInventory) {
            IInventory inventory = (IInventory) te;
            
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack stack = inventory.getStackInSlotOnClosing(i);
                
                if (stack != null) {
                    float spawnX = x + world.rand.nextFloat();
                    float spawnY = y + world.rand.nextFloat();
                    float spawnZ = z + world.rand.nextFloat();
                    
                    EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);
                    
                    float mult = 0.05F;
                    
                    droppedItem.motionX = (-0.5F + world.rand.nextFloat()) * mult;
                    droppedItem.motionY = (4 + world.rand.nextFloat()) * mult;
                    droppedItem.motionZ = (-0.5F + world.rand.nextFloat()) * mult;
                    
                    world.spawnEntityInWorld(droppedItem);
                }
            }
        }
        super.breakBlock(world, x, y, z, id, meta);
=======
    	TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof IInventory) {
			IInventory inventory = (IInventory)te;
			
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack stack = inventory.getStackInSlotOnClosing(i);
				
				if (stack != null) {
					float spawnX = x + world.rand.nextFloat();
					float spawnY = y + world.rand.nextFloat();
					float spawnZ = z + world.rand.nextFloat();
					
					EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);
					
					float mult = 0.02F;
					
					droppedItem.motionX = (-0.5F + world.rand.nextFloat()) * mult;
					droppedItem.motionY = (4 + world.rand.nextFloat()) * mult;
					droppedItem.motionZ = (-0.5F + world.rand.nextFloat()) * mult;
					
					world.spawnEntityInWorld(droppedItem);
					if(i == 0)
						world.spawnEntityInWorld(new EntityItem(world, spawnX, spawnY, spawnZ, new ItemStack(this)));
				}
			}
		}
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int meta) {
        return Block.cloth.getIcon(0, 1);
>>>>>>> Commit
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public Icon getIcon(int par1, int par2) {
        
        return Block.cloth.getIcon(0, 14);
    }
    
    @Override
    public int getRenderType() {
        return RenderInfo.TOOLBOX_RENDER_ID;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new WCTileEntityToolBox();
    }
    
    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int par5, EntityPlayer player) {
        float spawnX = x + world.rand.nextFloat();
        float spawnY = y + world.rand.nextFloat();
        float spawnZ = z + world.rand.nextFloat();
        world.spawnEntityInWorld(new EntityItem(world, spawnX, spawnY, spawnZ, new ItemStack(this)));
    }
    
}
