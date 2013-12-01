package dgrxf.watercraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.server.container.ToolboxContainer;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

/**
 * 
 * GuiToolBox
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class GuiToolBox extends GuiContainer {
    
    private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/toolbox.png");
    private WCTileEntityToolBox           tile;
    private ItemStack                     stack;
    
    public GuiToolBox(InventoryPlayer inventory, WCTileEntityToolBox te) {
        super(new ToolboxContainer(inventory, te));
        tile = te;
        stack = inventory.getCurrentItem();
        
        xSize = 176;
        ySize = 218;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float x, int y, int j) {
        GL11.glColor4f(1, 1, 1, 1);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        // TODO: Translate
        fontRenderer.drawString(tile != null ? tile.getPlayerName() + "'s ToolBox"
                : stack.getTagCompound().getString("playerName") + "'s ToolBox", guiLeft + 7, guiTop + 5, 0x404040);
        
    }
}
