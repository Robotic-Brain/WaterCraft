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
    private int[][][] blocks;
    
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
            
            if (nextBuoy == null) {
                findNextBouy(-1);
                System.out.println("Searching");
            }
            
            if (e != null && nextBuoy != null) {
                System.out.println("Yup");
                e.setTargetLocation(nextBuoy.xCoord, nextBuoy.yCoord);
            }
        }
        
    }
    
    @Override
    protected void findNextBouy(int yAdjust) {
        
        if (!hasBouy) {
            switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)) {
                case 0:
                    // North
                    for (int x = 1; !hasBouy && x < searchRange; x++) {
                        if (worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord - x) instanceof WCTileEntityBuoy) {
                            nextBuoy = (WCTileEntityBuoy) worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord - x);
                            hasBouy = true;
                            System.out.println("Buoy get on North at pos: {x: " + nextBuoy.xCoord + ", y: " + nextBuoy.yCoord + ", z: " + nextBuoy.zCoord + "}");
                        }
                    }
                    break;
                case 1:
                    // East
                    for (int x = 1; !hasBouy && x < searchRange; x++) {
                        if (worldObj.getBlockTileEntity(xCoord + x, yCoord-1, zCoord) instanceof WCTileEntityBuoy) {
                            nextBuoy = (WCTileEntityBuoy) worldObj.getBlockTileEntity(xCoord + x, yCoord-1, zCoord);
                            hasBouy = true;
                            System.out.println("Buoy get on East at pos: {x: " + nextBuoy.xCoord + ", y: " + nextBuoy.yCoord + ", z: " + nextBuoy.zCoord + "}");
                        }
                    }
                    break;
                case 2:
                    // South
                    for (int x = 1; !hasBouy && x < searchRange; x++) {
                        if (worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord + x) instanceof WCTileEntityBuoy) {
                            nextBuoy = (WCTileEntityBuoy) worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord + x);
                            hasBouy = true;
                            System.out.println("Buoy get on South at pos: {x: " + nextBuoy.xCoord + ", y: " + nextBuoy.yCoord + ", z: " + nextBuoy.zCoord + "}");
                        }
                    }
                    break;
                case 3:
                    // West
                    for (int x = 1; !hasBouy && x < searchRange; x++) {
                        if (worldObj.getBlockTileEntity(xCoord - x, yCoord-1, zCoord) instanceof WCTileEntityBuoy) {
                            nextBuoy = (WCTileEntityBuoy) worldObj.getBlockTileEntity(xCoord - x, yCoord-1, zCoord);
                            hasBouy = true;
                            System.out.println("Buoy get on West at pos: {x: " + nextBuoy.xCoord + ", y: " + nextBuoy.yCoord + ", z: " + nextBuoy.zCoord + "}");
                        }
                    }
                    break;
            }
        }
    }
    
    /*
     * NOTE: This needs updating, should return Entity[] of all boats in List
     * list. Haven't bothered yet though for testing purposes.
     */
    public Entity findEntityBoat(int direction, Class<? extends WCEntityBoat> entC) {
        int tempX = 0, tempZ = 0;
        switch (direction) {
            case 0:
                tempZ -= 3;
                break;
            case 1:
                tempX += 3;
                break;
            case 2:
                tempZ += 3;
                break;
            case 3:
                tempX -= 3;
                break;
        }
        
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(xCoord - 1 + tempX, yCoord - 2, zCoord - 1 + tempZ, xCoord + 1 + tempX, yCoord + 2, zCoord + 1 + tempZ);
        
        List list = worldObj.getEntitiesWithinAABB(entC, bounds);
        
        for (int a = 0; a < list.size(); a++) {
            Entity e = (Entity) list.get(a);
            if (e instanceof WCEntityBoat) {
                // System.out.println("boat");
                return e;
            }
        }
        
        return null;
    }
    
    public boolean checkForMultiBlock() {
        int tempX = xCoord;
        int tempY = yCoord - 1;
        int tempZ = zCoord;
        int val = 0;
        switch (direction) {
            case 0:
                tempZ -= 3;
                break;
            case 1:
                tempX += 3;
                break;
            case 2:
                tempZ += 3;
                break;
            case 3:
                tempX -= 3;
                break;
        }
        blocks = new int[2][5][5];
        int yL = 0;
        int inc = 0;
        for (int y = -1; y < 1; y++) {
            int xL = 0, zL = 0;
            for (int i = tempX - 2; i <= tempX + 2; i++) {
                zL = 0;
                for (int j = tempZ - 2; j <= tempZ + 2; j++) {
                    inc++;
                    if (worldObj.blockExists(i, tempY + y, j)) {
                        blocks[yL][xL][zL] = worldObj.getBlockId(i, tempY + y, j);
                        int temp = tempY + y;
                    }
                    zL++;
                }
                xL++;
            }
            yL++;
        }
        firstRun = false;
        return MultiBlockInfo.dock.isPatternCorrect(direction, blocks);
    }
}
