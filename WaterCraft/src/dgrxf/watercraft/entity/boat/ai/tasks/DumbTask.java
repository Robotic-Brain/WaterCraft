package dgrxf.watercraft.entity.boat.ai.tasks;

import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;
import dgrxf.watercraft.util.Vector2;

/**
 * 
 * DumbTask
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 */
public class DumbTask extends BoatAITaskBase {
    
    private Vector2          target, lastLocation;
    private AbstractBaseBoat boat;
    private ForgeDirection   direction;
    
    public DumbTask(AbstractBaseBoat boat, Float priority, Object... args) {
        super(boat, priority);
        this.boat = boat;
    }
    
    @Override
    public void updateMotion() {
        moveToTarget();
        
        if (target != null && boat.worldObj.isAirBlock((int) target.x, (int) boat.posY, (int) target.y)) {
            setTargetLocation(lastLocation);
            lastLocation = null;
        }
    }
    
    @Override
    public void buoyFound(WCBouyLogic buoy) {
        if (buoy.hasNextBuoy(buoy.getBlockDirection())) {
            setTargetLocation(buoy.getNextBuoyCoords(buoy.getBlockDirection()).xz());
            direction = buoy.getBlockDirection();
            lastLocation = buoy.getBuoyCoords().xz();
        }
    }
    
    private void moveToTarget() {
        if (target == null || boat.worldObj.isRemote) {
            return;
        }
        
        Vector2 boatPos = new Vector2(boat.posX, boat.posZ);
        Vector2 distance = target.sub(boatPos);
        Vector2 newMotion = new Vector2();
        
        newMotion = distance.normalize().scalarMult(0.3);
        
        boat.motionX = newMotion.x;
        boat.motionZ = newMotion.y;
    }
    
    private void setTargetLocation(Vector2 target) {
        this.target = target;
    }
}
