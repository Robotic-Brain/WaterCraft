package dgrxf.watercraft.entity.boat.ai.tasks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.interfaces.ILockableBlock;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.lib.EntityInfo;

/**
 * 
 * InventoryTask
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class InventoryTask extends BoatAITaskBase implements IInventory, ILockableBlock{

	private int guiID;
	private ItemStack[] items;
	private Object modID;
	private boolean lockable;
	private int code;
	
	public InventoryTask(AbstractBaseBoat boat, Float priority, Object... args) {
		super(boat, priority);
		this.guiID = (Integer) args[0];
		this.modID = args[1];
		this.items = new ItemStack[(Integer)args[2]];
		this.lockable = (Boolean)args[3];
	}

	@Override
	public void entityInit() {
		super.entityInit();
	}

	@Override
	public void onInteractFirst(EntityPlayer player) {
        if (!boat.worldObj.isRemote) {
        	if(lockable){
	        	ItemStack heldItem = player.inventory.getCurrentItem();
	            if (!player.isSneaking()) {
	            	if(boat.getDataWatcher().getWatchableObjectByte(EntityInfo.DATAWATCHER_CHEST_LOCK) == (byte)0 || (heldItem != null && heldItem.itemID == ModItems.KEY.getId() && heldItem.getItemDamage() == this.getCode()))
	            		if(guiID < 0){
	            			openVanillaGUI(player);
	            		}
	            		else
	            			FMLNetworkHandler.openGui(player, modID, guiID, boat.worldObj, boat.entityId, 0, 0);
	            } else {
	                if(boat.getDataWatcher().getWatchableObjectByte(EntityInfo.DATAWATCHER_CHEST_LOCK) == 1){
	                	if(heldItem != null && heldItem.itemID == ModItems.KEY.getId()){
	                		if(heldItem.getItemDamage() == this.getCode()){
	                			boat.getDataWatcher().updateObject(EntityInfo.DATAWATCHER_CHEST_LOCK, new Byte((byte)0));
	                		}
	                	}
	                }
	                else{
	                	if(heldItem != null && heldItem.itemID == ModItems.PADLOCK.getId()){
	            			boat.getDataWatcher().updateObject(EntityInfo.DATAWATCHER_CHEST_LOCK, new Byte((byte)1));
	            			this.setCode(heldItem.getItemDamage());
	                	}
	                }
	            }
	        }
        	else
        	{
        		if(!player.isSneaking()){
        			if(guiID < 0){
        				openVanillaGUI(player);
        			}
        			else
            			FMLNetworkHandler.openGui(player, modID, guiID, boat.worldObj, boat.entityId, 0, 0);
        		}
        	}
        }
	}
	
	private void openVanillaGUI(EntityPlayer player){
		switch(guiID){
		case GuiHandler.VANILLA_CHEST_ID:
			player.displayGUIChest(this);
			break;
		}
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
    public ItemStack decrStackSize(int i, int j) {
        ItemStack item = getStackInSlot(i);
        if (item != null) {
            if (item.stackSize <= j) {
                setInventorySlotContents(i, null);
            } else {
                item = item.splitStack(j);
                onInventoryChanged();
            }
        }
        return item;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        ItemStack item = getStackInSlot(i);
        setInventorySlotContents(i, null);
        return item;
    }

    public void setInventorySlotContents(int i, ItemStack item) {
        items[i] = item;
        if (item != null && item.stackSize > getInventoryStackLimit()) {
            item.stackSize = getInventoryStackLimit();
        }
        onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return "invetoryTask";
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
	public void onInventoryChanged() {}

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return entityplayer.getDistanceSq(boat.posX + 0.5, boat.posY + 0.5, boat.posZ + 0.5) <= 64;
    }

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT(tag);
		
		NBTTagList items = new NBTTagList();
		
		for (int i = 0; i < getSizeInventory(); i++){
			ItemStack stack = getStackInSlot(i);
			
			if (stack != null){
				NBTTagCompound item = new NBTTagCompound();
				
				item.setInteger("Slot", i);
				stack.writeToNBT(item);
				items.appendTag(item);
			}
		}
		tag.setTag("Items", items);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		NBTTagList items = compound.getTagList("Items");
		
		for (int i = 0; i < items.tagCount(); i++){
			NBTTagCompound item = (NBTTagCompound)items.tagAt(i);
			int slot = item.getInteger("Slot");
			
			if(slot >= 0 && slot < getSizeInventory()){
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
			
		}
	}


	@Override
	public void setLocked(boolean lock) {
	}


	@Override
	public boolean isLocked() {
		return false;
	}


	@Override
	public int getCode() {
		return code;
	}


	@Override
	public void setCode(int code) {
		this.code = code;
	}
	
}
