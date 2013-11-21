package dgrxf.watercraft.tileentity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import dgrxf.watercraft.interfaces.IItemModule;
import dgrxf.watercraft.interfaces.IModularBoat;
import dgrxf.watercraft.util.ModuleHelper;

public class WCTileEntityBoatAssembler extends TileEntity implements IInventory{

	private ItemStack[] items = new ItemStack[3];

	
	@Override
	public void updateEntity() {
		if(this.worldObj.isRemote) return;
		ItemStack slotZero = getStackInSlot(0);
		ItemStack slotOne = getStackInSlot(1);
		
		if(slotZero != null && slotOne != null){
			HashSet<String> modules = new HashSet();
			ItemStack item = null;
			NBTTagCompound tag;
			if(((slotZero.getItem() instanceof IModularBoat) && (slotOne.getItem() instanceof IItemModule))){
				createAndReturnItem(slotZero, item, (IItemModule)slotOne.getItem(), modules);
			}else if(((slotZero.getItem() instanceof IItemModule) && (slotOne.getItem() instanceof IModularBoat))){
				createAndReturnItem(slotOne, item, (IItemModule)slotZero.getItem(), modules);
			}
		}
	}
	
	private void createAndReturnItem(ItemStack slot, ItemStack item, IItemModule mod, HashSet<String> strings){
		if(addModuleToSet(slot, mod, strings)){
			if(strings.size() != 0){
				item = new ItemStack(slot.getItem());
				ModuleHelper.writeSetToItemStackNBT(strings, item);
				returnItem(item);
			}
		}
	}
	
	private void returnItem(ItemStack item){
		decrStackSize(0, 1);
		decrStackSize(1, 1);
		setInventorySlotContents(2, item);
	}
	
	private boolean addModuleToSet(ItemStack boat, IItemModule mod, HashSet<String> set){
		HashSet<String> temp = ((IModularBoat)boat.getItem()).getModuleList(boat);
		set.addAll(temp);
		return set.add(mod.getBoatModule().getName());
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
