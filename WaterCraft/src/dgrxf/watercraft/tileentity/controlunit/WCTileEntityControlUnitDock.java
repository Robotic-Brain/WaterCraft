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
import dgrxf.watercraft.entity.boat.ModularBoat;
import dgrxf.watercraft.multiblock.NewDockMultiBlock;
import dgrxf.watercraft.tileentity.ITileEntityInterfaceEvent;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;
import dgrxf.watercraft.tileentity.buoy.WCTileEntityBuoy;
import dgrxf.watercraft.tileentity.controlunit.logic.ControlUnitLogic;
import dgrxf.watercraft.util.LogHelper;
import dgrxf.watercraft.util.Rectangle;
import dgrxf.watercraft.util.Vector3;

/**
 * 
 * WCTileEntityControlUnitDock
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class WCTileEntityControlUnitDock extends WCBouyLogic implements ITileEntityInterfaceEvent {
    
    /** Constants **/
    private static final int UPDATE_COUNT_DOWN = 20;
    
    /** Fields **/
    private boolean          multiBlockFormed;
    private int              updateTimer;
    private int              multiBlockTimer;
    
    /** Tabs **/
    public int               activeTabIndex;
    public boolean           basicTab, chestTab, tankTab;
    
    /** Boats **/
    public AbstractBaseBoat dockedBoat;
    public boolean          holdBoat;
    
    private Rectangle		AABBBounds;
    
    /**Logic **/
    private ControlUnitLogic basicLogic = ControlUnitLogic.basicOnInteraction;
    private ControlUnitLogic chestLogic;
    private ControlUnitLogic tankLogic;
    
    public WCTileEntityControlUnitDock() {
        updateTimer = UPDATE_COUNT_DOWN;
        multiBlockTimer = 0;
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
        	
        	/** 
        	 * Checks to see if there is a multiblock and also if it still exists 
        	 * 
        	 * Runs once on start up and then ever 40 ticks.
        	 **/
        	multiBlockTimer--;
        	if(multiBlockTimer <= 0){
        		multiBlockFormed = checkForMultiBlock();
        		multiBlockTimer = UPDATE_COUNT_DOWN;
        	}
        	
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
        	updateTimer = UPDATE_COUNT_DOWN;
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
     * This will check to see what boat is inside the AAB and will run the set Logic depending on it's type
     * 
     * This is no good, this isn't generic, we'd have to change this every time we were to add more logic.
     * 
     * @param Boat inside the AAB
     */
    private void runLogic(AbstractBaseBoat e) {
        if (e instanceof ModularBoat)
            logic(basicLogic);
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
        
        AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(this.AABBBounds.x, tempY - 1, this.AABBBounds.y, this.AABBBounds.w + this.AABBBounds.x, tempY + 1, this.AABBBounds.h + this.AABBBounds.y);
        List list = worldObj.getEntitiesWithinAABB(entC, bounds);
        
        for (int a = 0; a < list.size(); a++) {
            Entity e = (Entity) list.get(a);
            if (e instanceof AbstractBaseBoat) {
            	System.out.println("test");
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
    	this.AABBBounds = NewDockMultiBlock.checkMultiblock(worldObj, new Vector3(xCoord, yCoord - 1, zCoord), getBlockDirection());
        return null != this.AABBBounds;
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
	//	if(stack.getItem().itemID == ModItems.upgrades.itemID){
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
	//	}
		return false;
	}
		
	public void setBasicLogic(ControlUnitLogic basicLogic) {
		this.basicLogic = basicLogic;
	}
}
