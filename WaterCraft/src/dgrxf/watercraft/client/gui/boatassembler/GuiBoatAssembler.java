package dgrxf.watercraft.client.gui.boatassembler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.client.gui.GuiColor;
import dgrxf.watercraft.client.gui.components.GuiGraphicsRectangle;
import dgrxf.watercraft.client.gui.components.GuiRectangle;
import dgrxf.watercraft.common.container.BoatAssemblerContainer;
import dgrxf.watercraft.interfaces.IModularBoat;
import dgrxf.watercraft.item.boat.ItemModularBoat;
import dgrxf.watercraft.module.ModuleHelper;
import dgrxf.watercraft.module.ModuleRegistry;
import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.tileentity.WCTileEntityBoatAssembler;
import dgrxf.watercraft.util.Rectangle;

/**
 * 
 * GuiBoatAssembler
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class GuiBoatAssembler extends GuiBase {
	
	private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/boatassembler.png"); 
	private WCTileEntityBoatAssembler inventory;
	private InventoryPlayer playerInv;
	private int rotation = 0;
	private GuiButton assemble;
	private GuiGraphicsRectangle[] drawRects = new GuiGraphicsRectangle[2];
	
	public GuiBoatAssembler(InventoryPlayer inventory, WCTileEntityBoatAssembler inv) {
		super(new BoatAssemblerContainer(inventory, inv));
		this.xSize = 196;
		this.ySize = 218;
		this.inventory = inv;
		this.playerInv = inventory;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		assemble = new GuiButton(0, guiLeft + 110, guiTop + 6, 60, 20, "Assemble");   // TODO Translation
		buttonList.add(assemble);
		drawRects[0] = new GuiBoatAssemblerRectangle(7, 28, 90, 90, playerInv, new Rectangle(43, 96, 17, 17));
		drawRects[1] = new GuiBoatAssemblerRectangle(99, 28, 90, 90, playerInv, new Rectangle(135, 96, 17, 17));
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		PacketHandler.sendInterfacePacket((byte)par1GuiButton.id, new byte[0]);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float tickTime, int mouseX, int mouseY) {
		GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		rotation++;
		int k = 0;
		for(GuiGraphicsRectangle rect : drawRects){
			rect.renderingHandler(rect.getWidth()/2 + guiLeft, rect.getHeight()/2 + guiTop, inventory.getStackInSlot(k), rotation, 25, this);
			k++;
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GL11.glColor4f(1, 1, 1, 1);
		// TODO Translation
		fontRenderer.drawString("Boat Assembler", 8, 6, GuiColor.GRAY.toRGB());
		fontRenderer.drawString("Inventory", 8, 122, GuiColor.GRAY.toRGB());
		fontRenderer.drawSplitString(returnItemName(0), 10, 31, 85, returnItemColour(0));
		fontRenderer.drawSplitString(returnItemName(1), 102, 31, 85, returnItemColour(1));

		//rotation++;
		int i = 0;
		for(GuiGraphicsRectangle rect : drawRects){
			if(inventory.getStackInSlot(i) != null && rect.inRect(this, mouseX, mouseY)){
				if(ModuleRegistry.isItemRegistered(inventory.getStackInSlot(i)) || inventory.getStackInSlot(i).getItem() instanceof IModularBoat)
					rect.drawHoverString(this, mouseX, mouseY, ModuleHelper.getModuleInfo(inventory.getStackInSlot(i)));
			}
			i++;
		}
	}
	
	public String returnItemName(int slot){
		ItemStack stack = inventory.getStackInSlot(slot);
		if(stack != null && (ModuleRegistry.isItemRegistered(stack) || stack.getItem() instanceof ItemModularBoat))
			return stack.getDisplayName();
		else if(stack == null) return "Empty";  // TODO Translation
		else return "Not a Module";   // TODO Translation
	}
	
	public int returnItemColour(int slot){
		ItemStack stack = inventory.getStackInSlot(slot);
		if(stack != null && (ModuleRegistry.isItemRegistered(stack) || stack.getItem() instanceof ItemModularBoat))
			return GuiColor.WHITE.toRGB();
		else if(stack == null) return GuiColor.WHITE.toRGB();
		else return GuiColor.RED.toRGB();
	}	
}
