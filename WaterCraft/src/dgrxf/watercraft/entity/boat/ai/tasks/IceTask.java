package dgrxf.watercraft.entity.boat.ai.tasks;

import net.minecraft.block.Block;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;

public class IceTask extends BoatAITaskBase {
    
    public IceTask(AbstractBaseBoat boat, float priority) {
        super(boat, priority);
    }
    
    @Override
    public void preOnUpdate() {
        if (!boat.worldObj.isRemote) {
            int myX = (int) boat.posX;
            int myY = (int) boat.posY;
            int myZ = (int) boat.posZ;
            
            for (int dx = -1; dx <= 1; ++dx) {
                for (int dy = -1; dy <= 1; ++dy) {
                    for (int dz = -1; dz <= 1; ++dz) {
                        if (Block.ice.blockID == boat.worldObj.getBlockId(myX + dx, myY + dy, myZ + dz)) {
                            boat.worldObj.setBlock(myX + dx, myY + dy, myZ + dz, Block.waterStill.blockID);
                        }
                    }
                }
            }
        }
    }
}
