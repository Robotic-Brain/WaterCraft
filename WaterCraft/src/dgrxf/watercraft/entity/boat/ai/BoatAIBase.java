package dgrxf.watercraft.entity.boat.ai;

import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.entity.boat.WCEntityBoatBase;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;

/**
 * Use this AI base class for simple boats
 */
public abstract class BoatAIBase {
    
    protected WCEntityBoatBase boat;
    
    public BoatAIBase(WCEntityBoatBase boat) {
        this.boat = boat;
    }
    
    /**
     * Gets called after entityInit()
     */
    public void entityInit() {
    }
    
    /**
     * Gets called before onUpdate()
     */
    public void preOnUpdate() {
    }
    
    /**
     * Use this to move in new direction
     */
    public void updateMotion() {
    }
    
    /**
     * Gets called after onUpdate()
     */
    public void postOnUpdate() {
    }
    
    /**
     * Gets called if boat is near buoy
     */
    public void buoyFound(WCBouyLogic buoy) {
    }
    
    public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
    }
    
    public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
    }
    
}
