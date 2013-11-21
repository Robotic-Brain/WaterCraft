package dgrxf.watercraft.tileentity.controlunit;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.ChestBoat;
import dgrxf.watercraft.entity.boat.DumbBoat;
import dgrxf.watercraft.entity.boat.TankBoat;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.multiblock.NewDockMultiBlock;
import dgrxf.watercraft.tileentity.ITileEntityInterfaceEvent;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;
import dgrxf.watercraft.tileentity.buoy.WCTileEntityBuoy;
import dgrxf.watercraft.tileentity.controlunit.logic.ControlUnitLogic;
import dgrxf.watercraft.util.LogHelper;
import dgrxf.watercraft.util.Vector3;

/**
 * Class Created By: ??? Class Last Edited By: xandayn
 * 
 * Class Last Edited On: 11/07/2013 MM/DD/YYYY
 * 
 */

public class WCTileEntityControlUnitDock extends WCBouyLogic implements ITileEntityInterfaceEvent {
    
    /** Constants **/
    private static final int UPDATE_COUNT_DOWN = 20;
    private ForgeDirection[] directions        = { ForgeDirection.NORTH,
            ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH };
    
    /** Fields **/
    private boolean          multiBlockFormed;
    private int              updateTimer;
    
    /** Tabs **/
    public int               activeTabIndex;
    public boolean           basicTab, chestTab, tankTab;
    
    /** Boats **/
    public AbstractBaseBoat dockedBoat;
    public boolean          holdBoat;
    
    /**Logic **/
    private ControlUnitLogic basicLogic = ControlUnitLogic.basicOnInteraction;
    private ControlUnitLogic chestLogic;
    private ControlUnitLogic tankLogic;
    
    public WCTileEntityControlUnitDock() {
        updateTimer = UPDATE_COUNT_DOWN;
        multiBlockFormed = false;
        basicTab = true;
        chestTab = false;
        tankTab = false;
        holdBoat = true;
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
        	/** Checks to see if there is a multiblock and also if it still exists **/
        	multiBlockFormed = checkForMultiBlock();
        	
        	if(multiBlockFormed){
        		AbstractBaseBoat e = findEntityBoat(getBlockDirection(), AbstractBaseBoat.class);
        		if(e != null){
        			/** This is only ran once (When the boat enters the ABB **/
        			if(!isBoatInCenter(e)){
        				dockedBoat = e;
        				holdBoat = true;
        				positionBoatInCenter(e);
        			}
        			/** Runs the Logic **/
        			runLogic(e);
        			
        			/** Runs when the Logic that is set by the user is finished **/
        			if(!holdBoat){
        				updateBuoys();
        				e.isIdle = false;
        				e.ai.buoyFound(this);;
        			}
        		}
        	}
        	updateTimer = 20;
        }
    }
    
    /**
     * This is Overridden as it is needs to check one Y level below it's current yCoord 
     */
    
    @Override
    protected void findNewBuoys() {
        for (int d = ForgeDirection.NORTH.ordinal(); d <= ForgeDirection.EAST.ordinal(); ++d) {
            ForgeDirection dir = ForgeDirection.getOrientation(d);
            
            for (int i = 1; !hasNextBuoy(dir) && i <= searchRange; ++i) {
                TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX * i, (yCoord - 1) + dir.offsetY * i, zCoord + dir.offsetZ * i);
                if (te instanceof WCTileEntityBuoy) {
                    setNextBuoy((WCTileEntityBuoy) te, dir);
                    ((WCTileEntityBuoy) te).updateBuoys();
                    LogHelper.debug("Buoy get on " + dir + " me: [x: " + xCoord + ", y: " + yCoord + ", z: " + zCoord + "]" + " next: [x: " + te.xCoord + ", y: " + te.yCoord + ", z: " + te.zCoord + "]");
                }
            }
        }
    }

    /**
     * 
     * This will check to see what boat is inside the AAB and will run the set Logic depending on it's type
     * 
     * @param Boat inside the AAB
     */
    
    private void runLogic(AbstractBaseBoat e) {
    	switch(e.boatType){
	    	case simpleBoat:
	    		logic(basicLogic);
	    		break;
			case LavaBoat:
				logic(basicLogic);
				break;
			case ModularBoat:
				logic(basicLogic);
				break;
			case chestBoat:
				if(chestTab) logic(chestLogic); else logic(basicLogic);
				break;
			case iceBoat:
				logic(basicLogic);
				break;
			case tankBoat:
				if(tankTab) logic(tankLogic); else logic(basicLogic);
				break;
    	}
	}
    /**
     * Runs the logic
     * 
     * @param Logic you want it to run
     */
    public void logic(ControlUnitLogic logic){
    	if(logic != null){
    		logic.runLogic(worldObj, xCoord, yCoord, zCoord);
		}
    }
    /**
     * Gets any boats inside the AAB
     * 
     * @param The Direction the block is facing
     * @param The boat it's looking for
     * @return Returns the boat inside the AAB
     */
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
	
	/**
     * Checks to see if the boat is in the centre of the dock
     * 
     * @param Boat inside the AAB
     */  
    public void positionBoatInCenter(AbstractBaseBoat boat){
    	int tempX = getBoatTempX();
    	int tempZ = getBoatTempZ();
    	boat.posX = tempX;
    	boat.posZ = tempZ;
        boat.setPosition(boat.posX, boat.posY, boat.posZ);
    }
    
    /**
     * Checks to see if the boat is in the centre of the dock
     * 
     * @param Boat inside the AAB
     * @return True if the boat is in the centre
     */
    public boolean isBoatInCenter(AbstractBaseBoat boat){
    	int tempX = getBoatTempX();
    	int tempZ = getBoatTempZ();
    	if(boat.posX == tempX && boat.posZ == tempZ)
    		return true;
    	else
    		return false;
    }
    
    /**
     * Gets the X depending on the direction
     * 
     * @return X Coord
     */
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
    
  /**
   * Gets the Z depending on the direction
   * 
   * @return Z Coord
   */
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
}
