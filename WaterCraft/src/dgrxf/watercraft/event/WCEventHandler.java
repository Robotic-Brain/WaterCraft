package dgrxf.watercraft.event;

import dgrxf.watercraft.client.sound.Sounds;
import dgrxf.watercraft.util.damage.InceptionSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class WCEventHandler {

	@ForgeSubscribe
	public void onEntityDeath(LivingDeathEvent event){
		if (event.source instanceof InceptionSource && event.entity != null) {
		    Sounds.INCEPTION.play(event.entity.posX, event.entity.posY, event.entity.posZ, 1.0f, 1.0f);
		}
	}
	
}
