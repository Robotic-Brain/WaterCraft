package dgrxf.watercraft.client.gui.components;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.client.gui.GuiBase;
import dgrxf.watercraft.client.gui.GuiColor;
import dgrxf.watercraft.entity.boat.ModularBoat;
import dgrxf.watercraft.item.ModItems;

/**
 * 
 * GuiGraphicsRectangle
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class GuiGraphicsRectangle extends GuiRectangle{
	InventoryPlayer inv;
	GuiRectangle[] exemptAreas = null;
	
	public GuiGraphicsRectangle(int x, int y, int w, int h, InventoryPlayer inv, GuiRectangle... exemptAreas){
		super(x, y, w, h);
		this.inv = inv;
		this.exemptAreas = exemptAreas;
	}
	
	public void drawString(String s, int x, int y, int color, FontRenderer fontRenderer){
		int length = s.length();
		fontRenderer.drawString(s, x + this.getX(), y + this.getY(), GuiColor.WHITE.toRGB());
	}
	
    public boolean inRect(GuiBase gui, int x, int y) {
    	if(exemptAreas != null){
    		if(super.inRect(gui, x, y)){
    			for(GuiRectangle exempt : exemptAreas){
		    		if(exempt.inRect(gui, x, y)){
		    			return false;
		    		}
    			}
	    		return true;
    		}
    		return false;
    	}else{
    		return super.inRect(gui, x, y);
    	}
    }
	
	public void renderingHandler(int x, int y, ItemStack stack, int rotation, float scale){
		if(stack != null){

			GL11.glPushMatrix();
			
			if(rotation == 360)
				rotation = 0;
			
			if(stack.getItem() == ModItems.MODULAR_BOAT.getItem()){
				//temp code
				renderRotatingEntity(new ModularBoat(Minecraft.getMinecraft().theWorld, 0, 0, 0, stack.getTagCompound()), x, y, rotation, scale);
			}else if(stack.getItem() instanceof ItemBlock){
				renderStackAsBlock(stack, x, y, scale, rotation);
			}else {
				renderStackAsItem(stack, x, y, rotation, scale);
			}
			GL11.glPopMatrix();
		}
	}
	
	public void renderRotatingEntity(Entity e, int x, int y, float rotation, float scale){
		GL11.glPushMatrix();
		GL11.glTranslatef(this.getX() + x, this.getY() + y, 100);
		GL11.glScalef(-scale, scale, scale);
		GL11.glRotatef(180, 0, 0, 1);
		GL11.glRotatef(20, 1, 0, 0);
		GL11.glRotatef(rotation, 0, 1, 0);
		RenderManager.instance.renderEntityWithPosYaw(e, 0, 0, 0, 0, 0);
		GL11.glPopMatrix();
	}
	
	public void renderStackAsItem(ItemStack stack, int x, int y, float rotation, float scale) {
		GL11.glPushMatrix();
		
		
        Icon icon = inv.player.getItemIcon(stack, 10);
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
        GL11.glTranslatef(this.getX() + x,this.getY() + y, 100.0F);
        GL11.glRotatef(rotation, 0, 1, 0);
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glScalef(-scale, scale, scale);
        ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
		GL11.glPopMatrix();
	}

	public void renderStackAsBlock(ItemStack stack, int x, int y, float scale, float rotation) {
		GL11.glPushMatrix();
		TextureManager textMan = Minecraft.getMinecraft().getTextureManager();
		RenderBlocks renderBlocks = new RenderBlocks();
		
		textMan.bindTexture(textMan.getResourceLocation(0));
		GL11.glTranslatef(this.getX() + x, this.getY() + y, 100);
		GL11.glScalef(-scale, scale, scale);
		GL11.glRotatef(180, 0, 0, 1);
		GL11.glRotatef(20, 1, 0, 0);
		GL11.glRotatef(rotation, 0, 1, 0);
		renderBlocks.renderBlockAsItem(Block.blocksList[stack.itemID], stack.getItemDamage(), 1.0f);
		GL11.glPopMatrix();
	}
	
}