package dgrxf.watercraft.client.gui.boat;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.common.container.BoatContainer;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;

/**
 * 
 * GuiBoat
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class GuiBoat extends GuiBase{

	public GuiBoat(IInventory player, AbstractBaseBoat boat) {
		super(new BoatContainer(player, boat));
		xSize = 196;
        ySize = 218;
	}
	
	private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/boat.png");
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		 GL11.glColor4f(1, 1, 1, 1);
	     Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	     drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		
	}
}
