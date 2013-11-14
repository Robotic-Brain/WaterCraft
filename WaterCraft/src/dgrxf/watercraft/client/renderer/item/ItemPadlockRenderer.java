package dgrxf.watercraft.client.renderer.item;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By:Drunk Mafia Class Last Edited On:14/06/2013 MM/DD/YYYYY
 * 
 */

public class ItemPadlockRenderer implements IItemRenderer{

	private IModelCustom modelPadlock = AdvancedModelLoader.loadModel("/assets/watercraft/models/padlock_open.obj");
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		switch (type) {
	        case EQUIPPED:
	            GL11.glTranslatef(0.4F, 1F, 0.6F);
	            GL11.glScalef(1F, 1F, 1F);
	            break;
	        case EQUIPPED_FIRST_PERSON:
	            GL11.glTranslatef(0F, 0.7F, 0.5F);
	            GL11.glRotatef(180, 0F, 1F, 0);
	            GL11.glScalef(1F, 1F, 1F);
	            break;
	        case INVENTORY:
	            GL11.glTranslatef(0.0F, -0.26F, 0.0F);
	            GL11.glRotatef(180, 0F, 1F, 0);
	            GL11.glScalef(1F, 1F, 1F);
	            break;
	        default:
		}
		GL11.glScalef(5F, 5F, 5F);
		modelPadlock.renderAll();
		
		GL11.glPopMatrix();		
	}

}
