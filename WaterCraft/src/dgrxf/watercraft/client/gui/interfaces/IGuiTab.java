package dgrxf.watercraft.client.gui.interfaces;

import net.minecraft.client.gui.GuiButton;
import dgrxf.watercraft.client.gui.GuiBase;

/**
 * IGuiTab
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 */
public interface IGuiTab {

    
    public String getName();
    
    public int getId();
    
    public void drawTabTitle(IGuiTabContainer gui);
    
    public abstract void drawBackground(GuiBase gui, int x, int y);
    
    public abstract void drawForeground(GuiBase gui, int x, int y);
    
    public abstract void actionPerformed(GuiButton button);
    
    public abstract void mouseClick(GuiBase gui, int x, int y, int button);
    
    public abstract void mouseMoveClick(GuiBase gui, int x, int y, int button, long timeSinceClicked);
    
    public abstract void mouseReleased(GuiBase gui, int x, int y, int button);
	
    public boolean isActive();
    
	public void setTabActive(boolean active);
	
}
