package dgrxf.watercraft.entity.boat.ai.tasks;

import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;
import dgrxf.watercraft.util.LogHelper;
import dgrxf.watercraft.util.MathHelper;
import dgrxf.watercraft.util.Vector2;

public class DumbTask extends BoatAITaskBase {
    
    private Vector2 target;
    
    public DumbTask(AbstractBaseBoat boat, float priority) {
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
        //double xDist, zDist;
        if (target == null || boat.worldObj.isRemote) {
            return;
        }
        
        Vector2 boatPos = new Vector2(boat.posX, boat.posZ);
        Vector2 distance = target.sub(boatPos);
        Vector2 newMotion = new Vector2();
        
        if (distance.length2() > 25.0) {
            LogHelper.debug("Distance " + distance);
            //LogHelper.debug("Normalized " + distance.normalize());
            newMotion = distance.normalize().scalarMult(BOAT_SPEED*2.0);
        } else {
            newMotion = distance.normalize().scalarMult(BOAT_SPEED/2.0);
        }
        
        if (distance.dot(new Vector2(boat.motionX, boat.motionZ)) < 0) {
            newMotion = distance.normalize().scalarMult(1);
        }
        
        /*LogHelper.debug("Boat AI " + newMotion);
        LogHelper.debug("Target " + target);*/
        
        //newMotion.add(new Vector2(boat.motionX, boat.motionZ));
        
        boat.motionX += newMotion.x;
        boat.motionZ += newMotion.y;
        
        /*xDist = MathHelper.calculatePointDistance(boat.posX, target.x);
        zDist = MathHelper.calculatePointDistance(boat.posZ, target.y);
        if (xDist > 1.0F) {
            if (boat.posX < target.x) {
                boat.motionX += BOAT_SPEED;
            } else if (boat.posX > target.x) {
                boat.motionX += -BOAT_SPEED;
            }
        }
        
        if (zDist > 1.0F) {
            if (boat.posZ < target.y) {
                boat.motionZ += BOAT_SPEED;
            } else if (boat.posZ > target.y) {
                boat.motionZ += -BOAT_SPEED;
            }
        }
        
        if (zDist <= 1F && xDist <= 1F) {
            target = null;
        }*/
    }
    
    private void setTargetLocation(Vector2 target) {
        this.target = target;
    }
    
}
