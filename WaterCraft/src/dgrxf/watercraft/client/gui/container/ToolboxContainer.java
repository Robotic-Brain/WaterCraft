package dgrxf.watercraft.client.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import dgrxf.watercraft.client.gui.container.slot.ToolBoxSlot;
import dgrxf.watercraft.client.sound.Sounds;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

/**
 * Class Created By: Drunk Mafia (TDM) Class Last Modified By: Drunk Mafia (TDM)
 * 
 * Class Last Modified On: 11/09/2013 MM/DD/YYYY
 * 
 */

public class ToolboxContainer extends Container {

	private WCTileEntityToolBox tile;
	private boolean isInInv;
	
	public ToolboxContainer(InventoryPlayer invPlayer, WCTileEntityToolBox te) {
		if(te != null){
			tile = te;
			isInInv = false;
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new ToolBoxSlot(te, x, 8 + 18 * x, 16));
			}
			tile.isOpen = true;
		}else{
			WCTileEntityToolBox temp = new WCTileEntityToolBox();
			tile = temp;
			isInInv = true;
			if(invPlayer.getCurrentItem().getTagCompound() != null){
				ItemStack stack = invPlayer.getCurrentItem();
				NBTTagCompound tag = invPlayer.getCurrentItem().getTagCompound();
				NBTTagList items = tag.getTagList("Items");
				for (int i = 0; i < items.tagCount(); i++) {
					NBTTagCompound item = (NBTTagCompound)items.tagAt(i);
					int slot = item.getByte("Slot");
					
					if (slot >= 0 && slot < temp.getSizeInventory()) {
						temp.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
					}
				}
				tag.setBoolean("isOpen", true);
				stack.setTagCompound(tag);
				invPlayer.mainInventory[invPlayer.currentItem] = stack;
				
			}else{
				ItemStack stack = invPlayer.getCurrentItem();
				NBTTagCompound tag = new NBTTagCompound();
				tag.setBoolean("isOpen", true);
				stack.setTagCompound(tag);
				invPlayer.mainInventory[invPlayer.currentItem] = stack;
			}
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new ToolBoxSlot(temp, x, 8 + 18 * x, 16));
			}
		}
		
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 99));
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 41 + y * 18));
			}
		}
		
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		Sounds.TOOLBOX_CLOSING.play(player.posX, player.posY, player.posZ, 1, 1);
		if(isInInv){
			ItemStack toolbox = player.getCurrentEquippedItem();
			NBTTagCompound tag;
			if(toolbox.getTagCompound() != null)
				tag = toolbox.getTagCompound();
			else
				tag = new NBTTagCompound();
			NBTTagList items = new NBTTagList();
			
			tag.setBoolean("isOpen", false);
			
			for (int i = 0; i < tile.getSizeInventory(); i++) {		
				ItemStack stack = tile.getStackInSlot(i);
				
				if (stack != null) {
					NBTTagCompound item = new NBTTagCompound();
					item.setByte("Slot", (byte)i);
					stack.writeToNBT(item);
					items.appendTag(item);
				}
			}
			tag.setTag("Items", items);
			toolbox.setTagCompound(tag);
			player.inventory.mainInventory[player.inventory.currentItem] = toolbox;
		}
		tile.isOpen = false;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		Slot slot = getSlot(i);	
		return slot.getStack();
	}
	
	
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
}
