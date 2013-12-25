package dgrxf.watercraft.entity.boat.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;

/**
 * BoatAIBase
 * 
 * Use this AI base class for simple boats
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
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
     * Gets called when the boat takes damage
     */
    public boolean attackEntityFrom(DamageSource source, float damage) {
        return true;
    }
    
    /**
     * Gets called just before the boat has been destroyed
     */
    public void setDead(){}
    
    /**
     * Gets called when the player right clicks a boat
     */
    public void onInteractFirst(EntityPlayer player){
    }
    
    /**
     * Gets called when the boat collides with another entity
     */
    public void applyEntityCollision(Entity par1Entity) {}
    
    /**
     * Return false to prevent boat breaking from collisions
     */
    public boolean breakBoat() {
        return true;
    }
    
    /**
     * This gets called if a signal was sent to a boat
     * 
     * @param signal send signal
     * @param sender Object which send the signal
     * @param args  arguments the sender needs to track state
     */
    public void onSignalReceived(String signal, ISignalSender sender, Object... args) {
    }
    
    public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
    }
    
    public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
    }
}
