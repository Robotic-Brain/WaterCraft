package dgrxf.watercraft.entity.boat.ai.tasks;

import dgrxf.watercraft.entity.boat.WCEntityBoatBase;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;
import dgrxf.watercraft.util.LogHelper;
import dgrxf.watercraft.util.MathHelper;
import dgrxf.watercraft.util.Vector2;

public class DumbTask extends BoatAITaskBase {
    
    private Vector2 target;
    
    public DumbTask(WCEntityBoatBase boat, float priority) {
        super(boat, priority);
    }
    
    @Override
    public void updateMotion() {
        moveToTarget();
    }
    
    @Override
    public void buoyFound(WCBouyLogic buoy) {
        if (buoy.hasNextBuoy(buoy.getBlockDirection())) {
            setTargetLocation(buoy.getNextBuoyCoords(buoy.getBlockDirection()).xz());
            LogHelper.debug("Traget set");
        }
    }
    
    private static final double BOAT_SPEED = 0.05;
    
    private void moveToTarget() {
        float xDist, zDist;
        if (target == null || boat.worldObj.isRemote) {
            return;
        }
        xDist = MathHelper.calculatePointDistance((float) boat.posX, target.x);
        zDist = MathHelper.calculatePointDistance((float) boat.posZ, target.y);
        if (xDist > 1.0F) {
            if (boat.posX < target.x)
                boat.motionX += BOAT_SPEED;
            else if (boat.posX > target.x)
                boat.motionX += -BOAT_SPEED;
        }/* else {
            boat.motionX = 0;
            xDist = 0;
        }*/
        
        if (zDist > 1.0F) {
            if (boat.posZ < target.y)
                boat.motionZ += BOAT_SPEED;
            else if (boat.posZ > target.y)
                boat.motionZ += -BOAT_SPEED;
        }/* else {
            boat.motionZ = 0;
            zDist = 0;
        }*/
        
        if (zDist <= 1F && xDist <= 1F) {
            target = null;
        }
    }
    
    private void setTargetLocation(Vector2 target) {
        this.target = target;
    }
    
}
