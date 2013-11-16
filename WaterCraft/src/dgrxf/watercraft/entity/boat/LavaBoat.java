package dgrxf.watercraft.entity.boat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LavaBoat extends WCEntityBoatBase {

    public LavaBoat(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
    }
    
    public LavaBoat(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }
    
    @Override
    public void onUpdate() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.extinguish();
            if (this.riddenByEntity instanceof EntityLivingBase) {
                ((EntityLivingBase) this.riddenByEntity).addPotionEffect(new PotionEffect(12, 10, 0, true));
            }
        }
        super.onUpdate();
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource src, float par2) {
        if (src.isFireDamage() == true) {
            return false;
        } else {
            return super.attackEntityFrom(src, par2);
        }
    }
}
