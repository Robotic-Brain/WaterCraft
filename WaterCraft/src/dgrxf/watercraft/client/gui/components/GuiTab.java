package dgrxf.watercraft.client.gui.components;

import net.minecraft.client.gui.GuiButton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.client.gui.crane.CraneGUI;
import dgrxf.watercraft.client.gui.interfaces.IGuiTab;

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
    
    public void drawTabTitle(IGuiTab gui, int x, int y) {
        if (gui.getActiveTab() == this) {
            gui.getFontRenderer().drawString(name, getX() + 2, getY() + 2, 0xFFFFFF);
        } else {
            gui.getFontRenderer().drawString(name, getX() + 2, getY() + 2, 0);
        }
    }
    
    public abstract void drawBackground(CraneGUI gui, int x, int y);
    
    public abstract void drawForeground(CraneGUI gui, int x, int y);
    
    public void actionPerformed(GuiButton button) {
    }
    
    public void mouseClick(CraneGUI gui, int x, int y, int button) {
    }
    
    public void mouseMoveClick(CraneGUI gui, int x, int y, int button, long timeSinceClicked) {
    }
    
    public void mouseReleased(CraneGUI gui, int x, int y, int button) {
    }
    
}
