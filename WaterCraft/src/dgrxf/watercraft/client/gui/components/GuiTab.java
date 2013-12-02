package dgrxf.watercraft.client.gui.components;

import net.minecraft.client.gui.GuiButton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.client.gui.crane.CraneGUI;
import dgrxf.watercraft.client.gui.interfaces.IGuiTabContainer;

/**
 * 
 * GuiTab
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
@SideOnly(Side.CLIENT)
public abstract class GuiTab extends GuiRectangle {
    
    private String name;
    private int    id;
    
    public GuiTab(String name, int id) {
        super(8 + id * 45, 18, 45, 12);
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
    
}
