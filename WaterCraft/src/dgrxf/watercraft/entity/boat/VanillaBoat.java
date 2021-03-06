package dgrxf.watercraft.entity.boat;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.RopeTask;
import dgrxf.watercraft.entity.boat.ai.tasks.VanillaTask;

/**
 * 
 * VanillaBoat
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class VanillaBoat extends AbstractBaseBoat {
    
    public VanillaBoat(World par1World) {
        super(par1World);
    }
    
    public VanillaBoat(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
    
    @Override
    protected void updateBoatAI(BoatAITaskList list) {
    	list.addTask(new RopeTask(this, 2f));
        list.addTask(new VanillaTask(this, 1f));
    }
    
}
