package dgrxf.watercraft.client.gui.boat;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.client.gui.GuiColor;
import dgrxf.watercraft.client.gui.components.GuiDropDownScrollList;
import dgrxf.watercraft.client.gui.crane.CraneGUI;
import dgrxf.watercraft.common.container.BoatContainer;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.enumeration.Colours;
import dgrxf.watercraft.module.ModuleHelper;
import dgrxf.watercraft.module.ModuleRegistry;

/**
 * 
 * GuiBoat
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class GuiBoat extends GuiBase{
	
	private AbstractBaseBoat boat;
	
	private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/boat.png");
	private ArrayList<ItemStack> modules;
	
	public GuiBoat(IInventory player, AbstractBaseBoat boat) {
		super(new BoatContainer(player, boat));
		xSize = 196;
        ySize = 218;
        
        this.boat = boat;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		 GL11.glColor4f(1, 1, 1, 1);
	     Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	     drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		fontRenderer.drawString("Boat", 8, 5, GuiColor.GRAY.toRGB());
	}
	
	@Override
    protected void mouseClicked(int x, int y, int button) {
	}

	@Override
    protected void mouseClickMove(int x, int y, int button, long timeSinceClicked) {
	}

	@Override
    protected void mouseMovedOrUp(int x, int y, int button) {
	}
}
