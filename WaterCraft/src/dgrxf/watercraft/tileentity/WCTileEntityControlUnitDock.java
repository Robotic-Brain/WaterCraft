package dgrxf.watercraft.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.WCEntityBoat;
import dgrxf.watercraft.lib.MultiBlockInfo;
import dgrxf.watercraft.util.LogHelper;
import dgrxf.watercraft.util.Vector2;

/**
 * Class Created By: ??? Class Last Edited By: xandayn
 * 
 * Class Last Edited On: 11/07/2013 MM/DD/YYYY
 * 
 */

public class WCTileEntityControlUnitDock extends WCTileEntityBuoy {
    
    private boolean multiBlockFormed;
    private int     updateTimer;
    private int     secondTimer;     //This fixes bug report #1 and #2
                                      
    public WCTileEntityControlUnitDock() {
        updateTimer = 20;
    }
    
    @Override
    public void updateEntity() {
        if (worldObj.isRemote) {
            return;
        }
        
        updateTimer--;
        
        if (updateTimer <= 0) {
            secondTimer++;
            findNextBuoy(-1);
            
            if (!multiBlockFormed || secondTimer >= 3) {
                secondTimer = 0;
                multiBlockFormed = checkForMultiBlock();
            } else {
                WCEntityBoat e = findEntityBoat(getBuoyDirection(), WCEntityBoat.class);
                
                if (e != null && hasNextBuoy()) {
                    e.setTargetLocation(new Vector2(nextX, nextZ));
                }
            }
            
            updateTimer = 20;
        }
    }
    
    /*
     * NOTE: This needs updating, should return Entity[] of all boats in List
     * list. Haven't bothered yet though for testing purposes.
     */
    @Override
    public WCEntityBoat findEntityBoat(ForgeDirection d, Class<? extends WCEntityBoat> entC) {
        int tempX = xCoord + d.offsetX * 3;
        int tempY = yCoord - 1;
        int tempZ = zCoord + d.offsetZ * 3;
        
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(tempX - 1, tempY - 1, tempZ - 1, tempX + 1, tempY + 1, tempZ + 1);
        
        List list = worldObj.getEntitiesWithinAABB(entC, bounds);
        
        for (int a = 0; a < list.size(); a++) {
            Entity e = (Entity) list.get(a);
            if (e instanceof WCEntityBoat) {
                return (WCEntityBoat) e;
            }
        }
        
        return null;
    }
    
    public boolean checkForMultiBlock() {
        return MultiBlockInfo.dock.getMultiBlock(getWorldObj(), xCoord, yCoord, zCoord, getWorldObj().getBlockMetadata(xCoord, yCoord, zCoord));
    }
}
