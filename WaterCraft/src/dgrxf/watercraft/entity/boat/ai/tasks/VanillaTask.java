package dgrxf.watercraft.entity.boat.ai.tasks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.item.ModItems;

/**
 * 
 * VanillaTask
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class VanillaTask extends BoatAITaskBase {
    
    public VanillaTask(AbstractBaseBoat boat, Float priority, Object... args) {
        super(boat, priority);
    }
    
    @Override
    public void updateMotion() {
        // ---------- PLAYER STEERING [START]
        if (boat.riddenByEntity != null && boat.riddenByEntity instanceof EntityLivingBase) {
            double forwardSpeed = ((EntityLivingBase) boat.riddenByEntity).moveForward;
            
            if (forwardSpeed > 0.0D) {
                double facingXcomp = -Math.sin(boat.riddenByEntity.rotationYaw * (float) Math.PI / 180.0F);
                double facingZcomp = Math.cos(boat.riddenByEntity.rotationYaw * (float) Math.PI / 180.0F);
                boat.motionX += facingXcomp * boat.speedMultiplier * 0.05000000074505806D;
                boat.motionZ += facingZcomp * boat.speedMultiplier * 0.05000000074505806D;
            }
        }
        // ---------- PLAYER STEERING [END]
    }
    
    @Override
    public void onInteractFirst(EntityPlayer player) {
        if (boat.riddenByEntity == null) {
        	if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == ModItems.ROPE.getItem()){
        		return;
        	}
            if (!boat.worldObj.isRemote) {
                player.mountEntity(boat);
            }
        }
    }
    
}
