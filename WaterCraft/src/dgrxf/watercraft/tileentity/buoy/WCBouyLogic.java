package dgrxf.watercraft.tileentity.buoy;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.lib.MiscInfo;
import dgrxf.watercraft.tileentity.DirectionalTileEntity;
import dgrxf.watercraft.util.LogHelper;
import dgrxf.watercraft.util.Vector3;

public abstract class WCBouyLogic extends DirectionalTileEntity {
    
    /**
     * Constants
     */
    private static final int PARTICLES_SPAWNING_TIME = 10;
    
    /**
     * Now configurable inside the Config
     */
    private static final int    DEFAULT_RANGE   = MiscInfo.BOUY_RANGE;
    
    /**
     * Fields
     */
    protected int               searchRange;
    private int 				lastEntityId;
    private boolean				spawnParticles;
    private int					particlesTimer;
    
    /**
     * Stores the next buoy in given direction
     * (Don't save to nbt)
     * 
     * Mapping: NORTH, SOUTH, WEST, EAST
     */
    protected Vector3[] nextBuoys = new Vector3[4];
                                                                       
    /**
     * Default Constructor
     */
    public WCBouyLogic() {
        searchRange = DEFAULT_RANGE;
        particlesTimer = PARTICLES_SPAWNING_TIME;
        spawnParticles = false;
    }
    
    /**
     * Searches for the next buoys in range
     */
    protected void findNewBuoys() {
        for (int d = ForgeDirection.NORTH.ordinal(); d <= ForgeDirection.EAST.ordinal(); ++d) {
            ForgeDirection dir = ForgeDirection.getOrientation(d);
            
            for (int i = 1; !hasNextBuoy(dir) && i <= searchRange; ++i) {
                TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX * i, yCoord + dir.offsetY * i, zCoord + dir.offsetZ * i);
                if (te instanceof WCTileEntityBuoy) {
                    setNextBuoy((WCTileEntityBuoy) te, dir);
                    ((WCTileEntityBuoy) te).updateBuoys();
                    LogHelper.debug("Buoy get on " + dir + " me: [x: " + xCoord + ", y: " + yCoord + ", z: " + zCoord + "]" + " next: [x: " + te.xCoord + ", y: " + te.yCoord + ", z: " + te.zCoord + "]");
                }
            }
        }
    }
    
    /**
     * This checks if listed buoys still exist and adds new ones
     */
    public void updateBuoys() {
        LogHelper.debug("Buoy Update!");
        for (int i = 0; i < nextBuoys.length; i++) {
            Vector3 p = nextBuoys[i];
            if (p != null) {
                TileEntity te = worldObj.getBlockTileEntity((int) p.x, (int) p.y, (int) p.z);
                if (!(te instanceof WCBouyLogic)) {
                    nextBuoys[i] = null;
                }
            }
        }
        
        findNewBuoys();
    }
    
    /**
     * Sets the next Buoy in line
     * 
     * @param next Next BuoyTileEntity
     * @param direction Direction of next buoy
     */
    public void setNextBuoy(WCTileEntityBuoy next, ForgeDirection direction) {
        nextBuoys[direction.ordinal() - 2] = new Vector3(next.xCoord, next.yCoord, next.zCoord);
    }
    
    /**
     * Gets the next Buoy Coordinates in line
     * 
     * @param direction Direction of next buoy
     * @return nextBuoy
     */
    public Vector3 getNextBuoyCoords(ForgeDirection direction) {
            return nextBuoys[direction.ordinal() - 2];
    }
    
    /**
     * Gets the next Buoy TE in given direction or null
     * 
     * @param direction Direction to travel
     * @return Next buoy
     */
    public WCBouyLogic getNextBuoy(ForgeDirection direction) {
        Vector3 p = getNextBuoyCoords(direction);
        if (p != null) {
            TileEntity te = worldObj.getBlockTileEntity((int) p.x, (int) p.y, (int) p.z);
            if (te instanceof WCBouyLogic) {
                return (WCBouyLogic) te;
            }
        }
        
        return null;
    }
    
    /**
     * Gets all stored Buoy coordinates
     * 
     * @return Buoy coordinate array
     */
    public Vector3[] getNextBuoysCoords() {
        return nextBuoys;
    }
    
    /*public WCEntityBoat findEntityBoat(ForgeDirection direction, Class<? extends WCEntityBoat> entC) {
        
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 2, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
        
        List list = worldObj.getEntitiesWithinAABB(entC, bounds);

    	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        for (int a = 0; a < list.size(); a++) {
            Entity e = (Entity) list.get(a);
            if (e instanceof WCEntityBoat) {
                return (WCEntityBoat) e;
            }
        }
        return null;
    }*/
    
    /**
     * Has Next Buoy
     * 
     * @param direction Direction of next buoy
     * @return true if has next buoy in given direction
     */
    public boolean hasNextBuoy(ForgeDirection direction) {
        return null != getNextBuoy(direction);
    }
    
    /**
     * Buoys don't have to tick anymore
     */
    @Override
    public boolean canUpdate() {
        return false;
    };
    
    /*private void spawnParticle() {
    	if (hasNextBuoy()) {
           	float distance = (new Vector3(xCoord, yCoord, zCoord)).sub(getNextBuoyPos()).length();
           	float horizontalSpeed = distance / BuoyParticle.getFlyTime();
        	float verticalSpeed = BuoyParticle.getGravity() * BuoyParticle.getFlyTime() / 2.0F;
           	
           	Vector3 velocity = (new Vector3(getBlockDirection())).scalarMult(horizontalSpeed).add(new Vector3(0, verticalSpeed, 0));
            CustomParticles.BUOY.spawnParticle(worldObj, xCoord + 0.5F, yCoord + 1, zCoord + 0.5F, velocity.x, velocity.y, velocity.z);
        }
    }*/
    
    /*public void enableSpawning() {
    	spawnParticles = !spawnParticles;
    }*/

    @Override
    public String toString() {
        String result = "Buoy at: " + "[" + xCoord + ", " + yCoord + ", " + zCoord + "] " + getBlockDirection() + "\n";
        
        for (int i = 0; i < nextBuoys.length; i++) {
            result += "\t" + nextBuoys[i] + "\n";
        }
        return result;
    }
}
