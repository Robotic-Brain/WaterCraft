package dgrxf.watercraft.tileentity;

import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class WCTileEntityLockAssembler extends TileEntity implements IInventory {
	
	private static final int SLOTS = 5;
	private static final int OUTPUT_SLOTS = 2;
	private static final int[] INGREDIENTS_ID = {Item.ingotIron.itemID, Item.ingotGold.itemID, Item.goldNugget.itemID};
	
	private ItemStack[] items = new ItemStack[SLOTS];
	
	public WCTileEntityLockAssembler() {
		
	}

	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return items[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int count) {
		ItemStack stack = getStackInSlot(i);
		
		if (stack != null) {
			if (stack.stackSize <= count) {
				setInventorySlotContents(i, null);
			} else {
				stack = stack.splitStack(count);
				onInventoryChanged();
			}
		}
		
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack item = getStackInSlot(i);
		setInventorySlotContents(i, null);
		return item;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {		
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		
		items[i] = itemstack;		
		onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "Lock Assembler";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return i < SLOTS - OUTPUT_SLOTS && Arrays.asList(INGREDIENTS_ID).contains(itemstack.itemID);
	}

}
