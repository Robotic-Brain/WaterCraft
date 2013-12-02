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
import dgrxf.watercraft.util.Rectangle;

/**
 * 
 * GuiGraphicsRectangle
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */

//TODO:Rewrite this class, I didn't write it very well.
public abstract class GuiGraphicsRectangle extends GuiRectangle{
	protected InventoryPlayer inv;
	protected Rectangle[] exemptAreas = null;
	
	public GuiGraphicsRectangle(int x, int y, int w, int h, InventoryPlayer inv, Rectangle... exemptAreas){
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
    			for(Rectangle exempt : exemptAreas){
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
	
	public abstract void renderingHandler(int x, int y, ItemStack stack, int rotation, float scale);

	
}
