package dgrxf.watercraft.entity.boat;

import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.LavaTask;
import dgrxf.watercraft.entity.boat.ai.tasks.VanillaTask;
import dgrxf.watercraft.enumeration.BoatType;
import dgrxf.watercraft.interfaces.ICustomBoatTexture;
import dgrxf.watercraft.lib.RenderInfo;

public class LavaBoat extends AbstractBaseBoat implements ICustomBoatTexture{
    
    public LavaBoat(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
    }
    
    public LavaBoat(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }
    
    @Override
    protected void updateBoatAI(BoatAITaskList list) {
    	list.addTask(new VanillaTask(this, 0.0F));
    	list.addTask(new LavaTask(this, 1.0F));
    }

	@Override
	public ResourceLocation getCustomTexture() {
		return RenderInfo.IRON_BOAT_TEXTURE_LOCATION;
	}
	
	@Override
    public BoatType getBoatType() {
    	return BoatType.LavaBoat;
    }
}
