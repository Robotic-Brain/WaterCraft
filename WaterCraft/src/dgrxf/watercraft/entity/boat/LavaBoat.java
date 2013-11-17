package dgrxf.watercraft.entity.boat;

import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.LavaTask;
import dgrxf.watercraft.entity.boat.ai.tasks.VanillaTask;

public class LavaBoat extends AbstractBaseBoat {
    
    public LavaBoat(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
    }
    
    public LavaBoat(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource src, float par2) {
        if (src.isFireDamage() == true) {
            return false;
        } else {
            return super.attackEntityFrom(src, par2);
        }
    }
    
    @Override
    protected void setBoatAI(BoatAITaskList list) {
    	list.addTask(new VanillaTask(this, 0.0F));
    	list.addTask(new LavaTask(this, 1.0F));
    }
}
