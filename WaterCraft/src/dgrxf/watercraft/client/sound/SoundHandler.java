package dgrxf.watercraft.client.sound;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.util.LogHelper;

/**
 * (Original from VSWE Courses)
 * http://courses.vswe.se/
 */
@SideOnly(Side.CLIENT)
public class SoundHandler {
    
    public SoundHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @ForgeSubscribe
    public void onSoundsLoad(SoundLoadEvent event) {
        for (Sounds sound : Sounds.values()) {
            addSound(event, sound);
        }
    }
    
    private void addSound(SoundLoadEvent event, Sounds sound) {
        try {
            event.manager.addSound(Sounds.SOUNDS_LOCATION + ":" + sound.getName() + ".ogg");
        } catch (Exception e) {
            LogHelper.warning("Failed loading sound file: " + sound.getName());
        }
    }
    
}
