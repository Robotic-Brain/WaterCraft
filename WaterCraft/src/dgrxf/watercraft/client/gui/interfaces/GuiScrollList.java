package dgrxf.watercraft.client.gui.interfaces;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import dgrxf.watercraft.util.Vector2;

public class GuiScrollList extends GuiExtra{

	private ArrayList<String> list;
	private int scrollPos;
	private int selectedIndex;
	private boolean isScrolling;
	
	public GuiScrollList(int x, int y, int w, int h, GuiBase gui) {
		super(x, y, w, h, gui);
		list = new ArrayList<String>();
	}
	
	public void clearList() {
		list.clear();
	}
	
	public void clearPos() {
		scrollPos = 0;
		selectedIndex = -1;
	}
	
	public boolean isActive() {
		return true;
	}
	
	public int getSize() {
		return list.size();
	}
	
	public void add(String str) {
		list.add(str);
	}
	
	public void remove(int index) {
		if (index < 0) return;
		list.remove(index);
		if (index == selectedIndex && list.size() > 0) selectedIndex = 0;
		else if(index == selectedIndex) selectedIndex = -1;
		if (list.size() < 6) scrollPos = 0;
	}
	
	public ArrayList<String> getList() {
		return list;
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}
	
	private void doScroll(int y) {
		scrollPos = (y - (scrollArea[1] - getY()) - 16);
		if (scrollPos < 0) scrollPos = 0;
		else if (scrollPos > 47) scrollPos = 47;
	}
	
	private int getScroll() {
		float total = list.size() * 11;
		float avalible = 55;
		float overflow = total - avalible;
		float lenght = scrollArea[3] - 8;
		return (int)(overflow * (scrollPos / lenght));
	}
	
	private int[] getItemButtonRect(int id) {
		int offsetY = 11 * id - getScroll();
		//System.out.println("Id: " + id + " OffsetY: " + getScroll());
		int height = 11;
		int y = getY() + offsetY;
		
		if (offsetY < 0) {
			height += offsetY;
			y -= offsetY;
		}else if (offsetY + height >= 55) {
			height = 55 - offsetY;
		}
		return new int[] {getX(), y, 84, height, offsetY};
	}
	
	@Override
	public void drawBackground(GuiBase gui, int x, int y) {
		if (!isActive()) return;
		
		for (int i = 0; i < list.size(); i++) {
			int[] rect = getItemButtonRect(i);
			
			if(rect[3] > 0) {
				
				int srcY = 223;
				int hoverSrcY = srcY + (inRect(x, y, rect) ? 11 : list.get(i) == null ? 33 : 0);
				int selectedSrcY = 234;
				
				if(rect[4] < 0) {
					srcY -= rect[4];
					selectedSrcY -= rect[4];
					hoverSrcY -= rect[4];
				}
				draw(gui, rect, 172, srcY);
				draw(gui, rect, 172, hoverSrcY);
				if(i == selectedIndex) 
					draw(gui, rect, 172, selectedSrcY);
					
			}
		}
		int[] pos = {scrollArea[0] - getX(), scrollArea[1] - getY() + scrollPos, 9, 8};
		draw(gui, pos, 157, 218 + (list.size() >= 6 ? 0: 8));
	}
	
	@Override
	public void drawForeground(GuiBase gui, int x, int y) {
		if (!isActive()) return;
		
		for (int i = 0; i < list.size(); i++) {
			int[] rect = getItemButtonRect(i);
			
			int x1 = rect[0] + 3;
			int y1 = rect[1] + 2;
			if (rect[4] < 0) 
				y1 += rect[4];
			
			if ((rect[4] >= - 8) && (rect[4] <= 52))
				gui.getFontRenderer().drawString(list.get(i) == null ? "" : list.get(i), x1, y1, 4210752);
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("watercraft", "textures/gui/controllunit.png"));
			GL11.glColor4f(1F, 1F, 1F, 1F);
			gui.drawTexturedModalRect(getX() , getY() - 8, 19, 57, 50, 8);
			gui.drawTexturedModalRect(getX() , getY() + 55, 19, 120, 50, 8);
		}
	}
	
	public void onClick(int id) {}
 	
	@Override
	public void mouseMoveClick(GuiBase gui, int x, int y, int button, long timeSinceClicked) {
		if (!isActive()) return;
		
		if (isScrolling)
			if (button == -1) {
				this.isScrolling = false;
			} else {
				doScroll(y);
			}
	}
	
	@Override
	public void mouseClick(GuiBase gui, int x, int y, int button) {
		if (!isActive()) return;
		
		for(int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				int[] rect = getItemButtonRect(i);
				
				if ((rect[3] > 0) && (inRect(x, y, rect))) {
					if (selectedIndex == i) selectedIndex = -1;
					else selectedIndex = i;
					onClick(i);
					break;
				}
			}
		}
		int[] pos = {scrollArea[0] - getX(), scrollArea[1] - getY(), 9, scrollArea[3]};
		if ((list.size() >= 6) && (inRect(x, y, pos))) {
			doScroll(y);
			isScrolling = true;
		}
		
	}
	
	@Override
	public void mouseReleased(GuiBase gui, int x, int y, int button) {
		if (isScrolling) {
			isScrolling = false;
		}
	}
	
	private int[] scrollArea = {105 + getX(), 65 + getY(), 9, 55};
	
}
