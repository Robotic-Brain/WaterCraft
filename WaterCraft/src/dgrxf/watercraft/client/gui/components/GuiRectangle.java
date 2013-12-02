package dgrxf.watercraft.client.gui.components;

import java.util.Arrays;

import dgrxf.watercraft.client.gui.GuiBase;

/**
 * 
 * GuiRectangle
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public abstract class GuiRectangle extends GuiComponent{
    
    public GuiRectangle(int x, int y, int w, int h) {
    	super(x, y, w, h);
    }

	/* (non-Javadoc)
	 * @see dgrxf.watercraft.client.gui.components.GuiComponent#drawBackground(dgrxf.watercraft.client.gui.GuiBase, int, int)
	 */
	@Override
	public abstract void drawBackground(GuiBase gui, int x, int y);

	/* (non-Javadoc)
	 * @see dgrxf.watercraft.client.gui.components.GuiComponent#drawForeground(dgrxf.watercraft.client.gui.GuiBase, int, int)
	 */
	@Override
	public abstract void drawForeground(GuiBase gui, int x, int y);
}
