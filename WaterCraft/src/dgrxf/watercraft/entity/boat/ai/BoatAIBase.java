package dgrxf.watercraft.entity.boat.ai;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;

/**
 * Use this AI base class for simple boats
 */
public abstract class BoatAIBase {
    
    protected AbstractBaseBoat boat;
    
    public BoatAIBase(AbstractBaseBoat boat) {
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
    
    /**
     * Gets called
     */
    public boolean attackEntityFrom(DamageSource source, float damage) {
        return true;
    }
    
    /**
     * Gets called when the player right clicks a boat
     */
    public void onInteractFirst(EntityPlayer player){
    }
    
    public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
    }
    
    public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
    }
    
}
