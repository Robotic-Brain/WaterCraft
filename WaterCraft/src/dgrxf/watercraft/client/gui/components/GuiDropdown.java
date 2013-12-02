package dgrxf.watercraft.client.gui.components;

import java.util.List;

import dgrxf.watercraft.client.gui.GuiBase;

/**
 * 
 * GuiDropdown
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class GuiDropdown extends GuiComponent {
    
    public GuiDropdown(int x, int y, List options) {
        super(x, y, 58, 14);
    }
    
    @Override
    public void drawBackground(GuiBase gui, int x, int y) {
        int srcY = 0;
        if (inRect(gui, x, y)) {
            srcY = 14;
        }
        gui.drawTexturedModalRect(gui.getLeft() + getX() - 1, gui.getTop() + getY() - 1, 196, 28, 59, 60);
        draw(gui, 196, srcY);
        
    }
    
    @Override
    public void drawForeground(GuiBase gui, int x, int y) {
        //gui.getFontRenderer().drawString(dir, getX() + 4, getY() + 3, 0);
    }
    
    @Override
    public void mouseClick(GuiBase gui, int x, int y, int button) {
        if (inRect(gui, x, y)) {
        	
        } else {
        	
        }
    }
    
}
