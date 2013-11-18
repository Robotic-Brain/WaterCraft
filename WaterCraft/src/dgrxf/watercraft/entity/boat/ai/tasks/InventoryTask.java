package dgrxf.watercraft.entity.boat.ai.tasks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.interfaces.ILockableBlock;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.lib.EntityInfo;

public class InventoryTask extends BoatAITaskBase{

	public InventoryTask(AbstractBaseBoat boat, float priority) {
		super(boat, priority);
	}

	
	//TODO: Update this to accept different types of GUIs
	@Override
	public void onInteractFirst(EntityPlayer player) {
		if(!(boat instanceof IInventory)) return;
		
        if (!boat.worldObj.isRemote) {
        	if(boat instanceof ILockableBlock){
	        	ItemStack heldItem = player.inventory.getCurrentItem();
	            if (!player.isSneaking()) {
	            	if(boat.getDataWatcher().getWatchableObjectByte(EntityInfo.DATAWATCHER_CHEST_LOCK) == 0 || (heldItem != null && heldItem.itemID == ModItems.key.itemID && heldItem.getItemDamage() == ((ILockableBlock)boat).getCode()))
	            		player.displayGUIChest((IInventory)boat);
	            } else {
	                if(boat.getDataWatcher().getWatchableObjectByte(EntityInfo.DATAWATCHER_CHEST_LOCK) == 1){
	                	if(heldItem != null && heldItem.itemID == ModItems.key.itemID){
	                		if(heldItem.getItemDamage() == ((ILockableBlock)boat).getCode()){
	                			boat.getDataWatcher().updateObject(EntityInfo.DATAWATCHER_CHEST_LOCK, new Byte((byte)0));
	                		}
	                	}
	                }
	                else{
	                	if(heldItem != null && heldItem.itemID == ModItems.padlock.itemID){
	            			boat.getDataWatcher().updateObject(EntityInfo.DATAWATCHER_CHEST_LOCK, new Byte((byte)1));
	            			((ILockableBlock)boat).setCode(heldItem.getItemDamage());
	                	}
	                }
	            }
	        }
        	else
        	{
        		if(!player.isSneaking()){
        			player.displayGUIChest((IInventory)boat);
        		}
        	}
        }
	}
	
}
