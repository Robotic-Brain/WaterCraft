package dgrxf.watercraft.entity.boat.ai.tasks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;

public class LavaTask extends BoatAITaskBase {

	public LavaTask(AbstractBaseBoat boat, float priority) {
		super(boat, priority);
	}

	@Override
	public void preOnUpdate() {
        if (boat.riddenByEntity != null) {
            if (boat.riddenByEntity instanceof EntityLivingBase) {
                ((EntityLivingBase) boat.riddenByEntity).addPotionEffect(new PotionEffect(12, 10, 0, true));
                boat.riddenByEntity.extinguish();
            }
        }
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
	    if (source.isFireDamage() == true) {
            return false;
        } else {
            return true;
        }
	}
	
}
