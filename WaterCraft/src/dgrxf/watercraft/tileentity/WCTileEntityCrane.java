package dgrxf.watercraft.tileentity;

import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;

/**
 * 
 * WCTileEntityCrane
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class WCTileEntityCrane extends DirectionalTileEntity implements IInventory, ITileEntityInterfaceEvent{

	ItemStack[] items = new ItemStack[4];
	HashMap<Integer, Actions[]> actions = new HashMap();
	public int activeTabIndex=0;
	
	private enum Actions{
		
	}
	
	@Override
	public void updateEntity() {
		AbstractBaseBoat b = getBoatInBounds(getBlockDirection());
		if(b != null){
			ItemStack[] temp = b.getBoatModules();
			for(ItemStack boatItem : temp){
				int toCall = getTabForItem(boatItem);
				if(toCall != -1){
					//TODO: Add gui interactions here now that we have the tab to execute the list from
					// ALSO TODO: Make an ActionRegistry that will accept tasks, like the AI system, then preform actions here.
				}
			}
		}
	}
	
	private int getTabForItem(ItemStack item) {
		for(int i = 0; i < items.length; i++){
			if(getStackInSlot(i) != null && getStackInSlot(i).isItemEqual(item)){
				return i;
			}
		}
		return -1;
	}

	public AbstractBaseBoat getBoatInBounds(ForgeDirection d){
        int tempX = xCoord + d.offsetX * 3;
        int tempZ = zCoord + d.offsetZ * 3;
        
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(tempX - 1, yCoord - 1, tempZ - 1, tempX + 1, yCoord + 1, tempZ + 1);
        List list = worldObj.getEntitiesWithinAABB(AbstractBaseBoat.class, bounds);
        
        for (int a = 0; a < list.size(); a++) {
            Entity e = (Entity) list.get(a);
            if (e instanceof AbstractBaseBoat) {
                return (AbstractBaseBoat) e;
            }
        }
        
        return null;
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
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.items[par1] != null) {
            ItemStack itemstack;
            
            if (this.items[par1].stackSize <= par2) {
                itemstack = this.items[par1];
                this.items[par1] = null;
                this.onInventoryChanged();
                return itemstack;
            } else {
                itemstack = this.items[par1].splitStack(par2);
                
                if (this.items[par1].stackSize == 0) {
                    this.items[par1] = null;
                }
                
                this.onInventoryChanged();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.items[par1] != null) {
            ItemStack itemstack = this.items[par1];
            this.items[par1] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.items[par1] = par2ItemStack;
        
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
        
        this.onInventoryChanged();
    }

	@Override
	public String getInvName() {
		return null;
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
		return entityplayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {
		
	}

	@Override
	public void closeChest() {
		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	@Override
	public void receiveInterfaceEvent(byte buttonid, byte[] extraInfo) {
		this.activeTabIndex = extraInfo[0];
	}
	
}
