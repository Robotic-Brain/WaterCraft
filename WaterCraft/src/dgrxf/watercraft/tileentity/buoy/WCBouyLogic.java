package dgrxf.watercraft.tileentity.buoy;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.WCEntityBoat;
import dgrxf.watercraft.lib.MiscInfo;
import dgrxf.watercraft.tileentity.DirectionalTileEntity;
import dgrxf.watercraft.util.LogHelper;
import dgrxf.watercraft.util.Vector3;

public abstract class WCBouyLogic extends DirectionalTileEntity {
    
    /**
     * NBT-Tags
     */
    /*private static final String NBT_NEXT_BUOY_X = "BuoyTarX";
    private static final String NBT_NEXT_BUOY_Y = "BuoyTarY";
    private static final String NBT_NEXT_BUOY_Z = "BuoyTarZ";*/
    
    /**
     * Constants
     */
    /*private static final int SEARCH_COUNT_DOWN = 10;*/
    private static final int PARTICLES_SPAWNING_TIME = 10;
    
    /**
     * Now configurable inside the Config
     */
    private static final int    DEFAULT_RANGE   = MiscInfo.BOUY_RANGE;
    
    /**
     * Fields
     */
    /*protected boolean           hasBuoy;
    protected int               nextX;                                // needed for readFromNBT
	protected int               nextY;                                // needed for readFromNBT
    protected int               nextZ;                                // needed for readFromNBT*/
    protected int               searchRange;
    //private int                 searchTimer;                          //do not save this value to nbt, there's no need
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
    /*protected WCBouyLogic nextBuoyNegX;
    protected WCBouyLogic nextBuoyY;
    protected WCBouyLogic nextBuoyNegY;*/
                                                                       
    /**
     * Default Constructor
     * 
     */
    public WCBouyLogic() {
        //hasBuoy = false;
        searchRange = DEFAULT_RANGE;
        particlesTimer = PARTICLES_SPAWNING_TIME;
        spawnParticles = false;
    }
    
    /**
     * Searches for the next buoy
     * 
     * @param yOffset
     *            vertical search offset
     */
    /*protected void findNextBuoy(int yOffset) {
        ForgeDirection dir = getBlockDirection();
        
        setNextBuoy(null);
        
        for (int i = 1; !hasNextBuoy() && i <= searchRange; ++i) {
            TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX * i, (yCoord + yOffset) + dir.offsetY * i, zCoord + dir.offsetZ * i);
            if (te instanceof WCTileEntityBuoy) {
                setNextBuoy((WCTileEntityBuoy) te);
                //LogHelper.debug("Buoy get on " + dir + " me: [x: " + xCoord + ", y: " + yCoord + ", z: " + zCoord + "]" + " next: [x: " + te.xCoord + ", y: " + te.yCoord + ", z: " + te.zCoord + "]");
            }
        }
    }*/
    
    protected void findNewBuoys() {
        //ForgeDirection dir = getBlockDirection();
        
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
    
    public void updateBuoys() {
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
     * Gets the next Buoy in line
     * 
     * @param direction Direction of next buoy
     * @return nextBuoy
     */
    public Vector3 getNextBuoyCoords(ForgeDirection direction) {
            return nextBuoys[direction.ordinal() - 2];
    }
    
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
    
    public Vector3[] getNextBuoysCoords() {
        return nextBuoys;
    }
    
    public WCEntityBoat findEntityBoat(ForgeDirection direction, Class<? extends WCEntityBoat> entC) {
        
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
    }
    
    /**
     * Has Next Buoy
     * 
     * @param direction Direction of next buoy
     * @return true if has next buoy in given direction
     */
    public boolean hasNextBuoy(ForgeDirection direction) {
        return null != getNextBuoy(direction);
    }
    
    @Override
    public void updateEntity() {
        if (worldObj.isRemote) {
            return;
        }
        
        /*searchTimer--;
        
        if (searchTimer <= 0) {
            findNextBuoy(0);
            
            WCEntityBoat e = findEntityBoat(getBlockDirection(), WCEntityBoat.class);
            WCEntitySmartBoat eS = (WCEntitySmartBoat)findEntityBoat(getBlockDirection(), WCEntitySmartBoat.class);
            
            if (eS != null && lastEntityId != eS.entityId) {
            	lastEntityId = eS.entityId;
            	eS.iterateList();
            }
            else if(eS == null)
            {
            	lastEntityId = 0;
            }
            if (e != null && hasNextBuoy()) {
            	if(!(e instanceof WCEntitySmartBoat))
            	e.setTargetLocation(new Vector2(nextX, nextZ));
            }
            
            searchTimer = SEARCH_COUNT_DOWN;
        }
        
        if (spawnParticles && particlesTimer-- <= 0) {
        	particlesTimer = PARTICLES_SPAWNING_TIME;
        	spawnParticle();
        }*/
        // Debug stuff
        /*if (timer <= 0) {
            LogHelper.debug(this);
            timer = 20;
        } else {
            timer--;
        }*/
    }
    
    private int timer = 0;
    
    @Override
    public boolean canUpdate() {
        return false;
    };
    
    /*@Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (hasNextBuoy()) {
            compound.setInteger(NBT_NEXT_BUOY_X, nextX);
            compound.setInteger(NBT_NEXT_BUOY_Y, nextY);
            compound.setInteger(NBT_NEXT_BUOY_Z, nextZ);
        }
    }*/
    
    /*@Override
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
    }*/
    
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
    
    /**
     * Returns the position of next buoy
     * 
     * @return position
     */
    /*public Vector3 getNextBuoyPos() {
        return new Vector3(nextX, nextY, nextZ);
    }*/

    @Override
    public String toString() {
        String result = "Buoy at: " + "[" + xCoord + ", " + yCoord + ", " + zCoord + "]\n";
        
        for (int i = 0; i < nextBuoys.length; i++) {
            result += "\t" + nextBuoys[i] + "\n";
        }
        return result;
    }
}
