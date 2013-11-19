package dgrxf.watercraft.entity.boat.ai.tasks;

import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;
import dgrxf.watercraft.util.LogHelper;
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
    
    private static final double BOAT_SPEED = 0.3;
    
    private void moveToTarget() {
        if (target == null || boat.worldObj.isRemote) {
            return;
        }
        
        Vector2 boatPos = new Vector2(boat.posX, boat.posZ);
        Vector2 distance = target.sub(boatPos);
        Vector2 newMotion = new Vector2();
        
        newMotion = distance.normalize().scalarMult(BOAT_SPEED);
        
        boat.motionX = newMotion.x;
        boat.motionZ = newMotion.y;
    }
    
    private void setTargetLocation(Vector2 target) {
        this.target = target;
    }
    
}
