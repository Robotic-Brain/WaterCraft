package dgrxf.watercraft.entity.boat.ai.tasks;

import net.minecraft.block.Block;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;

public class IceTask extends BoatAITaskBase {
    
    public IceTask(AbstractBaseBoat boat, Float priority, Object... args) {
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
    
    @Override
    public boolean breakBoat() {
        for (int int1 = -2; int1 < 2; int1++) {
            for (int int2 = -2; int2 < 1; int2++) {
                for (int int3 = -2; int3 < 2; int3++) {
                    if (boat.worldObj.getBlockId(((int) boat.posX + int1), ((int) boat.posY + int2), ((int) boat.posZ + int3)) != Block.ice.blockID
                            && (boat.worldObj.getBlockId(((int) boat.posX + int1), ((int) boat.posY + int2), ((int) boat.posZ + int3)) != Block.waterMoving.blockID)
                            && (boat.worldObj.getBlockId(((int) boat.posX + int1), ((int) boat.posY + int2), ((int) boat.posZ + int3)) != Block.waterStill.blockID)
                            && (boat.worldObj.getBlockId(((int) boat.posX + int1), ((int) boat.posY + int2), ((int) boat.posZ + int3)) != 0))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
