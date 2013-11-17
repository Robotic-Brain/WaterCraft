//TODO: Redo this class

/*package dgrxf.watercraft.entity;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.util.Vector2;

public class WCEntitySmartBoat extends AbstractBaseBoat {
    
    private int              iterator = 0;
    private ForgeDirection[] list;
    
    public WCEntitySmartBoat(World world) {
        super(world);
    }
    
    public WCEntitySmartBoat(World world, double x, double y, double z) {
        super(world, x, y, z);
    }
    
    @Override
    public void setTargetLocation(Vector2 target) {
        this.target = target;
    }
    
    public void setList(ForgeDirection[] list) {
        this.list = list;
    }
    
    public void iterateList() {
        System.out.println(iterator);
        iterator++;
        if (list != null) {
            if (iterator > list.length - 1) {
                iterator = 0;
                /*ForgeDirection[] reverseList = new ForgeDirection[list.length];
                int j = 0;
                for(int i = list.length - 1; i > 0; --i){
                	reverseList[j] = list[i];
                	++j;
                }
                list = reverseList;
            }
        } else {
            //target = null;
        }
    }
    
    @Override
    public void moveToTarget() {
        if (target == null || worldObj.isRemote) {
            return;
        }
        motionX = target.x;
        motionZ = target.y;
        //System.out.println(target.x + "," + target.y);
    }
    
    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        
        if (list != null) {
            //super.setTargetLocation(list[iterator]);
            setTargetLocation(new Vector2((float) list[iterator].offsetX / 10, (float) list[iterator].offsetZ / 10));
        }
        
    }
}
*/