package dgrxf.watercraft.item;

import java.util.List;

import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemBlockToolBox extends ItemBlock {

	public ItemBlockToolBox(int id) {
		super(id);
		this.setCreativeTab(Watercraft.creativeTab);
		maxStackSize = 1;
	}
	//This is a comment
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if(world.isRemote) return true;
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
		tile.setPlayerName(player.username);
		player.inventory.mainInventory[player.inventory.currentItem] = null;
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
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
			
			list.add("This Toolbox contains: ");
			int index = 0;
			for(int i = 0; i < inv.length; i++){
				if(inv[i] != null){
					index++;
					list.add(index + "| " + inv[i].getDisplayName());
				}
			}
		}else{
			list.add("An empty Toolbox");
		}
	}
}
