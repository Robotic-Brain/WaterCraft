package dgrxf.watercraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.common.container.ToolboxContainer;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;
import dgrxf.watercraft.util.TranslationHelper.TH;

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
        
        String playerName;
        if (tile != null) {
            playerName = tile.getPlayerName();
        } else {
            // TODO: This should be a constant!
            playerName = stack.getTagCompound().getString("playerName");
        }
        fontRenderer.drawString(TH.translate(TH.buildKey("toolbox_owner_text"), playerName), guiLeft + 7, guiTop + 5, 0x404040);
        
    }
}
