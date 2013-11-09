package dgrxf.watercraft.client.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import dgrxf.watercraft.client.gui.container.slot.ToolBoxSlot;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

/**
 * Class Created By: Drunk Mafia (TDM) Class Last Modified By: Drunk Mafia (TDM)
 * 
 * Class Last Modified On: 11/09/2013 MM/DD/YYYY
 * 
 */

public class ToolboxContainer extends Container {

	private WCTileEntityToolBox tile;
	
	public ToolboxContainer(InventoryPlayer invPlayer, WCTileEntityToolBox te) {
		
		tile = te;
		
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new ToolBoxSlot(te, x, 8 + 18 * x, 16));
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
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		Slot slot = getSlot(i);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			ItemStack result = stack.copy();
			
			if (i >= 36) {
				if (!mergeItemStack(stack, 0, 36, false)) {
					return null;
				}
			}else if(tile.isItemValidForSlot(0, stack) || !mergeItemStack(stack, 36, 36 + tile.getSizeInventory(), false)) {
				return null;
			}
			
			if (stack.stackSize == 0) {
				slot.putStack(null);
			}else{
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
