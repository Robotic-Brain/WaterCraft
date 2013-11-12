package dgrxf.watercraft.client.gui.interfaces.controlunit;

import net.minecraft.client.gui.GuiButton;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.client.gui.interfaces.GuiBase;
import dgrxf.watercraft.client.gui.interfaces.GuiRectangle;

@SideOnly(Side.CLIENT)
public abstract class GuiTab extends GuiRectangle {

	private String name;
	private int id;
	
	public GuiTab(String name, int id) {
		super(8  + id * 45, 18, 45, 12);
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public void drawTabTitle(ControlUnitGUI gui, int x, int y) {
		if(gui.activeTab == this)
			gui.getFontRenderer().drawString(name, getX() + 2,  getY() + 2, 0xFFFFFF);
		else
			gui.getFontRenderer().drawString(name, getX() + 2,  getY() + 2, 0);
	}

	public abstract void drawBackground(ControlUnitGUI gui, int x, int y);
	public abstract void drawForeground(ControlUnitGUI gui, int x, int y);
	public void actionPerformed(GuiButton button) {}
	public void mouseClick(ControlUnitGUI gui, int x, int y, int button) {}
	public void mouseMoveClick(ControlUnitGUI gui, int x, int y, int button, long timeSinceClicked) {}
	public void mouseReleased(ControlUnitGUI gui, int x, int y,int  button) {}

	

	
}
