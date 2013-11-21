package dgrxf.watercraft.entity.boat;

import net.minecraft.world.World;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.DumbTask;
import dgrxf.watercraft.entity.boat.ai.tasks.VanillaTask;

public class DumbBoat extends AbstractBaseBoat {
	
    public DumbBoat(World par1World) {
        super(par1World);
    }
    
    public DumbBoat(World world, double x, double y, double z) {
        super(world, x, y, z);
        
    }
    
    @Override
    protected void updateBoatAI(BoatAITaskList list) {
        list.addTask(new DumbTask(this, 1f));
        list.addTask(new VanillaTask(this, 0f));
    }
}
