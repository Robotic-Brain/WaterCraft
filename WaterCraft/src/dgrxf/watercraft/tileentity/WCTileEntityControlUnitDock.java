package dgrxf.watercraft.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import dgrxf.watercraft.entity.WCEntityBoat;
import dgrxf.watercraft.lib.MultiBlockInfo;

/**
 * Class Created By: ???
 * Class Last Edited By: xandayn
 * 
 * Class Last Edited On: 11/07/2013 
 *                       MM/DD/YYYY
 * 
 */

public class WCTileEntityControlUnitDock extends WCTileEntityBuoy {
    
    private boolean firstRun, multiBlockFormed;
    private int direction;
    private int updateTimer;
    
    public WCTileEntityControlUnitDock() {
        firstRun = true;
    }
    
    @Override
    public void updateEntity() {
        if (worldObj.isRemote)
            return;
        
        if (firstRun) {
            direction = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            multiBlockFormed = checkForMultiBlock();
            System.out.println(multiBlockFormed ? "It worked" : "Damn");
        }
        
        if (!multiBlockFormed) {
            updateTimer++;
            if (updateTimer >= 20) {
                multiBlockFormed = checkForMultiBlock();
                updateTimer = 0;
                System.out.println(multiBlockFormed ? "It worked" : "Damn");
            }
        } else {
            WCEntityBoat e = (WCEntityBoat) findEntityBoat(direction, WCEntityBoat.class);
            
            if (hasNextBuoy()) {
                findNextBuoy(-1);
            }
            
            if (e != null && hasNextBuoy()) {
                e.setTargetLocation(nextX, nextY);
            }
        }
        
    }
    
    /*
     * NOTE: This needs updating, should return Entity[] of all boats in List
     * list. Haven't bothered yet though for testing purposes.
     */
    public Entity findEntityBoat(int direction, Class<? extends WCEntityBoat> entC) {
        int tempX = xCoord, tempZ = zCoord;
        switch (direction-2) {
        case 0:
            tempZ -= 3;
            break;
        case 1:
            tempZ += 3;
            break;
        case 2:
            tempX -= 3;
            break;
        case 3:
            tempX += 3;
            break;
    }
        
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(tempX - 1, yCoord - 2, tempZ - 1, 1 + tempX, yCoord + 2, 1 + tempZ);
        
        List list = worldObj.getEntitiesWithinAABB(entC, bounds);
        
        for (int a = 0; a < list.size(); a++) {
            Entity e = (Entity) list.get(a);
            if (e instanceof WCEntityBoat) {
            	System.out.println("Boat Get");
                return e;
            }
        }
        
        return null;
    }
    
    public boolean checkForMultiBlock() {
        firstRun = false;
        return MultiBlockInfo.dock.getMultiBlock(getWorldObj(), xCoord, yCoord, zCoord, direction);
    }
}
