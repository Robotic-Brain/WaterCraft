package dgrxf.watercraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityBoatChest extends WCEntityBoat implements IInventory {
    
    private ItemStack[] items;
    
    private boolean     dropContentsWhenDead = true;
    
    public EntityBoatChest(World par1World) {
        super(par1World);
        items = new ItemStack[3];
    }
    
    public int getSizeInventory() {
        return items.length;
    }
    
    public void killBoat(DamageSource par1DamageSource) {
        /*
         * super.killMinecart(par1DamageSource);
         * 
         * for (int i = 0; i < this.getSizeInventory(); ++i) { ItemStack
         * itemstack = this.getStackInSlot(i);
         * 
         * if (itemstack != null) { float f = this.rand.nextFloat() * 0.8F +
         * 0.1F; float f1 = this.rand.nextFloat() * 0.8F + 0.1F; float f2 =
         * this.rand.nextFloat() * 0.8F + 0.1F;
         * 
         * while (itemstack.stackSize > 0) { int j = this.rand.nextInt(21) + 10;
         * 
         * if (j > itemstack.stackSize) { j = itemstack.stackSize; }
         * 
         * itemstack.stackSize -= j; EntityItem entityitem = new
         * EntityItem(this.worldObj, this.posX + (double)f, this.posY +
         * (double)f1, this.posZ + (double)f2, new ItemStack(itemstack.itemID,
         * j, itemstack.getItemDamage())); float f3 = 0.05F; entityitem.motionX
         * = (double)((float)this.rand.nextGaussian() * f3); entityitem.motionY
         * = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
         * entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);
         * this.worldObj.spawnEntityInWorld(entityitem); } } }
         */
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
    
    @Override
    public void setInventorySlotContents(int i, ItemStack item) {
        items[i] = item;
        if (item != null && item.stackSize > getInventoryStackLimit()) {
            item.stackSize = getInventoryStackLimit();
        }
        onInventoryChanged();
    }
    
    @Override
    public String getInvName() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean isInvNameLocalized() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void onInventoryChanged() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return entityplayer.getDistanceSq(posX + 0.5, posY + 0.5, posZ + 0.5) <= 64;
    }
    
    @Override
    public void openChest() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void closeChest() {
        // TODO Auto-generated method stub
        
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
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }
    
}
