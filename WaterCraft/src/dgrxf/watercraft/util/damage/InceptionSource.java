package dgrxf.watercraft.util.damage;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;

/**
 * 
 * InceptionSource
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class InceptionSource extends DamageSource {
    
    public InceptionSource() {
        super("inception");
    }
    
    @Override
    public boolean isExplosion() {
        return false;
    }
    
    @Override
    public boolean isProjectile() {
        return false;
    }
    
    @Override
    public boolean isUnblockable() {
        return true;
    }
    
    @Override
    public boolean canHarmInCreative() {
        return true;
    }
    
    @Override
    public ChatMessageComponent getDeathMessage(EntityLivingBase par1EntityLivingBase) {
        //return (new ChatMessageComponent()).addFormatted("test");
        return super.getDeathMessage(par1EntityLivingBase);
    }
    
}
