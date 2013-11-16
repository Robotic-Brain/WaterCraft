package dgrxf.watercraft.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.WCEntitySmartBoat;
import dgrxf.watercraft.entity.boat.WCEntityBoatBase;
import dgrxf.watercraft.multiblock.NewDockMultiBlock;
import dgrxf.watercraft.tileentity.buoy.WCTileEntityBuoy;
import dgrxf.watercraft.util.Vector3;

/**
 * Class Created By: ??? Class Last Edited By: xandayn
 * 
 * Class Last Edited On: 11/07/2013 MM/DD/YYYY
 * 
 */

public class WCTileEntityControlUnitDock extends WCTileEntityBuoy implements ITileEntityInterfaceEvent{
    
    /**
     * Constants
     */
    private static final int UPDATE_COUNT_DOWN = 20;
    private static final int SECOND_TIMER = 3;
    
    public int activeTabIndex;
    
    private boolean multiBlockFormed;
    private int     updateTimer;
    private int     secondTimer;
    private ForgeDirection[] directions = {ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH};
    
    public WCTileEntityControlUnitDock() {
        updateTimer = UPDATE_COUNT_DOWN;
    }
    
    // TODO: redo code for this
    /*@Override
    public void updateEntity() {
        if (worldObj.isRemote) {
            return;
        }
        
        updateTimer--;
        
        if (updateTimer <= 0) {
            secondTimer++;
            
            if (!multiBlockFormed || secondTimer >= SECOND_TIMER) {
                secondTimer = 0;
                multiBlockFormed = checkForMultiBlock();
            } else {
                WCEntityBoatBase e = findEntityBoat(getBlockDirection(), WCEntityBoatBase.class);
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
    }*/
    
    /*
     * NOTE: This needs updating, should return Entity[] of all boats in List
     * list. Haven't bothered yet though for testing purposes.
     * TODO: Update this to work with the new dock code
     */
    public WCEntityBoatBase findEntityBoat(ForgeDirection d, Class<? extends WCEntityBoatBase> entC) {
        int tempX = xCoord + d.offsetX * 3;
        int tempY = yCoord;
        int tempZ = zCoord + d.offsetZ * 3;
        
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(tempX - 1, tempY - 1, tempZ - 1, tempX + 1, tempY + 1, tempZ + 1);
        
        List list = worldObj.getEntitiesWithinAABB(entC, bounds);
        
        for (int a = 0; a < list.size(); a++) {
            Entity e = (Entity) list.get(a);
            if (e instanceof WCEntityBoatBase) {
                return (WCEntityBoatBase) e;
            }
        }
        
        return null;
    }
    
    public boolean checkForMultiBlock() {
        return null != NewDockMultiBlock.checkMultiblock(worldObj, new Vector3(xCoord, yCoord, zCoord), getBlockDirection());
    }

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		activeTabIndex = compound.getInteger("activeTab");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("activeTab", activeTabIndex);
	}

	@Override
	public void receiveInterfaceEvent(byte id, byte[] extraInfo) {
		switch(id) {
			case 0:
				activeTabIndex = (int)extraInfo[0];
				break;
			default:
		}
	}
	
}
