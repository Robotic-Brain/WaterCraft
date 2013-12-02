package dgrxf.watercraft.client.gui.components;

import net.minecraft.client.gui.GuiButton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.client.gui.interfaces.IGuiTab;
import dgrxf.watercraft.client.gui.interfaces.IGuiTabContainer;

/**
 * 
 * GuiTab
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
@SideOnly(Side.CLIENT)
public abstract class GuiTab extends GuiRectangle implements IGuiTab{
    
    private String name;
    private int    id;
	private boolean isActive = false;
    
    public GuiTab(String name, int id, int x, int y, int w, int h) {
        super(x, y, w, h);
        this.id = id;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public void drawTabTitle(IGuiTabContainer gui) {
    	gui.getFontRenderer().drawString(name, getX() + 2, getY() + 2, 0xFFFFFF);
    }
    
    public abstract void drawBackground(GuiBase gui, int x, int y);
    
    public abstract void drawForeground(GuiBase gui, int x, int y);
    
    public abstract void actionPerformed(GuiButton button);
    
    public abstract void mouseClick(GuiBase gui, int x, int y, int button);
    
    public abstract void mouseMoveClick(GuiBase gui, int x, int y, int button, long timeSinceClicked);
    
    public abstract void mouseReleased(GuiBase gui, int x, int y, int button);
	
    public boolean isActive(){
    	return isActive;
    }
    
	public void setTabActive(boolean active){
		this.isActive = active;
	}
    
}
