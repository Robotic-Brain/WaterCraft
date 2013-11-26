package dgrxf.watercraft.client.gui.interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.client.gui.container.BoatAssemblerContainer;

public class GuiBoatAssembler extends GuiBase{
	
	private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/boatassembler.png"); 
	
	public GuiBoatAssembler(InventoryPlayer inventory, IInventory inv) {
		super(new BoatAssemblerContainer(inventory, inv));
		this.xSize = 196;
		this.ySize = 182;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
