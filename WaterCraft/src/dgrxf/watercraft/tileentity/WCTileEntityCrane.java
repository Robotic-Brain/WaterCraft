package dgrxf.watercraft.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;

public class WCTileEntityCrane extends DirectionalTileEntity implements IInventory, ITileEntityInterfaceEvent{

	ItemStack[] items = new ItemStack[4];
	
	@Override
	public void updateEntity() {
		AbstractBaseBoat b = getBoatInBounds(getBlockDirection());
		if(b != null){
			ItemStack[] temp = b.getBoatModules();
			for(ItemStack item : temp){
				for(ItemStack ourItem : items){
					if(item == ourItem){
						int toCall = getTabForItem(item);
						if(toCall != -1){
							//TODO: Add gui interactions here now that we have the tab to execute the list from
						}
					}
				}
			}
		}
	}
	
	private int getTabForItem(ItemStack item) {
		for(int i = 0; i < items.length; i++){
			if(getStackInSlot(i).isItemEqual(item)){
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
            	System.out.println("test");
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
	public ItemStack decrStackSize(int i, int j){ 
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		
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
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return false;
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
		
	}
	
}
