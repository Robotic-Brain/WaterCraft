package dgrxf.watercraft.event;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import dgrxf.watercraft.client.sound.Sounds;
import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.util.damage.InceptionSource;

/**
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class WCEventHandler {
    
    @ForgeSubscribe
    public void onEntityDeath(LivingDeathEvent event) {
        if (event.source instanceof InceptionSource && event.entity != null && !event.entity.worldObj.isRemote) {
            PacketHandler.sendSoundPackage(Sounds.INCEPTION.ordinal(), (int)event.entity.posX, (int)event.entity.posY, (int)event.entity.posZ, event.entity.dimension);
        }
    }
    
}
