package dgrxf.watercraft.entity.boat.ai;

import net.minecraft.tileentity.TileEntity;
import dgrxf.watercraft.entity.boat.DumbBoat;
import dgrxf.watercraft.tileentity.buoy.WCBouyLogic;
import dgrxf.watercraft.util.LogHelper;
import dgrxf.watercraft.util.MathHelper;
import dgrxf.watercraft.util.Vector2;

public class BoatAIDumb extends BoatEntityAIBase {
    
    private DumbBoat boat;
    private Vector2 target;
    
    public BoatAIDumb(DumbBoat boat) {
        this.boat = boat;
    }

    @Override
    public boolean shouldExecute() {
        return true;
    }
    
    public void updateTask() {
        getNewTargetFromBuoy();
        moveToTarget();
    }
    
    private void getNewTargetFromBuoy() {
        int myX = (int) boat.posX;
        int myY = (int) boat.posY;
        int myZ = (int) boat.posZ;
        
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                for (int dz = -1; dz <= 1; ++dz) {
                    TileEntity te = boat.worldObj.getBlockTileEntity(myX + dx, myY + dy, myZ + dz);
                    if (te instanceof WCBouyLogic) {
                        WCBouyLogic buoy = (WCBouyLogic) te;
                        
                        if (buoy.hasNextBuoy(buoy.getBlockDirection())) {
                            setTargetLocation(buoy.getNextBuoyCoords(buoy.getBlockDirection()).xz());
                            LogHelper.debug("Traget set");
                        }
                    }
                }
            }
        }
    }
    
    private void moveToTarget() {
        float xDist, zDist;
        if (target == null || boat.worldObj.isRemote) {
            return;
        }
        xDist = MathHelper.calculatePointDistance((float) boat.posX, target.x);
        zDist = MathHelper.calculatePointDistance((float) boat.posZ, target.y);
        if (xDist > 1.0F) {
            if (boat.posX < target.x)
                boat.motionX += 0.1;
            else if (boat.posX > target.x)
                boat.motionX += -0.1;
        }/* else {
            boat.motionX = 0;
            xDist = 0;
        }*/
        
        if (zDist > 1.0F) {
            if (boat.posZ < target.y)
                boat.motionZ += 0.1;
            else if (boat.posZ > target.y)
                boat.motionZ += -0.1;
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
