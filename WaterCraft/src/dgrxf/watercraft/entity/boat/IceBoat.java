package dgrxf.watercraft.entity.boat;

import net.minecraft.world.World;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.IceTask;
import dgrxf.watercraft.entity.boat.ai.tasks.VanillaTask;

public class IceBoat extends AbstractBaseBoat {
    
    public IceBoat(World par1World) {
        super(par1World);
    }
    
    public IceBoat(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }
    
    @Override
    protected void updateBoatAI(BoatAITaskList list) {
        list.addTask(new VanillaTask(this, 1f));
        list.addTask(new IceTask(this, 1.1f));
    }
}
