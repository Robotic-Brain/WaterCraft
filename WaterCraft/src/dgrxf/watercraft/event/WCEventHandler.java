package dgrxf.watercraft.event;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import dgrxf.watercraft.client.sound.Sounds;
import dgrxf.watercraft.util.damage.InceptionSource;

public class WCEventHandler {
    
    @ForgeSubscribe
    public void onEntityDeath(LivingDeathEvent event) {
        if (event.source instanceof InceptionSource && event.entity != null && event.entity.worldObj.isRemote) {
            //Sounds.INCEPTION.play(event.entity.posX, event.entity.posY, event.entity.posZ, 1.0f, 1.0f);
        }
    }
    
}
