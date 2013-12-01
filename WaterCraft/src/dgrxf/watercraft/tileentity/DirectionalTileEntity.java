package dgrxf.watercraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.block.DirectionalBlock;

/**
 * 
 * DirectionalTileEntity
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public abstract class DirectionalTileEntity extends TileEntity {
    
    /**
     * Sets the direction of the Block
     * 
     * @param direction
     */
    public void setBlockDirection(ForgeDirection d) {
        ((DirectionalBlock) Block.blocksList[worldObj.getBlockId(xCoord, yCoord, zCoord)]).setBlockDirection(worldObj, xCoord, yCoord, zCoord, d);
    }
    
    /**
     * Gets the direction of the Block
     * 
     * @return direction
     */
    public ForgeDirection getBlockDirection() {
        return ((DirectionalBlock) Block.blocksList[worldObj.getBlockId(xCoord, yCoord, zCoord)]).getBlockDirection(worldObj, xCoord, yCoord, zCoord);
    }
    
}
