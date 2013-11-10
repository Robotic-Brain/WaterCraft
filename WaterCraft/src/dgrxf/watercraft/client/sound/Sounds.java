package dgrxf.watercraft.client.sound;

import dgrxf.watercraft.lib.ModInfo;
import net.minecraft.client.Minecraft;

/**
 * (Original from VSWE Courses)
 */

public enum Sounds {
	TOOLBOX_OPENING("Toolbox_opening"),
	TOOLBOX_CLOSING("Toolbox_closing");
	
	public static final String SOUNDS_LOCATION = ModInfo.MODID;
	private String name;

	Sounds(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void play(double x, double y, double z, float volume, float pitch) {
		Minecraft.getMinecraft().sndManager.playSound(SOUNDS_LOCATION + ":" + name, (float)x, (float)y, (float)z, volume, pitch);
	}
}

