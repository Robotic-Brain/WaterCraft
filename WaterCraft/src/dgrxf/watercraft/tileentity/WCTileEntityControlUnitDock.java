package dgrxf.watercraft.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.WCEntityBoat;
import dgrxf.watercraft.entity.WCEntitySmartBoat;
import dgrxf.watercraft.lib.MultiBlockInfo;
import dgrxf.watercraft.util.Vector2;

/**
 * Class Created By: ??? Class Last Edited By: xandayn
 * 
 * Class Last Edited On: 11/07/2013 MM/DD/YYYY
 * 
 */

public class WCTileEntityControlUnitDock extends WCTileEntityBuoy {
    
    /**
     * Constants
     */
    private static final int UPDATE_COUNT_DOWN = 20;
    private static final int SECOND_TIMER = 3;
    
    private boolean multiBlockFormed;
    private int     updateTimer;
    private int     secondTimer;
    private ForgeDirection[] directions = {ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH};
    
    public WCTileEntityControlUnitDock() {
        updateTimer = UPDATE_COUNT_DOWN;
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
            
            if (!multiBlockFormed || secondTimer >= SECOND_TIMER) {
                secondTimer = 0;
                multiBlockFormed = checkForMultiBlock();
            } else {
                WCEntityBoat e = findEntityBoat(getBlockDirection(), WCEntityBoat.class);
                WCEntitySmartBoat eS = (WCEntitySmartBoat)findEntityBoat(getBlockDirection(), WCEntitySmartBoat.class);
                
                if(eS != null){
                	if(directions != null){
                		if(eS instanceof WCEntitySmartBoat){
                			eS.setList(directions);
                		}
                	}
                }
                
                if (e != null && hasNextBuoy()) {
                	if(!(e instanceof WCEntitySmartBoat))
                	e.setTargetLocation(new Vector2(nextX, nextZ));
                }
            }
            
            updateTimer = UPDATE_COUNT_DOWN;
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

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
	}
}
