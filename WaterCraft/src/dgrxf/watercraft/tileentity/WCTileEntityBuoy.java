package dgrxf.watercraft.tileentity;

/**
 * Class Made By: xandayn
 * 
 * Class Last Edited By: xandayn
 * Class Last Edited By: Robotic-Brain
 * Class Last Edited On: 11/08/2013
 * 						 MM/DD/YYYY
 */

import dgrxf.watercraft.util.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class WCTileEntityBuoy extends TileEntity {
    
    protected WCTileEntityBuoy nextBuoy;
    protected boolean hasBouy = false;
    protected int searchRange = 10;
    
    /**
     * NBT-Tags
     */
    private static final String NBT_NEXT_BUOY_X = "BuoyTarX";
    private static final String NBT_NEXT_BUOY_Y = "BuoyTarY";
    private static final String NBT_NEXT_BUOY_Z = "BuoyTarZ";
    
    
    protected void findNextBouy(int yOffset, boolean oppositeDir) {
        if (!hasBouy) {
            // TODO: re-add yAdjust (whatever it is meant for) (It's used by the dock, or it was going to be but I modified the dock so its useless now.)
        	ForgeDirection dir;
        	if(!oppositeDir)
        		dir = getBuoyDirection();
        	else
        		dir = getBuoyDirection().getOpposite();
            for (int i = 1; !hasBouy && i <= searchRange; ++i) {
                TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX * i, (yCoord + yOffset) + dir.offsetY * i, zCoord + dir.offsetZ * i);
                if (te instanceof WCTileEntityBuoy) {
                    setNextBuoy((WCTileEntityBuoy) te);
                    LogHelper.debug("Buoy get on " + dir + " at pos: {x: " + nextBuoy.xCoord + ", y: " + nextBuoy.yCoord + ", z: " + nextBuoy.zCoord + "}");
                }
            }
        }
    }
    
    /**
     * Sets the direction of the Buoy
     * UP/DOWN not allowed 
     * 
     * @param direction
     * @author Robotic-Brain
     */
    public void setBuoyDirection(ForgeDirection d) {
        if (d == ForgeDirection.NORTH
         || d == ForgeDirection.SOUTH
         || d == ForgeDirection.WEST
         || d == ForgeDirection.EAST)
        {
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, d.ordinal(), 3);    // Block Update 3=On | 2=Off
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
     * @param next Next BuoyTileEntity
     * @author Robotic-Brain
     */
    public void setNextBuoy(WCTileEntityBuoy next) {
        this.nextBuoy = next;
        this.hasBouy = true;
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
            findNextBouy(0, false);
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
        //If the compound has one of the keys it will have all the keys, thats why I only checked for one, still getting a null pointed or loading though, not sure why.
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
