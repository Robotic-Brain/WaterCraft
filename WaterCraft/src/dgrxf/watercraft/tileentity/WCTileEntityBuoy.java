package dgrxf.watercraft.tileentity;

import dgrxf.watercraft.util.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * Buoy TileEntity
 * 
 * @author xandayn
 *
 */
public class WCTileEntityBuoy extends WCTileEntity {
    
    /**
     * NBT-Tags
     */
    private static final String NBT_NEXT_BUOY_X = "BuoyTarX";
    private static final String NBT_NEXT_BUOY_Y = "BuoyTarY";
    private static final String NBT_NEXT_BUOY_Z = "BuoyTarZ";
    
    private static final int DEFAULT_RANGE = 10;
    
    /**
     * Fields
     */
    protected WCTileEntityBuoy nextBuoy;
    protected boolean hasBuoy;
    protected int searchRange;
    
    /**
     * Default Constructor
     * 
     */
    public WCTileEntityBuoy() {
        hasBuoy = false;
        searchRange = DEFAULT_RANGE;
    }
    
    protected void findNextBuoy(int yOffset) {
        if (!hasBuoy) {
        	ForgeDirection dir = getBuoyDirection();
        	
            for (int i = 1; !hasBuoy && i <= searchRange; ++i) {
                TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX * i, (yCoord + yOffset) + dir.offsetY * i, zCoord + dir.offsetZ * i);
                if (te instanceof WCTileEntityBuoy) {
                    setNextBuoy((WCTileEntityBuoy) te);
                    LogHelper.debug("Buoy get on " + dir
                                + " me: [x: " + xCoord + ", y: " + yCoord + ", z: " + zCoord + "]"
                                + " next: [x: " + nextBuoy.xCoord + ", y: " + nextBuoy.yCoord + ", z: " + nextBuoy.zCoord + "]");
                }
            }
        }
    }
    
    /**
     * Gets the direction of the Buoy
     * 
     * @return direction
     * @author Robotic-Brain
     */
    public ForgeDirection getBuoyDirection() {
        return ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
    }
    
    /**
     * Sets the next Buoy in line
     * 
     * @param next Next BuoyTileEntity
     * @author Robotic-Brain
     */
    public void setNextBuoy(WCTileEntityBuoy next) {
        this.nextBuoy = next;
        this.hasBuoy = true;
    }
    
    /**
     * Gets the next Buoy in line
     * 
     * @return nextBuoy
     * @author Robotic-Brain
     */
    public WCTileEntityBuoy getNextBuoy() {
        return nextBuoy;
    }
    
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        if (nextBuoy == null) {
            findNextBuoy(0);
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (nextBuoy != null) {
            compound.setInteger(NBT_NEXT_BUOY_X, nextBuoy.xCoord);
            compound.setInteger(NBT_NEXT_BUOY_Y, nextBuoy.yCoord);
            compound.setInteger(NBT_NEXT_BUOY_Z, nextBuoy.zCoord);
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        // If the compound has one of the keys it will have all the keys, thats why I only checked for one, still getting a null pointed or loading though, not sure why.
        // It won't have all keys if the save is corrupted ;)
        if (nextBuoy == null) {
            if (compound.hasKey(NBT_NEXT_BUOY_X)
            && compound.hasKey(NBT_NEXT_BUOY_Y)
            && compound.hasKey(NBT_NEXT_BUOY_Z))
            {
                int x = compound.getInteger(NBT_NEXT_BUOY_X);
                int y = compound.getInteger(NBT_NEXT_BUOY_Y);
                int z = compound.getInteger(NBT_NEXT_BUOY_Z);
                TileEntity te = worldObj.getBlockTileEntity(x, y, z);
                
                if (te instanceof WCTileEntityBuoy) {
                    setNextBuoy((WCTileEntityBuoy) te);
                }
            }
        }
    }
}
