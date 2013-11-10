package dgrxf.watercraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Class Created By: Frod Class Last Modified By: Frod
 * 
 * Class Last Modified On: 11/08/2013 MM/DD/YYYY
 * 
 */

/**
 * 
 * THIS CLASS IS NO LONGER USED
 * 
 */

public class WCTileEntitySmelter extends TileEntity /* implements IInventory */{
    
    private final int SMELTER_RANGE    = 15;
    private final int FREEZER_RANGE    = 15;
    private final int FREEZER_COOLDOWN = 50;
    private final int SMELTER_COOLDOWN = 50;
    private int       cooldown;
    
    public WCTileEntitySmelter() {
        cooldown = SMELTER_COOLDOWN;
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        
        if (!worldObj.isRemote) {
            if (cooldown == 0) {
                //temp code
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) + 1) % 2, 3);
                
                smeltIce();
                cooldown = FREEZER_COOLDOWN;
            } else {
                cooldown--;
            }
        }
    }
    
    private void smeltIce() {
        boolean set = false;
        for (int i = 0; i < SMELTER_RANGE; i++) {
            for (int x = 0; x <= i; x++) {
                int z = i - x;
                
                //System.out.println("X = " + Integer.toString(x) + " Z = " + Integer.toString(z));
                
                if (worldObj.getBlockId(xCoord + x, yCoord - 1, zCoord + z) == Block.ice.blockID) {
                    worldObj.setBlock(xCoord + x, yCoord - 1, zCoord + z, Block.waterStill.blockID);
                    set = true;
                }
                
                if (worldObj.getBlockId(xCoord - x, yCoord - 1, zCoord + z) == Block.ice.blockID) {
                    worldObj.setBlock(xCoord - x, yCoord - 1, zCoord + z, Block.waterStill.blockID);
                    set = true;
                }
                
                if (worldObj.getBlockId(xCoord + x, yCoord - 1, zCoord - z) == Block.ice.blockID) {
                    worldObj.setBlock(xCoord + x, yCoord - 1, zCoord - z, Block.waterStill.blockID);
                    set = true;
                }
                
                if (worldObj.getBlockId(xCoord - x, yCoord - 1, zCoord - z) == Block.ice.blockID) {
                    worldObj.setBlock(xCoord - x, yCoord - 1, zCoord - z, Block.waterStill.blockID);
                    set = true;
                }
            }
            
            if (set) {
                return;
            }
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("cooldown", cooldown);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        cooldown = compound.getInteger("cooldown");
    }
    
    /*
     * @Override public int getSizeInventory() { // TODO Auto-generated method
     * stub return 0; }
     * 
     * @Override public ItemStack getStackInSlot(int i) { // TODO Auto-generated
     * method stub return null; }
     * 
     * @Override public ItemStack decrStackSize(int i, int j) { // TODO
     * Auto-generated method stub return null; }
     * 
     * @Override public ItemStack getStackInSlotOnClosing(int i) { // TODO
     * Auto-generated method stub return null; }
     * 
     * @Override public void setInventorySlotContents(int i, ItemStack
     * itemstack) { // TODO Auto-generated method stub
     * 
     * }
     * 
     * @Override public String getInvName() { // TODO Auto-generated method stub
     * return null; }
     * 
     * @Override public boolean isInvNameLocalized() { // TODO Auto-generated
     * method stub return false; }
     * 
     * @Override public int getInventoryStackLimit() { // TODO Auto-generated
     * method stub return 0; }
     * 
     * @Override public boolean isUseableByPlayer(EntityPlayer entityplayer) {
     * // TODO Auto-generated method stub return false; }
     * 
     * @Override public void openChest() { // TODO Auto-generated method stub
     * 
     * }
     * 
     * @Override public void closeChest() { // TODO Auto-generated method stub
     * 
     * }
     * 
     * @Override public boolean isItemValidForSlot(int i, ItemStack itemstack) {
     * // TODO Auto-generated method stub return false; }
     */
}
