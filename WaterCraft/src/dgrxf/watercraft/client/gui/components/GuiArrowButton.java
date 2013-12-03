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
		
		private int[] opposites = {1, 0, 3, 2};
		
		public GuiDirection getOpposite(){
			return values()[opposites[ordinal()]];
		}
		
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
	public GuiArrowButton(int id, int x, int y, GuiDirection dir) {
		super(x, y, 12, 12);
		this.dir = dir;
		this.id = id;
	}
	
	private static final ResourceLocation sheet = new ResourceLocation(ModInfo.MODID, "textures/gui/guiComponents.png");

	public boolean mouseClick(GuiBase gui, int x, int y) {
		return inRect(gui, x, y);
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
			if(inRect(gui, x, y)){
				srcY = 24;
			}else{
				srcY = 12;
			}
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(this.sheet);
		gui.drawTexturedModalRect(getX()+gui.getLeft(), getY()+gui.getTop(), srcX, srcY, 12, 12);
	}

	
	
	@Override
	public void drawForeground(GuiBase gui, int x, int y) {
		
	}

	public void setArrowDirection(GuiDirection dir) {
		this.dir = dir;
	}
	
}
