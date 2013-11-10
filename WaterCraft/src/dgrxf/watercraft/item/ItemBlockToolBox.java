package dgrxf.watercraft.item;

import java.util.List;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.tileentity.WCTileEntityControlUnitDock;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;
import dgrxf.watercraft.util.RotationHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Class Created By: Drunk Mafia (TDM) Class Last Modified By: Drunk Mafia (TDM)
 * 
 * Class Last Modified On: 11/09/2013 MM/DD/YYYY
 * 
 */

public class ItemBlockToolBox extends ItemBlock {
<<<<<<< HEAD
    
    public ItemBlockToolBox(int id) {
        super(id);
        this.setCreativeTab(Watercraft.creativeTab);
        maxStackSize = 1;
    }
    
    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        if (world.isRemote)
            return true;
        if(!world.isBlockSolidOnSide(x, y-1, z, ForgeDirection.UP)) return false;
        
        world.setBlock(x, y, z, ModBlocks.toolbox.blockID);
        WCTileEntityToolBox tile = (WCTileEntityToolBox) world.getBlockTileEntity(x, y, z);
        if (stack.getTagCompound() != null) {
            NBTTagList items = stack.getTagCompound().getTagList("Items");
            
            for (int i = 0; i < items.tagCount(); i++) {
                NBTTagCompound item = (NBTTagCompound) items.tagAt(i);
                int slot = item.getByte("Slot");
                
                if (slot >= 0 && slot < tile.getSizeInventory()) {
                    tile.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
                }
            }
        }
        if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("playerName"))
            tile.setPlayerName(stack.getTagCompound().getString("playerName"));
        else
            tile.setPlayerName(player.username);
        player.inventory.mainInventory[player.inventory.currentItem] = null;
        return true;
    }
    
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
        if (stack.getTagCompound() != null) {
            NBTTagList items = stack.getTagCompound().getTagList("Items");
            
            ItemStack[] inv = new ItemStack[9];
            
            for (int i = 0; i < items.tagCount(); i++) {
                NBTTagCompound item = (NBTTagCompound) items.tagAt(i);
                int slot = item.getByte("Slot");
                
                if (slot >= 0 && slot < inv.length) {
                    inv[slot] = ItemStack.loadItemStackFromNBT(item);
                }
            }
            if (inv != null) {
                list.add(stack.getTagCompound().getString("playerName") + "'s Toolbox contains: ");
                int index = 0;
                for (int i = 0; i < inv.length; i++) {
                    if (inv[i] != null) {
                        index++;
                        list.add(index + "| " + inv[i].getDisplayName());
                    }
                }
            }
        } else {
            list.add("An empty Toolbox");
        }
    }
=======

	public ItemBlockToolBox(int id) {
		super(id);
		this.setCreativeTab(Watercraft.creativeTab);
		maxStackSize = 1;
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if(world.isRemote || !player.isSneaking()) return true;
		world.setBlock(x, y, z, ModBlocks.toolbox.blockID);
		WCTileEntityToolBox tile = (WCTileEntityToolBox) world.getBlockTileEntity(x, y, z);
		if(stack.getTagCompound() != null){
			NBTTagList items = stack.getTagCompound().getTagList("Items");
			
			for (int i = 0; i < items.tagCount(); i++) {
				NBTTagCompound item = (NBTTagCompound)items.tagAt(i);
				int slot = item.getByte("Slot");
				
				if (slot >= 0 && slot < tile.getSizeInventory()) {
					tile.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
				}
			}
		}
		if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("playerName")) tile.setPlayerName(stack.getTagCompound().getString("playerName")); else  tile.setPlayerName(player.username);
		player.inventory.mainInventory[player.inventory.currentItem] = null;
		 ((WCTileEntityToolBox) world.getBlockTileEntity(x, y, z)).setDirection(RotationHelper.yawToForge(player.rotationYaw));
		return true;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!player.isSneaking()){
			if(stack.getTagCompound() == null){
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("playerName", player.username);
				stack.setTagCompound(tag);
			}
			FMLNetworkHandler.openGui(player, Watercraft.instance, GuiHandler.TOOLBOX_GUI_ID, world, (int)player.posX,(int) player.posY, (int)player.posZ); 
		}
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
		if(GuiScreen.isShiftKeyDown()){
			if(stack.getTagCompound() != null){
				NBTTagList items = stack.getTagCompound().getTagList("Items");
			
				ItemStack[] inv = new ItemStack[9];
				
				for (int i = 0; i < items.tagCount(); i++) {
					NBTTagCompound item = (NBTTagCompound)items.tagAt(i);
					int slot = item.getByte("Slot");
					
					if (slot >= 0 && slot < inv.length) {
						inv[slot] = ItemStack.loadItemStackFromNBT(item);
					}
				}
				if(inv != null){
					list.add("This Toolbox currently contains: ");
					int index = 0;
					for(int i = 0; i < inv.length; i++){
						if(inv[i] != null){
							index++;
							list.add(index + "| " + inv[i].getDisplayName());
						}
					}
					if(index == 0)
						list.add("Nothing");
				}
			}else{
				list.add("An empty Toolbox");
			}
		}else{
			if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("playerName"))
				list.add("This Toolbox belongs to: " + stack.getTagCompound().getString("playerName"));
			else
				list.add("A unowned Toolbox");
		}
	}
>>>>>>> Commit
}
