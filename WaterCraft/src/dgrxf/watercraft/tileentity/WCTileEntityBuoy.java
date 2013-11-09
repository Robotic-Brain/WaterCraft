package dgrxf.watercraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.lib.MiscInfo;
import dgrxf.watercraft.util.LogHelper;

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
    
    /**
     * Now configurable inside the Config
     */
    private static final int    DEFAULT_RANGE   = MiscInfo.BOUY_RANGE;
    
    /**
     * Fields
     */
    protected boolean           hasBuoy;
    protected int               nextX;                       // needed for readFromNBT
    protected int               nextY;                       // needed for readFromNBT
    protected int               nextZ;                       // needed for readFromNBT
    protected int               searchRange;
    
    /**
     * Default Constructor
     * 
     */
    public WCTileEntityBuoy() {
        hasBuoy = false;
        searchRange = DEFAULT_RANGE;
    }
    
    protected void findNextBuoy(int yOffset) {
        if (!hasNextBuoy()) {
            ForgeDirection dir = getBuoyDirection();
            
            for (int i = 1; !hasNextBuoy() && i <= searchRange; ++i) {
                TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX * i, (yCoord + yOffset) + dir.offsetY * i, zCoord + dir.offsetZ * i);
                if (te instanceof WCTileEntityBuoy) {
                    setNextBuoy((WCTileEntityBuoy) te);
                    LogHelper.debug("Buoy get on " + dir + " me: [x: " + xCoord + ", y: " + yCoord + ", z: " + zCoord + "]" + " next: [x: " + te.xCoord + ", y: " + te.yCoord + ", z: " + te.zCoord + "]");
                }
            }
        }
    }
    
    /**
     * Sets the direction of the Buoy UP/DOWN not allowed
     * 
     * @param direction
     * @author Robotic-Brain
     * @editor xandayn
     */
    public void setDirection(ForgeDirection d) {
        // This is Buoy specific code, I don't think this belongs in the Baseclass?
        if (d == ForgeDirection.NORTH || d == ForgeDirection.SOUTH || d == ForgeDirection.WEST || d == ForgeDirection.EAST) {
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, d.ordinal(), 3); // Block Update 3=On | 2=Off
            LogHelper.debug("Buoy direction set: " + d + " at: [" + xCoord + ", " + yCoord + ", " + zCoord + "]");
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
     * @param next
     *            Next BuoyTileEntity
     * @author Robotic-Brain
     */
    public void setNextBuoy(WCTileEntityBuoy next) {
        if (next != null) {
            this.nextX = next.xCoord;
            this.nextY = next.yCoord;
            this.nextZ = next.zCoord;
            this.hasBuoy = true;
        } else {
            this.hasBuoy = false;
        }
    }
    
    /**
     * Gets the next Buoy in line
     * 
     * @return nextBuoy
     * @author Robotic-Brain
     */
    public WCTileEntityBuoy getNextBuoy() {
        if (hasNextBuoy()) {
            TileEntity te = worldObj.getBlockTileEntity(nextX, nextY, nextZ);
            if (te instanceof WCTileEntityBuoy) {
                return (WCTileEntityBuoy) te;
            }
        }
        
        return null;
    }
    
    /**
     * Has Next Buoy
     * 
     * @return true if has next buoy
     */
    public boolean hasNextBuoy() {
        return hasBuoy;
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!hasNextBuoy()) {
            findNextBuoy(0);
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (hasNextBuoy()) {
            compound.setInteger(NBT_NEXT_BUOY_X, nextX);
            compound.setInteger(NBT_NEXT_BUOY_Y, nextY);
            compound.setInteger(NBT_NEXT_BUOY_Z, nextZ);
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey(NBT_NEXT_BUOY_X) && compound.hasKey(NBT_NEXT_BUOY_Y) && compound.hasKey(NBT_NEXT_BUOY_Z)) {
            nextX = compound.getInteger(NBT_NEXT_BUOY_X);
            nextY = compound.getInteger(NBT_NEXT_BUOY_Y);
            nextZ = compound.getInteger(NBT_NEXT_BUOY_Z);
            hasBuoy = true;
        } else {
            hasBuoy = false;
        }
        
        LogHelper.debug("Loaded " + this);
    }
    
    @Override
    public String toString() {
        return "Buoy at: " + "[" + xCoord + ", " + yCoord + ", " + zCoord + "] "
             + "Next at: " + "[" + nextX + ", " + nextY + ", " + nextZ + "]";
    }
}
