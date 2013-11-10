package dgrxf.watercraft.tileentity;

import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.util.Vector3;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Class Created By: Frod Class Last Modified By: Frod
 * 
 * Class Last Modified On: 11/08/2013 MM/DD/YYYY
 * 
 */

public class WCTileEntityFreezer extends TileEntity {
    
    private final int SMELTER_RANGE    = 15;
    private final int FREEZER_RANGE    = 5;
    private final int FREEZER_COOLDOWN = 50;
    private final int SMELTER_COOLDOWN = 50;
    private int       cooldown;
    private int       type;
    
    public WCTileEntityFreezer() {
        setCooldown();
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        type = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        
        if (!worldObj.isRemote) {
            if (cooldown <= 0) {
                setIce();
                setCooldown();
            } else {
                cooldown--;
            }
        }
    }
    
    private void setIce() {
        boolean set;
        int range = getRange();
        
        for (int i = 0; i < range; i++) {
        	set = false;
        	
            for (int x = 0; x <= i; x++) {
                int z = i - x;
                set |= changeBlock(xCoord + x, yCoord - 1, zCoord + z) | changeBlock(xCoord - x, yCoord - 1, zCoord + z) | changeBlock(xCoord + x, yCoord - 1, zCoord - z) | changeBlock(xCoord - x, yCoord - 1, zCoord - z);
            }
            
            if (set) {
                return;
            }
        }
    }
    
    private void setCooldown() {
        switch (type) {
            case 1:
                cooldown = FREEZER_COOLDOWN;
                break;
            case 2:
                cooldown = SMELTER_COOLDOWN;
                break;
        }
    }
    
    private int getRange() {
        switch (type) {
            case 1:
                return FREEZER_RANGE;
            case 2:
                return SMELTER_RANGE;
        }
        
        return 0;
    }
    
    private boolean changeBlock(int x, int y, int z) {
        switch (type) {
            case 1:
                if ((worldObj.getBlockId(x, y, z) == Block.waterStill.blockID) || (worldObj.getBlockId(x, y, z) == Block.waterMoving.blockID)) {
                    worldObj.setBlock(x, y, z, Block.ice.blockID);
                    return true;
                }
                break;
            case 2:
                if (worldObj.getBlockId(x, y, z) == Block.ice.blockID) {
                    worldObj.setBlock(x, y, z, Block.waterStill.blockID);
                    return true;
                }
                break;
        }
        
        return false;
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
}
