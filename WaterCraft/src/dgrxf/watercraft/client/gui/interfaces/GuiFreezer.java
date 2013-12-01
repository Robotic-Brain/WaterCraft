package dgrxf.watercraft.client.gui.interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.server.container.FreezerContainer;
import dgrxf.watercraft.tileentity.WCTileEntityFreezer;

/**
 * 
 * GuiFreezer
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class GuiFreezer extends GuiContainer {
    
    private String screenText;
    private String temp;
    
    public GuiFreezer(WCTileEntityFreezer te) {
        //CalculatorContainer is an empty container, so I'm stealing that instead of creating a new one.
        //If this is a problem please create a new one.
        super(new FreezerContainer(te));
        xSize = 151;
        ySize = 80;
    }
    
    private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/freezer.png");
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRenderer.drawString("Select block mode", 8, 3, 0x404040);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
        buttonList.add(new GuiButton((0), guiLeft + 12, guiTop + 12, 35, 20, "Off"));
        buttonList.add(new GuiButton((1), guiLeft + 12, guiTop + 31, 35, 20, "Freeze"));
        buttonList.add(new GuiButton((2), guiLeft + 12, guiTop + 50, 35, 20, "Smelt"));
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
        PacketHandler.sendFreezerPacket((byte) button.id);
    }
}
