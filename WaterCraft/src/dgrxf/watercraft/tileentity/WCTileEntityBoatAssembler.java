package dgrxf.watercraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import dgrxf.watercraft.enumeration.Alphabet;
import dgrxf.watercraft.interfaces.IBoatModule;
import dgrxf.watercraft.interfaces.IItemModule;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.util.ModuleHelper;

public class WCTileEntityBoatAssembler extends TileEntity implements IInventory{

	private ItemStack[] items = new ItemStack[3];
	
	@Override
	public void updateEntity() {
		ItemStack slotZero = getStackInSlot(0);
		ItemStack slotOne = getStackInSlot(1);
		
		if(slotZero == null || slotOne == null) return;
		
		NBTTagCompound tagOne = new NBTTagCompound();
		NBTTagCompound tagTwo = new NBTTagCompound();
		NBTTagCompound tagReturn = new NBTTagCompound();
		NBTTagCompound tag = new NBTTagCompound();
		int startingPosOne = 0, startingPosTwo = 0, totalOrigTags = 0;
		
		if(slotZero.hasTagCompound()){
			tagOne = slotZero.getTagCompound().getCompoundTag("Modules");
			for(int i = 0; i < Alphabet.COUNT.ordinal(); i++){
				startingPosOne = i;
				if(nbtHasKey(tagOne, i)){
					writeOldTagsToNBT(tagReturn, tagOne, i, i);
					continue;
				}
				else{
					break;
				}
			}
		}
		
		if(slotOne.hasTagCompound()){
			tagTwo = slotOne.getTagCompound().getCompoundTag("Modules");
			for(int i = 0; i < Alphabet.COUNT.ordinal(); i++){
				startingPosTwo = i;
				if(nbtHasKey(tagTwo, i)){
					writeOldTagsToNBT(tagReturn, tagTwo, i + startingPosOne, i);
					continue;
				}
				else{
					break;
				}
			}
		}
		
		totalOrigTags = startingPosOne + startingPosTwo;
		
		ItemStack newItem = new ItemStack(ModItems.moduleBoat);
		if(slotZero.getItem() instanceof IItemModule){
			IItemModule modZero = (IItemModule)slotZero.getItem();
			if(slotOne.getItem() instanceof IItemModule){
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
		}
	}
	
	private void writeOldTagsToNBT(NBTTagCompound newTag, NBTTagCompound oldTag, int i, int i2){
		newTag.setString(Alphabet.values()[i].toString(), oldTag.getString(Alphabet.values()[i2].toString()));
	}
	
	private boolean nbtHasKey(NBTTagCompound tag, int i){
		
		if(tag.hasKey(Alphabet.values()[i].toString())){
			return true;
		}
		
		return false;
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
