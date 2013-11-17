package dgrxf.watercraft.client.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.gui.container.slot.ToolBoxSlot;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;
import dgrxf.watercraft.util.damage.WCDamageSources;

/**
 * Class Created By: Drunk Mafia (TDM) Class Last Modified By: Drunk Mafia (TDM)
 * 
 * Class Last Modified On: 11/09/2013 MM/DD/YYYY
 * 
 */

public class ToolboxContainer extends Container {
    
    private WCTileEntityToolBox tile;
    private boolean             isInInv;
    private ItemStack[]         loadedItems;
    
    public ToolboxContainer(InventoryPlayer invPlayer, WCTileEntityToolBox te) {
        
        loadedItems = new ItemStack[9];
        
        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 99));
        }
        
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 41 + y * 18));
            }
        }
        
        if (te != null) {
            tile = te;
            isInInv = false;
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new ToolBoxSlot(te, x, 8 + 18 * x, 16));
            }
            tile.isOpen = true;
            tile.openChest();
        } else {
            WCTileEntityToolBox temp = new WCTileEntityToolBox();
            tile = temp;
            isInInv = true;
            if (invPlayer.getCurrentItem().getTagCompound() != null) {
                ItemStack stack = invPlayer.getCurrentItem();
                NBTTagCompound tag = invPlayer.getCurrentItem().getTagCompound();
                NBTTagList items = tag.getTagList("Items");
                for (int i = 0; i < items.tagCount(); i++) {
                    NBTTagCompound item = (NBTTagCompound) items.tagAt(i);
                    int slot = item.getByte("Slot");
                    
                    if (slot >= 0 && slot < temp.getSizeInventory()) {
                        temp.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
                        loadedItems[i] = ItemStack.loadItemStackFromNBT(item);
                    }
                }
                tag.setBoolean("isOpen", true);
                stack.setTagCompound(tag);
                invPlayer.mainInventory[invPlayer.currentItem] = stack;
                
            } else {
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
    }
    
    @Override
    public void onContainerClosed(EntityPlayer player) {
        if (isInInv) {
            ItemStack toolbox = player.getCurrentEquippedItem();
            NBTTagCompound tag;
            if (toolbox != null) {
                if (toolbox.getTagCompound() != null) {
                    tag = toolbox.getTagCompound();
                } else {
                    tag = new NBTTagCompound();
                }
                NBTTagList items = new NBTTagList();
                
                tag.setBoolean("isOpen", false);
                
                for (int i = 0; i < tile.getSizeInventory(); i++) {
                    ItemStack stack = tile.getStackInSlot(i);
                    
                    if (stack != null) {
                        NBTTagCompound item = new NBTTagCompound();
                        item.setByte("Slot", (byte) i);
                        stack.writeToNBT(item);
                        items.appendTag(item);
                    }
                }
                tag.setTag("Items", items);
                toolbox.setTagCompound(tag);
                player.inventory.mainInventory[player.inventory.currentItem] = toolbox;
            } else {
                for (int i = 0; i < tile.getSizeInventory(); i++) {
                    if (tile.getStackInSlot(i) != null && loadedItems[i] == null) {
                        player.dropPlayerItem(tile.getStackInSlot(i));
                    } else {
                        if (tile.getStackInSlot(i) != null) {
                            System.out.println(loadedItems[i].getDisplayName());
                        }
                    }
                }
                
            }
        } else {
            tile.closeChest();
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int i) {
        Slot slot = getSlot(i);
        
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            ItemStack result = stack.copy();
            
            if (stack.getItem().itemID == ModBlocks.toolbox.blockID) {
                player.attackEntityFrom(WCDamageSources.inception, 1000);
            }
            
            if (i >= 36 && tile.isItemValidForSlot(0, stack)) {
                if (!mergeItemStack(stack, 0, 36, false)) {
                    return null;
                }
            } else if (!tile.isItemValidForSlot(0, stack) || !mergeItemStack(stack, 36, 36 + tile.getSizeInventory(), false)) {
                return null;
            }
            
            if (stack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
            
            slot.onPickupFromSlot(player, stack);
            
            return result;
        }
        
        return null;
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }
}
