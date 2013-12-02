package dgrxf.watercraft.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.lib.ModInfo;

/**
 * GuiArrowButton
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 */
public class GuiArrowButton extends GuiRectangle{

	public static enum GuiDirection{
		LEFT,
		RIGHT,
		UP,
		DOWN;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	GuiDirection dir;
	public int id;
	public boolean enabled=true;
	public GuiArrowButton(int id, int x, int y, int w, int h, GuiDirection dir) {
		super(x, y, w, h);
		this.dir = dir;
		this.id = id;
	}
	
	private static final ResourceLocation sheet = new ResourceLocation(ModInfo.MODID, "textures/gui/guiComponents.png");

	public boolean mouseClick(GuiBase gui, int x, int y) {
		return inRect(gui, x+gui.getLeft(), y+gui.getTop());
	}
	
	@Override
	public void drawBackground(GuiBase gui, int x, int y) {
		int srcX = 0;
		int srcY = 0;
		
		switch(dir){
			case LEFT:
				srcX = 24;
				break;
			case RIGHT:
				srcX = 36;
				break;
			case UP:
				srcX = 48;
				break;
			case DOWN:
				srcX = 60;
				break;
		}
		
		if(this.enabled){
			if(inRect(gui, x+gui.getLeft(), y+gui.getTop())){
				srcY = 24;
			}else{
				srcY = 12;
			}
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(this.sheet);
		gui.drawTexturedModalRect(getX(), getY(), srcX, srcY, 12, 12);
	}

	
	
	@Override
	public void drawForeground(GuiBase gui, int x, int y) {
		
	}

}
