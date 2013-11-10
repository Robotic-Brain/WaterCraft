package dgrxf.watercraft.tileentity;

import dgrxf.watercraft.lib.BlockInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * Class Created By: Drunk Mafia (TDM) Class Last Modified By: Drunk Mafia (TDM)
 * 
 * Class Last Modified On: 11/09/2013 MM/DD/YYYY
 * 
 */

public class WCTileEntityToolBox extends WCTileEntity implements IInventory {
    
    private ItemStack[] inventory;
    private String      playerName;
    public  boolean     isOpen;
    
    public WCTileEntityToolBox() {
        inventory = new ItemStack[getSizeInventory()];
        isOpen = false;
    }
    
    @Override
    public void updateEntity() {
        for (int i = 0; i < inventory.length; i++) {
            
            if (inventory[i] != null && inventory[i].getItem().itemID == BlockInfo.TOOLBOX_ID) {
                worldObj.createExplosion(null, xCoord, yCoord, zCoord, 10, true);
                Minecraft.getMinecraft().thePlayer.sendChatMessage("Tut tut tut, do not create a minecraft inception");
            }
        }
    }
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    @Override
    public int getSizeInventory() {
        return 9;
    }
    
    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory[i];
    }
    
    @Override
    public ItemStack decrStackSize(int i, int count) {
        ItemStack itemstack = getStackInSlot(i);
        
        if (itemstack != null) {
            if (itemstack.stackSize <= count) {
                setInventorySlotContents(i, null);
            } else {
                itemstack = itemstack.splitStack(count);
                onInventoryChanged();
            }
        }
        
        return itemstack;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        ItemStack slot = getStackInSlot(i);
        return slot;
    }
    
    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory[i] = itemstack;
        
        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }
    
    @Override
    public String getInvName() {
        return "WCToolBoxGUI";
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return true;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }
    
    @Override
    public void openChest() {
    }
    
    @Override
    public void closeChest() {
    }
    
    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        if (itemstack.getItem() instanceof ItemTool)
            return true;
        else if (itemstack.getItem() instanceof ItemBow)
            return true;
        else if (itemstack.getItem() instanceof ItemBucket)
            return true;
        else if (itemstack.getItem() instanceof ItemSword)
            return true;
        else if (itemstack.getItem() instanceof ItemShears)
            return true;
        else if (itemstack.getItem() instanceof ItemFlintAndSteel)
            return true;
        else if (itemstack.getItem() instanceof ItemFishingRod)
            return true;
        
        return false;
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
        compound.setString("playerName", playerName);
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
        playerName = compound.getString("playerName");
    }
    
    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        NBTTagCompound tag = pkt.data;
        playerName = tag.getString("playerName");
    }
    
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        if (playerName != null)
            tag.setString("playerName", playerName);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, blockMetadata, tag);
    }
}
