package dgrxf.watercraft.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.WCEntityBoat;
import dgrxf.watercraft.lib.MiscInfo;
import dgrxf.watercraft.util.LogHelper;
import dgrxf.watercraft.util.Vector2;

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
    private int					searchTimer; 			     //do not save this value to nbt, there's no need
    
    /**
     * Default Constructor
     * 
     */
    public WCTileEntityBuoy() {
        hasBuoy = false;
        searchRange = DEFAULT_RANGE;
    }
    
    /**
     * Searches for the next buoy
     * 
     * @param yOffset   vertical search offset
     */
    protected void findNextBuoy(int yOffset) {
            ForgeDirection dir = getBuoyDirection();
            
            setNextBuoy(null);
            
            for (int i = 1; !hasNextBuoy() && i <= searchRange; ++i) {
                TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX * i, (yCoord + yOffset) + dir.offsetY * i, zCoord + dir.offsetZ * i);
                if (te instanceof WCTileEntityBuoy) {
                    setNextBuoy((WCTileEntityBuoy) te);
                    //LogHelper.debug("Buoy get on " + dir + " me: [x: " + xCoord + ", y: " + yCoord + ", z: " + zCoord + "]" + " next: [x: " + te.xCoord + ", y: " + te.yCoord + ", z: " + te.zCoord + "]");
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
    
    public WCEntityBoat findEntityBoat(ForgeDirection direction, Class<? extends WCEntityBoat> entC) {
        
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 2, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
        
        List list = worldObj.getEntitiesWithinAABB(entC, bounds);
        
        for (int a = 0; a < list.size(); a++) {
            Entity e = (Entity) list.get(a);
            if (e instanceof WCEntityBoat) {
            	System.out.println("Boat Get");
                return (WCEntityBoat)e;
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
        if (worldObj.isRemote) {
            return;
        }
    	
        searchTimer--;
        
        if (searchTimer <= 0) {
            findNextBuoy(0);
            
            WCEntityBoat e = findEntityBoat(getBuoyDirection(), WCEntityBoat.class);
            if(e != null && hasNextBuoy()){
                e.setTargetLocation(new Vector2(nextX, nextZ));
            }
            
            searchTimer = 20;
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
