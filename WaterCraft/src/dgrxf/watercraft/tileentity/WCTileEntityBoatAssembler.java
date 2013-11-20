package dgrxf.watercraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import dgrxf.watercraft.enumeration.Alphabet;
import dgrxf.watercraft.interfaces.IBoatModule;
import dgrxf.watercraft.interfaces.IItemModule;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.item.boat.ItemModularBoat;
import dgrxf.watercraft.util.ModuleHelper;

public class WCTileEntityBoatAssembler extends TileEntity implements IInventory{

	private ItemStack[] items = new ItemStack[3];
	
	
	/*
	 * This method updates the entity, it checks the first two slots to see if there are IItemModules in the slots.
	 * It calls getOldTagsFromNBT, it then writes the new NBT data to a tag to be put onto an itemstack and places the new
	 * item in the third slot, with the custom NBT.
	 */
	@Override
	public void updateEntity() {
		ItemStack slotZero = getStackInSlot(0);
		ItemStack slotOne = getStackInSlot(1);
		
		if(slotZero == null || slotOne == null ||
				!(slotZero.getItem() instanceof IItemModule) || !(slotOne.getItem() instanceof IItemModule)) return;
		
		NBTTagCompound tagOne = new NBTTagCompound();
		NBTTagCompound tagTwo = new NBTTagCompound();
		NBTTagCompound tagReturn = new NBTTagCompound();
		NBTTagCompound tag = new NBTTagCompound();
		
		int startingPosOne = getOldTagsFromNBT(tagOne, tagReturn, slotZero, 0);
		int startingPosTwo = getOldTagsFromNBT(tagTwo, tagReturn, slotOne, startingPosOne);
		
		int totalOrigTags = startingPosOne + startingPosTwo;
		
		ItemStack newItem = new ItemStack(ModItems.moduleBoat);
		
		IItemModule modZero = (IItemModule)slotZero.getItem();
		IItemModule modOne = (IItemModule)slotOne.getItem();
		
		Class zero = modZero.getBoatModule();
		Class one = modOne.getBoatModule();
		
		int tagsAdded = ModuleHelper.writeModuleInforToNBT(zero, tagReturn, totalOrigTags);
		ModuleHelper.writeModuleInforToNBT(one, tagReturn, totalOrigTags + tagsAdded);
		
		tag.setCompoundTag("Modules", tagReturn);
		newItem.setTagCompound(tag);
		
		decrStackSize(0, 1);
		decrStackSize(1, 1);
		setInventorySlotContents(2, newItem);
			
		
	}
	
	
	/*
	 * This method checks to see if an item has NBT data on it, if it doesn't it returns a 0, but if it does it enters
	 * a for loop where it loops through to see how much data is in the tag and returns that amount.
	 */
	private int getOldTagsFromNBT(NBTTagCompound tag, NBTTagCompound returnTag, ItemStack stack, int startingOffset){
		if(stack.hasTagCompound()){
			tag = stack.getTagCompound().getCompoundTag("Modules");
			for(int i = 0; i < Alphabet.COUNT.ordinal(); i++){
				startingOffset = i;
				if(nbtHasKey(tag, i)){
					writeOldTagsToNBT(returnTag, tag, i + startingOffset, i);
					continue;
				}
				else{
					return startingOffset;
				}
			}
			return startingOffset;
		}
		return 0;
	}
	
	
	/*
	 * THis method writes a value from the old NBT it's given at position i2 and places it in the new NBT's position i.
	 */
	private void writeOldTagsToNBT(NBTTagCompound newTag, NBTTagCompound oldTag, int i, int i2){
		newTag.setString(Alphabet.values()[i].toString(), oldTag.getString(Alphabet.values()[i2].toString()));
	}
	
	/*
	 * This method just returns whether or not a tag has a key at the the position given.
	 */
	private boolean nbtHasKey(NBTTagCompound tag, int i){
		return tag.hasKey(Alphabet.values()[i].toString());
	}
	
	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if(i <= items.length)
			return items[i];
		else
			return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int count) {
		ItemStack itemstack = getStackInSlot(i);
		if(itemstack != null){
			if(itemstack.stackSize <= count){
				setInventorySlotContents(i, null);
			}else{
				itemstack = itemstack.splitStack(count);
				onInventoryChanged();
			}
		}
		
		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack item = getStackInSlot(i);
		setInventorySlotContents(i, null);
		return item;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		items[i] = itemstack;
		
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		
		onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5f, yCoord + 0.5f, zCoord + 0.5f) <= 64;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if(i == 2)
			return false;
		else
			return true;
	}

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList items = new NBTTagList();
        
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getStackInSlot(i);
            
            if (stack != null) {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);
                stack.writeToNBT(item);
                items.appendTag(item);
            }
        }
        compound.setTag("Items", items);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList items = compound.getTagList("Items");
        
        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound item = (NBTTagCompound) items.tagAt(i);
            int slot = item.getByte("Slot");
            
            if (slot >= 0 && slot < getSizeInventory()) {
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
            }
        }
    }
	
}
