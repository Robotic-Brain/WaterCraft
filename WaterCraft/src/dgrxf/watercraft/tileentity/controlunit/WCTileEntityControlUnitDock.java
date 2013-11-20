package dgrxf.watercraft.tileentity.controlunit;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.DumbBoat;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.multiblock.NewDockMultiBlock;
import dgrxf.watercraft.tileentity.ITileEntityInterfaceEvent;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;
import dgrxf.watercraft.tileentity.controlunit.logic.ControlUnitLogic;
import dgrxf.watercraft.util.Vector3;

/**
 * Class Created By: ??? Class Last Edited By: xandayn
 * 
 * Class Last Edited On: 11/07/2013 MM/DD/YYYY
 * 
 */

public class WCTileEntityControlUnitDock extends WCBouyLogic implements ITileEntityInterfaceEvent {
    
    /**
     * Constants
     */
    private static final int UPDATE_COUNT_DOWN = 20;
    
    public int               activeTabIndex;
    public boolean           basicTab, chestTab, tankTab;
    
    public AbstractBaseBoat dockedBoat;
    private boolean          multiBlockFormed, holdBoat;
    private int              updateTimer;
    private ForgeDirection[] directions        = { ForgeDirection.NORTH,
            ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH };
    
    //Logic
    private ControlUnitLogic basicLogic = ControlUnitLogic.basicOnInteraction;
    
    
    public WCTileEntityControlUnitDock() {
        updateTimer = UPDATE_COUNT_DOWN;
        multiBlockFormed = false;
        basicTab = true;
        chestTab = false;
        tankTab = false;
        setHoldBoat(true);
    }
    
    @Override
    public boolean canUpdate() {
        return true;
    }
    
    
    @Override
    public void updateEntity() {
    	if(worldObj.isRemote) return;
    	updateTimer--;
        if(updateTimer <= 0){
        	if(!multiBlockFormed){
        		multiBlockFormed = checkForMultiBlock();
        	}else if(multiBlockFormed){
        		AbstractBaseBoat e = findEntityBoat(getBlockDirection(), AbstractBaseBoat.class);
        		
        		if(e != null){
        			//This is ran only once when the boat enters the ABB of the controlUnit
        			if(!isBoatInCenter(e)){
        				dockedBoat = e;
        				setHoldBoat(true);
        				positionBoatInCenter(e);
        			}
        			e.isIdle = getHoldBoat();
        			getLoadedLogic(e);
        		}
        	}
        	updateTimer = 20;
        }
    }

    private void getLoadedLogic(AbstractBaseBoat e) {
		if(e instanceof DumbBoat){
			if(basicLogic != null){
				basicLogic.runLogic(worldObj, xCoord, yCoord, zCoord);
			}
		}
	}

	public AbstractBaseBoat findEntityBoat(ForgeDirection d, Class<? extends AbstractBaseBoat> entC) {
        int tempX = xCoord + d.offsetX * 3;
        int tempY = yCoord;
        int tempZ = zCoord + d.offsetZ * 3;
        
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(tempX - 1, tempY - 1, tempZ - 1, tempX + 1, tempY + 1, tempZ + 1);
        
        List list = worldObj.getEntitiesWithinAABB(entC, bounds);
        
        for (int a = 0; a < list.size(); a++) {
            Entity e = (Entity) list.get(a);
            if (e instanceof AbstractBaseBoat) {
                return (AbstractBaseBoat) e;
            }
        }
        
        return null;
    }
    
    public void positionBoatInCenter(AbstractBaseBoat boat){
    	int tempX = getBoatTempX();
    	int tempZ = getBoatTempZ();
    	boat.posX = tempX;
    	boat.posZ = tempZ;
    	boat.setPosition(boat.posX, boat.posY, boat.posZ);
    }
    
    public boolean isBoatInCenter(AbstractBaseBoat boat){
    	int tempX = getBoatTempX();
    	int tempZ = getBoatTempZ();
    	if(boat.posX == tempX && boat.posZ == tempZ)
    		return true;
    	else
    		return false;
    }
    
    public int getBoatTempX(){
    	int tempX = xCoord;
    	switch(getBlockDirection()){
			case EAST:
				tempX = tempX + 3;
				break;
			case WEST:
				tempX = tempX - 3;
				break;
			default:
				break;
    	}
    	return tempX;
    }
    
    public int getBoatTempZ(){
    	int tempZ = zCoord;
    	switch(getBlockDirection()){
			case NORTH:
				tempZ = tempZ - 3;
				break;
			case SOUTH:
				tempZ = tempZ + 3;
				break;
			default:
				break;
    	}
    	return tempZ;
    }
    
    public boolean checkForMultiBlock() {
        return null != NewDockMultiBlock.checkMultiblock(worldObj, new Vector3(xCoord, yCoord - 1, zCoord), getBlockDirection());
    }
    
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        tankTab = compound.getBoolean("tankTab");
        chestTab = compound.getBoolean("chestTab");
        activeTabIndex = compound.getInteger("activeTab");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("activeTab", activeTabIndex);
        compound.setBoolean("tankTab", tankTab);
        compound.setBoolean("chestTab", chestTab);
    }
    
    @Override
    public void receiveInterfaceEvent(byte id, byte[] extraInfo) {
        switch (id) {
            case 0:
                activeTabIndex = extraInfo[0];
                break;
            default:
        }
    }

	public boolean canBeUpgraded(ItemStack stack) {
		System.out.println("Tile");
		if(stack.getItem().itemID == ModItems.upgrades.itemID){
			switch(stack.getItemDamage()){
				case 1:
					if(!chestTab){
						chestTab = true;
						return true;
					}
					break;
				case 2:
					if(!tankTab){
						tankTab = true;
						return true;
					}
					break;
			}
		}
		return false;
	}
		
	public void setBasicLogic(ControlUnitLogic basicLogic) {
		this.basicLogic = basicLogic;
	}

	public boolean getHoldBoat() {
		return holdBoat;
	}

	public void setHoldBoat(boolean holdBoat) {
		this.holdBoat = holdBoat;
	}
}
