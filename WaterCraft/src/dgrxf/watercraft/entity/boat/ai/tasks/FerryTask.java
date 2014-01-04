package dgrxf.watercraft.entity.boat.ai.tasks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;

public class FerryTask extends BoatAITaskBase {
	
	public FerryTask(AbstractBaseBoat boat, Float priority, Object[] obj) {
		super(boat, priority);
	}
	
	@Override
	public void applyEntityCollision(Entity par1Entity) {
		super.applyEntityCollision(par1Entity);
		if (!boat.worldObj.isRemote)
        {
            if (par1Entity != boat.riddenByEntity)
            {
                if (par1Entity instanceof EntityLivingBase && !(par1Entity instanceof EntityPlayer) && boat.riddenByEntity == null && par1Entity.ridingEntity == null)
                {
                    par1Entity.mountEntity(boat);
                }
            }
        }
	}
}
