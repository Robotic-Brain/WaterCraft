package dgrxf.watercraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.common.container.LockAssemblerContainer;
import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.tileentity.WCTileEntityLockAssembler;

/**
 * 
 * GuiLockAssembler
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class GuiLockAssembler extends GuiBase {
	
	private WCTileEntityLockAssembler lock;
	private KeySlider[] sliders = new KeySlider[5];
	private int code;
	
	private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/lockassembler.png"); 

	public GuiLockAssembler(InventoryPlayer inventory, WCTileEntityLockAssembler lock) {
		super(new LockAssemblerContainer(inventory, lock));
		this.xSize = 196;
		this.ySize = 182;
		
		for (int i = 0; i < sliders.length; i++) {
			sliders [i] = new KeySlider(46 + 8*i, 78, 7, 5);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        for (int i = 0; i < sliders.length; i++) {
        	int srcY = 11; 
        	if (sliders[i].inRect(this, mouseX, mouseY) || sliders[i].clicked) {
        		srcY += 5;
        	}
        	
        	drawTexturedModalRect(sliders[i].getX() + guiLeft, guiTop + sliders[i].getY(), xSize, 21, 7, 78 - sliders[i].getY());
        	sliders[i].draw(this, xSize, srcY);
        }
	}
	
	@Override
	protected void mouseClicked(int x, int y, int mouseButton) {
		super.mouseClicked(x, y, mouseButton);
		
		if (mouseButton == 0) {
			for (int i = 0; i < sliders.length; i++) {
				if (sliders[i].inRect(this, x, y)) {
					sliders[i].clicked = true;
				}
			}
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		
		fontRenderer.drawString("Code: " + Integer.toString(code), 7, 5, 0x404040);
	}
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int button, long time) {
		super.mouseClickMove(mouseX, mouseY, button, time);
		if (button == 0) {
			for (KeySlider slid : sliders) {
				 if(slid.clicked) {
					 int y = (83 - mouseY + guiTop) / 5 + 1;
					 y = 83 - 5 * y;
					 if (y > 78) y = 78;
					 if (y < 43) y = 43;
					 slid.setY(y);
				 }
			 }
		}
	}
	
	@Override
	protected void mouseMovedOrUp(int par1, int par2, int par3) {
		super.mouseMovedOrUp(par1, par2, par3);
		code = 0;
		for (KeySlider slid : sliders) {
			slid.clicked = false;
			int i = (78 - slid.getY()) / 5;
			code += i;
			code *= 10;
		}
		code /= 10;
		
		short codeToSend = Short.valueOf(Integer.toString(code), 8);
		PacketHandler.sendLockAssemblerPacket(codeToSend);
	}

}
