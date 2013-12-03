package dgrxf.watercraft.client.gui.components;

import java.util.List;

import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.client.gui.components.GuiArrowButton.GuiDirection;
import dgrxf.watercraft.util.Rectangle;
import dgrxf.watercraft.util.Vector2;

/**
 * 
 * GuiDropdown
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class GuiDropdown extends GuiComponent {
    private static final int BUTTON_SIZE = 12;
    private static final Vector2 MENU_SIZE = new Vector2(48, 12);
    
    private GuiArrowButton click;
    
    private GuiScrollList list;
    
    boolean drawList = false;
    
    public GuiDropdown(int x, int y, List options) {
        super(x, y, (int)MENU_SIZE.x + BUTTON_SIZE, BUTTON_SIZE);
        click = new GuiArrowButton(0, x+(int)MENU_SIZE.x, y, GuiDirection.DOWN);
        list = new GuiScrollList(19, 65, 84, 55, 11) {

			@Override
			public Vector2 getScrollItemBackgroundPos() {
				return new Vector2(24,36);
			}

			@Override
			public Vector2 getScrollItemHoverBackgroundPos() {
				return new Vector2(24, 60);
			}

			@Override
			public Vector2 getScrollItemSelectedBackgroundPos() {
				return new Vector2(24, 48);
			}

			@Override
			public Rectangle getScrollBarArea() {
				return new Rectangle(click.getX(), click.getY() + click.getHeight(), 8, 44);
			}

			@Override
			public Vector2 getScrollBarSize() {
				return new Vector2(8, 9);
			}

			@Override
			public Vector2 getScrollBarTexturePos() {
				return new Vector2(72, 48);
			}

			@Override
			public Vector2 getScrollBarTextureDisabledPos() {
				return new Vector2(80, 48);
			}
        	
        };
    }
    
    @Override
    public void drawBackground(GuiBase gui, int x, int y) {
        int srcY = 0;
        if (inRect(gui, x, y)) {
            srcY = 14;
        }
        gui.drawTexturedModalRect(gui.getLeft() + getX(), gui.getTop() + getY(), 24, 36, (int)MENU_SIZE.x, (int)MENU_SIZE.y);
        click.drawBackground(gui, x, y);
        if(drawList){
        	list.drawBackground(gui, x, y);
        }
    }
    
    @Override
    public void drawForeground(GuiBase gui, int x, int y) {
    }
    
    @Override
    public void mouseClick(GuiBase gui, int x, int y, int button) {
        if(click.mouseClick(gui, x, y)){
        	drawList = !drawList;
        	click.setArrowDirection(click.dir.getOpposite());
        }
        if (inRect(gui, x, y)) {
        	
        } else {
        	
        }
    }
    
}
