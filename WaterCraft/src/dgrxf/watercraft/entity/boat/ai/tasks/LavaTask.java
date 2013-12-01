package dgrxf.watercraft.entity.boat.ai.tasks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.interfaces.ICustomBoatTexture;
import dgrxf.watercraft.lib.RenderInfo;

/**
 * 
 * LavaTask
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class LavaTask extends BoatAITaskBase implements ICustomBoatTexture{

	public LavaTask(AbstractBaseBoat boat, Float priority, Object... args) {
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
	
	@Override
	public boolean breakBoat() {
	    return false;
	}

	@Override
	public ResourceLocation getCustomTexture() {
		return RenderInfo.IRON_BOAT_TEXTURE_LOCATION;
	}
	
}
