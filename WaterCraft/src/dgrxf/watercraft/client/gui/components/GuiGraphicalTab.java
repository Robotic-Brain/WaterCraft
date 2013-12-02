package dgrxf.watercraft.client.gui.components;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.client.gui.interfaces.IGuiTab;
import dgrxf.watercraft.client.gui.interfaces.IGuiTabContainer;

/**
 * GraphicalGuiTab
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 */
public abstract class GuiGraphicalTab extends GuiGraphicsRectangle implements IGuiTab{

	private String name;
	private int id;
	private boolean isActive = false;
	
	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param inv
	 * @param exemptAreas
	 */
	public GuiGraphicalTab(String name, int id, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.name = name;
		this.id = id;
	}

    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public void drawTabTitle(IGuiTabContainer gui) {}
    
    public void drawBackground(GuiBase gui, int x, int y){
    	
    }
    
    public abstract void drawForeground(GuiBase gui, int x, int y);
    
    public abstract void actionPerformed(GuiButton button);
    
    public abstract void mouseClick(GuiBase gui, int x, int y, int button);
    
    public abstract void mouseMoveClick(GuiBase gui, int x, int y, int button, long timeSinceClicked);
    
    public abstract void mouseReleased(GuiBase gui, int x, int y, int button);
	
    public abstract ResourceLocation getGuiTabIcon();
    
    public boolean isActive(){
    	return isActive;
    }
    
	public void setTabActive(boolean active){
		this.isActive = active;
	}
	
}
