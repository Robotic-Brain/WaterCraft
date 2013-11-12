package dgrxf.watercraft.client.gui.interfaces.controlunit;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.client.gui.container.ControlUnitContainer;
import dgrxf.watercraft.client.gui.interfaces.GuiBase;
import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.tileentity.WCTileEntityControlUnitDock;
import dgrxf.watercraft.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ControlUnitGUI extends GuiBase {

	private WCTileEntityControlUnitDock unit;
	private final GuiTab[] tabs;
	public GuiTab activeTab;
	private GuiButton addButton;
	protected GuiButton removeButton;
	
	public ControlUnitGUI(InventoryPlayer inventory, WCTileEntityControlUnitDock te) {
		super(new ControlUnitContainer(inventory, te));
		unit = te;
		
		xSize = 196;
		ySize = 218;
		
		tabs = new GuiTab[] {
				new GuiTabRoute("First", 0, this),
				new GuiTabRoute("Second", 1, this),
				new GuiTabRoute("Third", 2, this),
				new GuiTabRoute("Fourth", 3, this)
		};
		LogHelper.log(Level.WARNING, "[DEBUG]: " + unit.activeTabIndex);
		activeTab = tabs[unit.activeTabIndex];
		((GuiTabRoute)activeTab).isActive = true;
	}

	private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/controllunit.png");
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		for(GuiTab tab: tabs) {
			int srcY = 218;
			int srcX = 0;
			
			switch (tab.getId()) {
				case 0:
					srcX = 0;
					break;
				case 3:
					srcX = 90;
					break;
				default:
					srcX = 45;
					break;
			}
			
			if(tab == activeTab) {
				srcY += 24;
			}else if(tab.inRect(this, x, y)){
				srcY += 12;
			}
			
			tab.draw(this, srcX, srcY);
		}
		
		activeTab.drawBackground(this, x, y);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		fontRenderer.drawString("Control Unit", 8, 6, 0x404040);
		
		for(GuiTab  tab: tabs) {
			tab.drawTabTitle(this, x, y);
		}
		
		activeTab.drawForeground(this, x, y);
		
		for(GuiTab tab: tabs) {
			tab.drawHoverString(this, x, y, tab.getName());
		}
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		addButton = new GuiButton(0, guiLeft + 93, guiTop + 38, 80, 20, "Add Direction");
		removeButton = new GuiButton(1, guiLeft + 120, guiTop + 74, 42, 20, "Remove");
		removeButton.enabled = false;
		buttonList.add(addButton);
		buttonList.add(removeButton);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		activeTab.actionPerformed(button);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		
		activeTab.mouseClick(this, x, y, button);
		int i = 0;
		for(GuiTab tab: tabs) {
			if(activeTab != tab) {
				if(tab.inRect(this, x, y)) {
					((GuiTabRoute)activeTab).isActive = false;
					unit.activeTabIndex = i;
					PacketHandler.sendInterfacePacket((byte)0, new byte[] {(byte)i});
					activeTab = tab;
					((GuiTabRoute)activeTab).isActive = true;
					break;
				}
			}
			i++;
		}
	}
	
	@Override
	protected void mouseClickMove(int x, int y, int button, long timeSinceClicked) {
		super.mouseClickMove(x, y, button, timeSinceClicked);
		
		activeTab.mouseMoveClick(this, x, y, button, timeSinceClicked);
	}
	
	@Override
	protected void mouseMovedOrUp(int x, int y, int button) {
		super.mouseMovedOrUp(x, y, button);
		
		activeTab.mouseReleased(this, x, y, button);
	}
	
	public ArrayList<String> getTargetDirectionsCurrentTab() {
		return ((GuiTabRoute)activeTab).getTargetDirections();
	}

}
