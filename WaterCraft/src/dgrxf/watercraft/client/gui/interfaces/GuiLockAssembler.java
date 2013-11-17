package dgrxf.watercraft.client.gui.interfaces;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.client.gui.container.LockAssemblerContainer;
import dgrxf.watercraft.tileentity.WCTileEntityLockAssembler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiLockAssembler extends GuiBase {
	
	private WCTileEntityLockAssembler lock;
	private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/lockassembler.png"); 

	public GuiLockAssembler(InventoryPlayer inventory, WCTileEntityLockAssembler lock) {
		super(new LockAssemblerContainer(inventory, lock));
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
