package dgrxf.watercraft.entity.boat.ai;

import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.WCEntityBoatBase;
import dgrxf.watercraft.entity.boat.BoatAIBase;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;
import dgrxf.watercraft.util.LogHelper;
import dgrxf.watercraft.util.MathHelper;
import dgrxf.watercraft.util.Vector2;
import dgrxf.watercraft.util.Vector3;

public class BoatAIDumb extends BoatAIBase {
    
    private Vector2 target;
    
    public BoatAIDumb(WCEntityBoatBase boat) {
        super(boat);
    }
    
    @Override
    public void updateMotion() {
        float xDist, zDist;
        if (target == null || boat.worldObj.isRemote) {
            return;
        }
        xDist = MathHelper.calculatePointDistance((float) boat.posX, target.x);
        zDist = MathHelper.calculatePointDistance((float) boat.posZ, target.y);
        if (xDist > 1.0F) {
            if (boat.posX < target.x)
                boat.motionX = 0.1;
            else if (boat.posX > target.x)
                boat.motionX = -0.1;
        } else {
            boat.motionX = 0;
            xDist = 0;
        }
        
        if (zDist > 1.0F) {
            if (boat.posZ < target.y)
                boat.motionZ = 0.1;
            else if (boat.posZ > target.y)
                boat.motionZ = -0.1;
        } else {
            boat.motionZ = 0;
            zDist = 0;
        }
        
        if (zDist == 0 && xDist == 0) {
            target = null;
        }
    }
    
    @Override
    public void buoyFound(WCBouyLogic buoy) {
        setTargetLocation(buoy.getNextBuoyCoords(ForgeDirection.NORTH).xz());
        LogHelper.debug("Traget set");
    }
    
    public void setTargetLocation(Vector2 target) {
        this.target = target;
    }
}
