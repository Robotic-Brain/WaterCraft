package dgrxf.watercraft.entity.boat.ai.tasks;

import net.minecraft.entity.EntityLivingBase;
import dgrxf.watercraft.entity.boat.WCEntityBoatBase;

public class VanillaTask extends BoatAITaskBase {

    public VanillaTask(WCEntityBoatBase boat, float priority) {
        super(boat, priority);
    }
    
    @Override
    public void updateMotion() {
        // ---------- PLAYER STEERING [START]
        if (boat.riddenByEntity != null && boat.riddenByEntity instanceof EntityLivingBase)
        {
            double forwardSpeed = (double)((EntityLivingBase)boat.riddenByEntity).moveForward;

            if (forwardSpeed > 0.0D)
            {
                double facingXcomp = -Math.sin((double)(boat.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
                double facingZcomp = Math.cos((double)(boat.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
                boat.motionX += facingXcomp * boat.speedMultiplier * 0.05000000074505806D;
                boat.motionZ += facingZcomp * boat.speedMultiplier * 0.05000000074505806D;
            }
        }
        // ---------- PLAYER STEERING [END]
    }
    
}
