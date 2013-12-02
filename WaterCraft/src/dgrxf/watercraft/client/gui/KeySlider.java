package dgrxf.watercraft.client.gui;

import dgrxf.watercraft.client.gui.components.GuiRectangle;

/**
 * 
 * KeySlider
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class KeySlider extends GuiRectangle {
	
	public boolean clicked;

	public KeySlider(int x, int y, int w, int h) {
		super(x, y, w, h);
		clicked = false;
	}

	@Override
	public void drawBackground(GuiBase gui, int x, int y) {}
	
	@Override
	public void drawForeground(GuiBase gui, int x, int y) {}
	
	
}
