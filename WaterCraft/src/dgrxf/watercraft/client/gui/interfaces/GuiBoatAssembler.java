package dgrxf.watercraft.client.gui.interfaces;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import dgrxf.watercraft.client.gui.GuiColor;
import dgrxf.watercraft.entity.boat.ModularBoat;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.server.container.BoatAssemblerContainer;

public class GuiBoatAssembler extends GuiBase {
	
	private static final ResourceLocation texture = new ResourceLocation("watercraft", "textures/gui/boatassembler.png"); 
	private IInventory inventory;
	private InventoryPlayer playerInv;
	private int rotation = 0;
	
	public GuiBoatAssembler(InventoryPlayer inventory, IInventory inv) {
		super(new BoatAssemblerContainer(inventory, inv));
		this.xSize = 196;
		this.ySize = 218;
		this.inventory = inv;
		this.playerInv = inventory;
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
		fontRenderer.drawString(returnItemName(0), 10, 31, GuiColor.WHITE.toRGB());
		fontRenderer.drawString(returnItemName(1), 103, 31, GuiColor.WHITE.toRGB());

		rotation++;
		renderItem(53, 75, inventory.getStackInSlot(0));
		renderItem(145, 75, inventory.getStackInSlot(1));
	}
	
	public String returnItemName(int slot){
		ItemStack stack = inventory.getStackInSlot(slot);
		return stack != null ? stack.getDisplayName() : "Empty";
	}
	
	public void renderItem(int x, int y, ItemStack stack){
		if(stack != null){

				GL11.glPushMatrix();
				RenderHelper.disableStandardItemLighting();
				
				if(rotation == 360)
					rotation = 0;
				
				if(stack.getItem() == ModItems.modularBoat){
					GL11.glTranslatef(x, y, 100);
					float scale = 30F;
					GL11.glScalef(-scale, scale, scale);
					GL11.glRotatef(180, 0, 0, 1);
					GL11.glRotatef(20, 1, 0, 0);
					GL11.glRotatef(rotation, 0, 1, 0);
					RenderManager.instance.renderEntityWithPosYaw(new ModularBoat(Minecraft.getMinecraft().theWorld, 0, 0, 0, stack.getTagCompound()), 0, 0, 0, 0, 0);
				}else if(stack.getItem() instanceof ItemBlock){
					renderBlock(stack, x, y, 30F);
				}else {
					renderItem(stack, x, y);
				}
				RenderHelper.enableStandardItemLighting();
				GL11.glPopMatrix();
			}
	}
	
	private void renderItem(ItemStack stack, int x, int y) {
        Icon icon = playerInv.player.getItemIcon(stack, stack.getItemDamage());
        TextureManager texturemanager = Minecraft.getMinecraft().renderEngine;

        if (icon == null)
        {
            GL11.glPopMatrix();
            return;
        }
        
		texturemanager.bindTexture(texturemanager.getResourceLocation(stack.getItemSpriteNumber()));
        Tessellator tessellator = Tessellator.instance;
        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        GL11.glTranslatef(x, y, 100.0F);
        GL11.glRotatef(rotation, 0, 1, 0);
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glScalef(-30, 30, 30);
        ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight()-10, 0.0625F);
	}

	private void renderBlock(ItemStack stack, int x, int y, float scale) {
		TextureManager textMan = Minecraft.getMinecraft().getTextureManager();
		RenderBlocks renderBlocks = new RenderBlocks();
		
		textMan.bindTexture(textMan.getResourceLocation(0));
		GL11.glTranslatef(x, y, 100);
		GL11.glScalef(-scale, scale, scale);
		GL11.glRotatef(180, 0, 0, 1);
		GL11.glRotatef(20, 1, 0, 0);
		GL11.glRotatef(rotation, 0, 1, 0);
		renderBlocks.renderBlockAsItem(Block.blocksList[stack.itemID], stack.getItemDamage(), 1.0f);
	}

	public boolean isItemBlock(int id){
		
		if(id < Block.blocksList.length){
			System.out.println("id");
			return Block.blocksList[id] != null ? true : false;
		}
		return false;
	}
}
