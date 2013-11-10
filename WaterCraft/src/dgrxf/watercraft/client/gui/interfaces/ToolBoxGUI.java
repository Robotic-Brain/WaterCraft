package dgrxf.watercraft.client.gui.interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.client.gui.container.ToolboxContainer;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

/**
 * Class Created By: Drunk Mafia (TDM) Class Last Modified By: Drunk Mafia (TDM)
 * 
 * Class Last Modified On: 11/09/2013 MM/DD/YYYY
 * 
 */

public class ToolBoxGUI extends GuiContainer {
<<<<<<< HEAD
    
    private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/toolbox.png");
    private WCTileEntityToolBox           tile;
    
    public ToolBoxGUI(InventoryPlayer inventory, WCTileEntityToolBox te) {
        super(new ToolboxContainer(inventory, te));
        tile = te;
        
        xSize = 176;
        ySize = 218;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float x, int y, int j) {
        GL11.glColor4f(1, 1, 1, 1);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        fontRenderer.drawString(tile.getPlayerName() + "'s ToolBox", guiLeft + 7, guiTop + 5, 0x404040);
        
    }
    
=======

	private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/toolbox.png");
	private WCTileEntityToolBox tile;
	private ItemStack stack;
	
	public ToolBoxGUI(InventoryPlayer inventory, WCTileEntityToolBox te) {
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
		
		fontRenderer.drawString(tile != null ? tile.getPlayerName() + "'s ToolBox" : stack.getTagCompound().getString("playerName") + "'s ToolBox", guiLeft + 7, guiTop + 5, 0x404040);
		
	}

>>>>>>> Commit
}
