package dgrxf.watercraft.entity.boat;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.DumbTask;
import dgrxf.watercraft.entity.boat.ai.tasks.InventoryTask;
import dgrxf.watercraft.interfaces.ILockableBlock;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.lib.EntityInfo;
import dgrxf.watercraft.util.LogHelper;

public class ChestBoat extends AbstractBaseBoat implements IInventory, ILockableBlock{

    public ItemStack[] items = new ItemStack[27];
	
    private int code;
    private boolean lock;
    
	public ChestBoat(World world){
		super(world);
	}
	
	public ChestBoat(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(EntityInfo.DATAWATCHER_CHEST_LOCK, new Byte((byte) 0));
	}
	
	@Override
	protected void setBoatAI(BoatAITaskList list) {
		list.addTask(new DumbTask(this, 0f));
		list.addTask(new InventoryTask(this, 1.0f));
	}

	@Override
	public Block getDisplayTile() {
		return ModBlocks.chest;
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
        return "Boat Chest";
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
        return entityplayer.getDistanceSq(posX + 0.5, posY + 0.5, posZ + 0.5) <= 64;
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
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
	//All of these aren't used, only implementing to differentiate from normal boats, using datawatchers instead so you don't have to send packets.
	@Override
	public void setLocked(boolean lock) {}

	@Override
	public boolean isLocked() {return false;}

	@Override
	public int getCode() {return 0;}

	@Override
	public void setCode(int code) {}
	
}
