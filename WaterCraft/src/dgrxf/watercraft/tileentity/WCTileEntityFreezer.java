package dgrxf.watercraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * 
 * WCTileEntityFreezer
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
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
    
    public void setType(int i) {
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, i, 3);
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
