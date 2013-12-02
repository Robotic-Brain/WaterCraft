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
public class GuiRectangle {
    
    private int x;
    private int y;
    private int w;
    private int h;
    
    public GuiRectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public boolean inRect(GuiBase gui, int mouseX, int mouseY) {
        mouseX -= gui.getLeft();
        mouseY -= gui.getTop();
        
        return x <= mouseX && mouseX <= x + w && y <= mouseY && mouseY <= y + h;
    }
    
    public boolean inRect(GuiBase gui, int x, int y, int[] rect) {
        if (rect.length < 4) {
            return false;
        }
        x -= gui.getLeft();
        y -= gui.getTop();
        return (x >= rect[0]) && (x <= rect[0] + rect[2]) && (y >= rect[1]) && (y <= rect[1] + rect[3]);
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return w;
    }
    
    public int getHeight() {
        return h;
    }
    
    public void draw(GuiBase gui, int srcX, int srcY) {
        gui.drawTexturedModalRect(gui.getLeft() + x, gui.getTop() + y, srcX, srcY, w, h);
    }
    
    public void draw(GuiBase gui, int[] rect, int srcX, int srcY) {
        gui.drawTexturedModalRect(gui.getLeft() + rect[0], gui.getTop() + rect[1], srcX, srcY, rect[2], rect[3]);
    }
    
    public void drawHoverString(GuiBase gui, int mouseX, int mouseY, String str) {
        if (inRect(gui, mouseX, mouseY)) {
            gui.drawHoverString(Arrays.asList(str.split("\n")), mouseX - gui.getLeft(), mouseY - gui.getTop());
        }
    }
}
