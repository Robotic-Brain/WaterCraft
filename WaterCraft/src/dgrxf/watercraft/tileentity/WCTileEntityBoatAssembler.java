package dgrxf.watercraft.tileentity;

import java.util.HashSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import dgrxf.watercraft.interfaces.IModularBoat;
import dgrxf.watercraft.module.ModuleHelper;
import dgrxf.watercraft.module.ModuleRegistry;
import dgrxf.watercraft.util.TranslationHelper.TH;

/**
 * 
 * WCTileEntityBoatAssembler
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class WCTileEntityBoatAssembler extends TileEntity implements IInventory, ITileEntityInterfaceEvent{

	private ItemStack[] items = new ItemStack[3];
	private boolean assemble = false;
	//TODO: Comment the class
	
	@Override
	public void updateEntity() {
		if(this.worldObj.isRemote) return;
		
		ItemStack slotZero = getStackInSlot(0);
		ItemStack slotOne = getStackInSlot(1);
		
		if(slotZero != null && slotOne != null && assemble){
			assemble = false;
			ItemStack item = null;
			if(slotZero.getItem() instanceof IModularBoat && ModuleRegistry.isItemRegistered(slotOne)){
				createAndReturnItem(slotZero, item, slotOne);
			}else if(ModuleRegistry.isItemRegistered(slotZero) && (slotOne.getItem() instanceof IModularBoat)){
				createAndReturnItem(slotOne, item, slotZero);
			}
		}else if(assemble){
			assemble = false;
		}
	}
	
	private void createAndReturnItem(ItemStack slot, ItemStack item, ItemStack modItem){
		HashSet<String> strings = addModuleToSetOrReturnModules(slot, modItem, true);
		HashSet<String> temp = addModuleToSetOrReturnModules(slot, modItem, false);
			if(!strings.equals(temp)){
				item = new ItemStack(slot.getItem());
				ModuleHelper.writeSetToItemStackNBT(strings, item);
				returnItem(item);
			}
	}
	
	private void returnItem(ItemStack item){
		decrStackSize(0, 1);
		decrStackSize(1, 1);
		setInventorySlotContents(2, item);
	}
	
	private HashSet<String> addModuleToSetOrReturnModules(ItemStack boat, ItemStack modItem, boolean addNewMods){
		HashSet<String> temp = ((IModularBoat)boat.getItem()).getModuleList(boat);
		if(addNewMods && !ModuleHelper.doTasksConflict(modItem, temp))
			temp.add(Integer.toString(modItem.itemID)+":"+Integer.toString(modItem.getItemDamage()));
		return temp;
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
		return TH.buildKey("container", "boat_assembler");
	}

	@Override
	public boolean isInvNameLocalized() {
		return true;
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

	@Override
	public void receiveInterfaceEvent(byte buttonid, byte[] extraInfo) {
		if(worldObj.isRemote) return;
		switch(buttonid){
		case 0:
			assemble = true;
			break;
		}
	}
	
}
