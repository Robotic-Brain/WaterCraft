package dgrxf.watercraft.client.gui;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

/**
 * 
 * GuiBase
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public abstract class GuiBase extends GuiContainer {
    
    public GuiBase(Container container) {
        super(container);
    }
    
    public int getLeft() {
        return guiLeft;
    }
    
    public int getTop() {
        return guiTop;
    }
    
    public void drawHoverString(List lst, int x, int y) {
        drawHoveringText(lst, x, y, fontRenderer);
    }
    
    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }
    
}
