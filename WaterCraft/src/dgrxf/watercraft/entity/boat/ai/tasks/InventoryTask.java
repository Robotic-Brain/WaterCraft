package dgrxf.watercraft.entity.boat.ai.tasks;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.interfaces.ILockableBlock;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.lib.EntityInfo;

public class InventoryTask extends BoatAITaskBase{

	private int guiID;
	
	public InventoryTask(AbstractBaseBoat boat, float priority, int guiID) {
		super(boat, priority);
		this.guiID = guiID;
	}


	@Override
	public void onInteractFirst(EntityPlayer player) {
		if(!(boat instanceof IInventory)) return;
		
        if (!boat.worldObj.isRemote) {
        	if(boat instanceof ILockableBlock){
	        	ItemStack heldItem = player.inventory.getCurrentItem();
	            if (!player.isSneaking()) {
	            	if(boat.getDataWatcher().getWatchableObjectByte(EntityInfo.DATAWATCHER_CHEST_LOCK) == 0 || (heldItem != null && heldItem.itemID == ModItems.key.itemID && heldItem.getItemDamage() == ((ILockableBlock)boat).getCode()))
	            		if(guiID < 0){
	            			openVanillaGUI(player);
	            		}
	            		else
	            			FMLNetworkHandler.openGui(player, Watercraft.instance, guiID, boat.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
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
				System.out.println("test3");
        		if(!player.isSneaking()){
        			if(guiID < 0){
        				openVanillaGUI(player);
        			}
        			else
            			FMLNetworkHandler.openGui(player, Watercraft.instance, guiID, boat.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
        		}
        	}
        }
	}
	
	private void openVanillaGUI(EntityPlayer player){
		switch(guiID){
		case GuiHandler.VANILLA_CHEST_ID:
			player.displayGUIChest((IInventory)boat);
			break;
		}
	}
	
}
