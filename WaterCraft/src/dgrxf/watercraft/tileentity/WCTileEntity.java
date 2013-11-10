package dgrxf.watercraft.tileentity;

import dgrxf.watercraft.util.LogHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * Watercraft TileEntity Baseclass
 * 
 */
public class WCTileEntity extends TileEntity {
    
    /**
     * Sets the direction of the Buoy UP/DOWN not allowed
     * 
     * @param direction
     * @author Robotic-Brain
     * @editor xandayn
     */
    public void setDirection(ForgeDirection d) {
        if (d == ForgeDirection.NORTH || d == ForgeDirection.SOUTH || d == ForgeDirection.WEST || d == ForgeDirection.EAST) {
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, d.ordinal(), 3); // Block Update 3=On | 2=Off
            LogHelper.debug("Buoy direction set: " + d + " at: [" + xCoord + ", " + yCoord + ", " + zCoord + "]");
        }
    }
    
}
