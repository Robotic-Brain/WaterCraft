package dgrxf.watercraft.client.gui.interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.client.gui.GuiColor;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.item.boat.ItemModularBoat;
import dgrxf.watercraft.module.ModuleRegistry;
import dgrxf.watercraft.network.PacketHandler;
import dgrxf.watercraft.server.container.BoatAssemblerContainer;
import dgrxf.watercraft.tileentity.WCTileEntityBoatAssembler;

public class GuiBoatAssembler extends GuiBase {
	
	private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/boatassembler.png"); 
	private WCTileEntityBoatAssembler inventory;
	private InventoryPlayer playerInv;
	private int rotation = 0;
	private GuiButton assemble;
	private GuiGraphicsRectangle[] drawRects = {new GuiGraphicsRectangle(7, 28, 90, 90, playerInv), new GuiGraphicsRectangle(99, 28, 90, 90, playerInv)};
	
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
		assemble = new GuiButton(0, guiLeft+110, guiTop+6, 60, 20, "Assemble");
		buttonList.add(assemble);
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		PacketHandler.sendInterfacePacket((byte)par1GuiButton.id, new byte[0]);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		GL11.glColor4f(1, 1, 1, 1);
		fontRenderer.drawString("Boat Assembler", 8, 6, GuiColor.GRAY.toRGB());
		fontRenderer.drawSplitString(returnItemName(0), 10, 31, 85, returnItemColour(0));
		fontRenderer.drawSplitString(returnItemName(1), 102, 31, 85, returnItemColour(1));

		rotation++;
		renderingHandler(53, 75, inventory.getStackInSlot(0));
		renderingHandler(145, 75, inventory.getStackInSlot(1));
	}
	
	public String returnItemName(int slot){
		ItemStack stack = inventory.getStackInSlot(slot);
		if(stack != null && (ModuleRegistry.isItemRegistered(stack) || stack.getItem() instanceof ItemModularBoat))
			return stack.getDisplayName();
		else if(stack == null){
			return "Empty";
		}
		else{
			return "Not a Module";
		}
	}
	
	public int returnItemColour(int slot){
		ItemStack stack = inventory.getStackInSlot(slot);
		if(stack != null && (ModuleRegistry.isItemRegistered(stack) || stack.getItem() instanceof ItemModularBoat))
			return GuiColor.WHITE.toRGB();
		else if(stack == null){
			return GuiColor.WHITE.toRGB();
		}
		else{
			return GuiColor.RED.toRGB();
		}
	}
	
	public void renderingHandler(int x, int y, ItemStack stack){
		if(stack != null){

				GL11.glPushMatrix();
				RenderHelper.disableStandardItemLighting();
				
				if(rotation == 360)
					rotation = 0;
				
				if(stack.getItem() == ModItems.modularBoat){
					
				}else if(stack.getItem() instanceof ItemBlock){
					
				}else {
					
				}
				RenderHelper.enableStandardItemLighting();
				GL11.glPopMatrix();
			}
	}
	

}
