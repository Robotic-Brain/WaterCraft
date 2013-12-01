package dgrxf.watercraft.client.gui.crane;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import dgrxf.watercraft.client.gui.GuiExtra;
import dgrxf.watercraft.client.gui.GuiRectangle;
import dgrxf.watercraft.client.gui.GuiScrollList;
import dgrxf.watercraft.util.Vector2;

/**
 * 
 * GuiTabRoute
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class GuiTabRoute extends GuiTab {
    
    private ArrayList<String> directions     = new ArrayList<String>() {
                                                 {
                                                     add("SOUTH");
                                                     add("EAST");
                                                     add("WEST");
                                                 }
                                             };
    private String            selectedString = "NORTH";
    private GuiRectangle      dropDownButton = new GuiRectangle(75, 40, 11, 14);
    private GuiRectangle      dropDownFirst  = new GuiRectangle(18, 40, 57, 14);
    private GuiScrollList     scrollList;
    private boolean           itemSelected;
    public boolean            isActive;
    private boolean           drawMenu;
    
    private GuiExtra[]        menu           = new GuiExtra[4];
    
    public GuiTabRoute(String name, int id) {
        super(name, id);
        
        scrollList = new GuiScrollList(19, 65, 84, 55, 11) {
            
            @Override
            public GuiRectangle getScrollBarArea() {
                return new GuiRectangle(105, 65, 9, 55);
            }
            
            @Override
            public boolean isActive() {
                return isActive;
            }
            
            @Override
            public boolean isFocused() {
                return !drawMenu;
            }
            
            @Override
            public void onClick(int id) {
                int i = scrollList.getSize();
                if (i > 0 && scrollList.getSelectedIndex() != -1) {
                    itemSelected = true;
                }
            }
            
            @Override
            public Vector2 getScrollItemBackgroundPos() {
                return new Vector2(172, 223);
            }
            
            @Override
            public Vector2 getScrollItemHoverBackgroundPos() {
                return new Vector2(172, 234);
            }
            
            @Override
            public Vector2 getScrollItemSelectedBackgroundPos() {
                return new Vector2(172, 245);
            }
            
            @Override
            public Vector2 getScrollBarSize() {
                return new Vector2(9, 8);
            }
            
            @Override
            public Vector2 getScrollBarTexturePos() {
                return new Vector2(157, 218);
            }
            
            @Override
            public Vector2 getScrollBarTextureDisabledPos() {
                return new Vector2(157, 226);
            }
        };
        menu[0] = new GuiDropdown(18, 40, selectedString, this);
        for (int i = 0; i < menu.length - 1; i++) {
            menu[i + 1] = new GuiDropdown(18, 40 + 15 * (i + 1), directions.get(i), this);
        }
    }
    
    @Override
    public void drawBackground(CraneGUI gui, int x, int y) {
        scrollList.drawBackground(gui, x, y);
        
        if (itemSelected) {
            gui.removeButton.enabled = true;
        } else if (gui.removeButton.enabled == true) {
            gui.removeButton.enabled = false;
        } else if (!itemSelected) {
            int i = scrollList.getSize();
            if (i > 0 && scrollList.getSelectedIndex() != -1) {
                itemSelected = true;
            }
        }
        
        if (drawMenu) {
            for (GuiExtra extra : menu) {
                extra.drawBackground(gui, x, y);
            }
        }
        
        if (drawMenu) {
            dropDownButton.draw(gui, 135, 218);
        }
        
        if (dropDownButton.inRect(gui, x, y) || dropDownFirst.inRect(gui, x, y)) {
            dropDownButton.draw(gui, 146, 218);
            dropDownFirst.draw(gui, 196, 14);
        }
        
    }
    
    @Override
    public void drawForeground(CraneGUI gui, int x, int y) {
        
        if (drawMenu) {
            for (GuiExtra extra : menu) {
                extra.drawForeground(gui, x, y);
            }
        } else {
            scrollList.drawForeground(gui, x, y);
            gui.getFontRenderer().drawString(selectedString, 22, 43, 0);
        }
        dropDownButton.drawHoverString(gui, x, y, "Click to see all options");
    }
    
    @Override
    public void mouseClick(CraneGUI gui, int x, int y, int button) {
        if (drawMenu) {
            if (dropDownButton.inRect(gui, x, y) || dropDownFirst.inRect(gui, x, y)) {
                drawMenu = false;
            }
            for (GuiExtra extra : menu) {
                extra.mouseClick(gui, x, y, button);
            }
        } else {
            scrollList.mouseClick(gui, x, y, button);
            if (dropDownButton.inRect(gui, x, y) || dropDownFirst.inRect(gui, x, y)) {
                drawMenu = true;
            }
        }
    }
    
    @Override
    public void mouseMoveClick(CraneGUI gui, int x, int y, int button, long timeSinceClicked) {
        scrollList.mouseMoveClick(gui, x, y, button, timeSinceClicked);
        if (drawMenu) {
            for (GuiExtra extra : menu) {
                extra.mouseMoveClick(gui, x, y, button, timeSinceClicked);
            }
        }
    }
    
    @Override
    public void mouseReleased(CraneGUI gui, int x, int y, int button) {
        scrollList.mouseReleased(gui, x, y, button);
        if (drawMenu) {
            for (GuiExtra extra : menu) {
                extra.mouseReleased(gui, x, y, button);
            }
        }
    }
    
    @Override
    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                scrollList.add(selectedString);
                break;
            case 1:
                scrollList.remove(scrollList.getSelectedIndex());
                itemSelected = false;
                break;
            default:
                break;
        }
    }
    
    public void setCurrentDir(String dir) {
        stopDrawingDropDown();
        int i = 0;
        for (String s : directions) {
            if (s.equals(dir)) {
                directions.remove(i);
                directions.add(i, selectedString);
                break;
            }
            i++;
        }
        selectedString = dir;
        menu = new GuiExtra[4];
        menu[0] = new GuiDropdown(18, 40, selectedString, this);
        for (int j = 0; j < menu.length - 1; j++) {
            menu[j + 1] = new GuiDropdown(18, 40 + 15 * (j + 1), directions.get(j), this);
        }
    }
    
    public void stopDrawingDropDown() {
        drawMenu = false;
    }
    
    public ArrayList<String> getTargetDirections() {
        return scrollList.getList();
    }
    
}
