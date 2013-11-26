package dgrxf.watercraft.client.gui.interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.server.container.CalculatorContainer;

public class GuiCalculator extends GuiContainer {
    
    private String screenText = "";
    private String temp;
    
    public GuiCalculator() {
        super(new CalculatorContainer());
        xSize = 151;
        ySize = 138;
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
                if (id != 10) {
                    buttonList.add(new GuiButton(id, guiLeft + 12 + 19 * x, guiTop + 41 + y * 22, 18, 20, "" + id));
                    id++;
                }
            }
        }
        buttonList.add(new GuiButton(10, guiLeft + 12, guiTop + 105, 18, 20, "+"));
        buttonList.add(new GuiButton(11, guiLeft + 31, guiTop + 105, 18, 20, "-"));
        buttonList.add(new GuiButton(12, guiLeft + 50, guiTop + 105, 18, 20, "/"));
        buttonList.add(new GuiButton(13, guiLeft + 69, guiTop + 105, 18, 20, "*"));
        buttonList.add(new GuiButton(14, guiLeft + 12, guiTop + 84, 57, 20, "="));
        buttonList.add(new GuiButton(15, guiLeft + 88, guiTop + 105, 18, 20, "C"));
        buttonList.add(new GuiButton(16, guiLeft + 69, guiTop + 84, 38, 20, "<="));
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
        
    }
}
