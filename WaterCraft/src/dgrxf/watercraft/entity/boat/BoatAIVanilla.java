package dgrxf.watercraft.entity.boat;

import net.minecraft.entity.EntityLivingBase;
import dgrxf.watercraft.entity.WCEntityBoatBase;

public class BoatAIVanilla extends BoatAIBase {
    
    public BoatAIVanilla(WCEntityBoatBase boat) {
        super(boat);
    }
    
    @Override
    public void updateMotion() {
        // ---------- PLAYER STEERING [START]
        if (boat.riddenByEntity != null && boat.riddenByEntity instanceof EntityLivingBase)
        {
            double forwardSpeed = (double)((EntityLivingBase)boat.riddenByEntity).moveForward;
            forwardSpeed = 0.5D;

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
