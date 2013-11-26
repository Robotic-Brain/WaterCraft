package dgrxf.watercraft.server.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BoatAssemblerContainer extends Container{

	public IInventory inv;
	public InventoryPlayer playerInv;
	
	public BoatAssemblerContainer(InventoryPlayer playerInv, IInventory inv){
		this.inv = inv;
		this.playerInv = playerInv;
		
		for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(playerInv, i, 18 + 18 * i, 194));
        }
        
        for (int i = 0; i < 27; i++) {
            addSlotToContainer(new Slot(playerInv, i + 9, 18 + 18 * (i % 9), 136 + 18 * (i / 9)));
        }
		
        addSlotToContainer(new Slot(inv, 0, 44, 97));
        addSlotToContainer(new Slot(inv, 1, 136, 97));
        addSlotToContainer(new Slot(inv, 2, 172, 8));
        
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        Slot slot = getSlot(par2);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            ItemStack newItemStack = itemStack.copy();

            if (par2 >= 36) {
                if (!mergeItemStack(itemStack, 0, 36, false))
                    return null;
            }
            else if (!this.mergeItemStack(itemStack, 36, 36 + inv.getSizeInventory(), false)){
                return null;
            }
            
            if(itemStack.stackSize == 0){
            	slot.putStack(null);
            }else {
            	slot.onSlotChanged();
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemStack);
            return newItemStack;
        }
        
        return null;
	}

}
