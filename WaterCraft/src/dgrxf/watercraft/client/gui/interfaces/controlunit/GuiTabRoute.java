package dgrxf.watercraft.client.gui.interfaces.controlunit;

import java.util.ArrayList;

import dgrxf.watercraft.client.gui.interfaces.GuiExtra;
import dgrxf.watercraft.client.gui.interfaces.GuiRectangle;
import net.minecraft.client.gui.GuiButton;

public class GuiTabRoute extends GuiTab {

	private ArrayList<String> directions = new ArrayList<String>() {{ add("SOUTH"); add("EAST"); add("WEST"); }};
	private ArrayList<String> targetDirections = new ArrayList<String>();
	private String selectedString = "NORTH";
	private GuiRectangle dropDownButton = new GuiRectangle(75, 40, 11, 14);	
	private boolean drawMenu;
	
	private GuiExtra[] menu = new GuiExtra[4];
	
	public GuiTabRoute(String name, int id) {
		super(name, id);	
		
		menu[0] = new GuiDropdown(18, 40, selectedString, this);
		for(int i = 0; i < menu.length - 1; i++) {
			menu[i + 1] = new GuiDropdown(18, 40 + 15 * (i + 1), directions.get(i), this);
		}
	}
	
	@Override
	public void drawBackground(ControlUnitGUI gui, int x, int y) {
		if(drawMenu) {
			dropDownButton.draw(gui, 146, 218);
		}
		
		if (dropDownButton.inRect(gui, x, y)) {
			dropDownButton.draw(gui, 135, 218);
		}
		
		if (drawMenu) {
			for(GuiExtra extra: menu) extra.drawBackground(gui, x, y);
		}
	}

	@Override
	public void drawForeground(ControlUnitGUI gui, int x, int y) {
		dropDownButton.drawString(gui, x, y, "Click to see all options");
		
		if (drawMenu) {
			for(GuiExtra extra: menu) extra.drawForeground(gui, x, y);
		}else{
			gui.getFontRenderer().drawString(selectedString, 22, 44, 0);
		}
	}
	
	@Override
	public void mouseClick(ControlUnitGUI gui, int x, int y, int button) {
		if (drawMenu) {
			if (dropDownButton.inRect(gui, x, y)) {
				drawMenu = false;
			}
			for(GuiExtra extra: menu) extra.mouseClick(gui, x, y, button);
		}else{
			if (dropDownButton.inRect(gui, x, y)) {
				drawMenu = true;
			}
		}
	}
	
	@Override
	public void mouseMoveClick(ControlUnitGUI gui, int x, int y, int button, long timeSinceClicked) {
		if (drawMenu) {
			for(GuiExtra extra: menu) extra.mouseMoveClick(gui, x, y, button, timeSinceClicked);
		}
	}
	
	@Override
	public void mouseReleased(ControlUnitGUI gui, int x, int y,int  button) {
		if (drawMenu) {
			for(GuiExtra extra: menu) extra.mouseReleased(gui, x, y, button);
		}
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 0:
			targetDirections.add(selectedString);
			break;
		default:
			break;
		}
	}
	
	public void setCurrentDir(String dir) {
		drawMenu = false;
		int i = 0;
		for (String s: directions) {
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
		for(int j = 0; j < menu.length - 1; j++) {
			menu[j + 1] = new GuiDropdown(18, 40 + 15 * (j + 1), directions.get(j), this);
		}
	}
	
	public ArrayList<String> getTargetDirections() {
		return targetDirections;
	}
	
}
