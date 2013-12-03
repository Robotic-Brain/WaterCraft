package dgrxf.watercraft.client.gui.components;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.client.gui.components.GuiArrowButton.GuiDirection;
import dgrxf.watercraft.lib.ModInfo;
import dgrxf.watercraft.util.Rectangle;
import dgrxf.watercraft.util.Vector2;

/**
 * 
 * GuiDropdown
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class GuiDropDownScrollList extends GuiComponent {
    private static final int BUTTON_SIZE = 12;
    private static final Vector2 MENU_SIZE = new Vector2(48, 12);
    
    private static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID, "textures/gui/guiComponents.png"); 
    
    private GuiArrowButton click;
    
    private GuiScrollList list;
    
    private GuiRectangle scrollBar;
    
    boolean drawList = false;
    
    public GuiDropDownScrollList(int x, int y, ArrayList<String> options) {
        super(x, y, (int)MENU_SIZE.x + BUTTON_SIZE, BUTTON_SIZE);
        click = new GuiArrowButton(0, x+(int)MENU_SIZE.x, y, GuiDirection.DOWN);
        list = new GuiScrollList(x, y+(int)MENU_SIZE.y, 48, 48, 12, options) {

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
				return new Rectangle(getX()+(int)MENU_SIZE.x + 2, getY()+1, 8, 46);
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
        scrollBar = new GuiRectangle(x+(int)MENU_SIZE.x, y + (int)MENU_SIZE.y, (int)MENU_SIZE.y, (int)MENU_SIZE.x) {
			
			@Override
			public void drawForeground(GuiBase gui, int x, int y) {}
			
			@Override
			public void drawBackground(GuiBase gui, int x, int y) {
				Minecraft.getMinecraft().renderEngine.bindTexture(texture);
				gui.drawTexturedModalRect(gui.getLeft() + getX(),gui.getTop() + getY(), 72, 0, getWidth(), getHeight());
			}
		};
    }
    
    @Override
    public void drawBackground(GuiBase gui, int x, int y) {
        int srcY = 0;
        if (inRect(gui, x, y)) {
            srcY = 14;
        }
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        gui.drawTexturedModalRect(gui.getLeft() + getX(), gui.getTop() + getY(), 24, 36, (int)MENU_SIZE.x, (int)MENU_SIZE.y);

        click.drawBackground(gui, x, y);
        if(drawList){
        	scrollBar.drawBackground(gui, x, y);
        	list.drawBackground(gui, x, y);
        }
    }
    
    @Override
    public void drawForeground(GuiBase gui, int x, int y) {

		int index = list.getSelectedIndex();
		ArrayList strings = list.getList();
		
		if(index != -1)
			gui.getFontRenderer().drawString(strings.get(index).toString(), getX() + 3, getY() + 2, 0xAAAAAA, false);
		
    	if(drawList){
    		list.drawForeground(gui, x, y);
    	}
    }
    @Override
    public void mouseMoveClick(GuiBase gui, int x, int y, int button,
    		long timeSinceClicked) {
    	list.mouseMoveClick(gui, x, y, button, timeSinceClicked);
    }
    
    @Override
    public void mouseReleased(GuiBase gui, int x, int y, int button) {
    	list.mouseReleased(gui, x, y, button);
    }
    
    @Override
    public void mouseClick(GuiBase gui, int x, int y, int button) {
        if(click.mouseClick(gui, x, y) || inRect(gui, x, y)){
        	drawList = !drawList;
        	click.setArrowDirection(click.dir.getOpposite());
        }
        if(drawList)
        	list.mouseClick(gui, x, y, button);
    }
    
}
