package dgrxf.watercraft.client.gui.interfaces;

import java.util.Arrays;

public class GuiRectangle {
	
	private int x;
	private int y;
	private int w;
	private int h;

	public GuiRectangle(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public boolean inRect(GuiBase gui, int mouseX, int mouseY) {
		mouseX -= gui.getLeft();
		mouseY -= gui.getTop();
		
		return x <= mouseX && mouseX <= x + w && y <= mouseY && mouseY <= y + h;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void draw(GuiBase gui, int srcX, int srcY) {
		gui.drawTexturedModalRect(gui.getLeft() + x, gui.getTop() + y, srcX, srcY, w, h);
	}
	
	public void drawString(GuiBase gui, int mouseX, int mouseY, String str) {
		if(inRect(gui, mouseX, mouseY)) {
			gui.drawHoverString(Arrays.asList(str.split("\n")), mouseX - gui.getLeft(), mouseY - gui.getTop());
		}
	}
}
