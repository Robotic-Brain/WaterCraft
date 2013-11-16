package dgrxf.watercraft.entity.boat;

import net.minecraft.world.World;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.DumbTask;

public class DumbBoat extends WCEntityBoatBase{

    public DumbBoat(World par1World) {
        super(par1World);
    }
    
    public DumbBoat(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected void setBoatAI() {
        //this.ai = new BoatAIDumb(this);
        BoatAITaskList list = new BoatAITaskList(this);
        list.addTask(new DumbTask(this, 1f));
        
        this.ai = list;
    }
}
