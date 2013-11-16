package dgrxf.watercraft.client.gui.interfaces;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.client.gui.container.CalculatorContainer;

public class GuiCalculator extends GuiContainer{

	private String screenText;
	private String temp;
	
	public GuiCalculator() {
		super(new CalculatorContainer());	
        xSize = 196;
        ySize = 218;
	}

	private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/calculator.png");
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		fontRenderer.drawString("Calculator", 8, 3, 0x404040);
		
		fontRenderer.drawSplitString(screenText, 10, 17, 178, 0x404040);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		int id = 0;
		for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 5; x++) {
            	if(id != 10){
            		id++;
            		buttonList.add(new GuiButton(id, guiLeft + 12 + 19 * x, guiTop + 41 + y * 22, 18, 20, "" + id));
            	}
            }
        }
		buttonList.add(new GuiButton((id + 1), guiLeft + 12, 118, 18, 20, "+"));
		buttonList.add(new GuiButton((id + 2), guiLeft + 31, 118, 18, 20, "-"));
		buttonList.add(new GuiButton((id + 3), guiLeft + 50, 118, 18, 20, "/"));
		buttonList.add(new GuiButton((id + 2), guiLeft + 69, 118, 18, 20, "*"));
		buttonList.add(new GuiButton((id + 3), guiLeft + 12, 96, 57, 20, "="));
		buttonList.add(new GuiButton((id + 4), guiLeft + 88, 118, 18, 20, "C"));
		buttonList.add(new GuiButton((id + 5), guiLeft + 69, 96, 38, 20, "<="));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		
	}
}
