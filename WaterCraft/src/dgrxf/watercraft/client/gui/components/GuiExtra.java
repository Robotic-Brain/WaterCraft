package dgrxf.watercraft.client.gui.components;

import dgrxf.watercraft.client.gui.GuiBase;

/**
 * 
 * GuiExtra
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public abstract class GuiExtra extends GuiRectangle {
    
    public GuiExtra(int x, int y, int w, int h) {
        super(x, y, w, h);
    }
    
    public abstract void drawBackground(GuiBase gui, int x, int y);
    
    public abstract void drawForeground(GuiBase gui, int x, int y);
    
    public void mouseClick(GuiBase gui, int x, int y, int button) {
    }
    
    public void mouseMoveClick(GuiBase gui, int x, int y, int button, long timeSinceClicked) {
    }
    
    public void mouseReleased(GuiBase gui, int x, int y, int button) {
    }
    
}
