package dgrxf.watercraft.client.renderer.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import dgrxf.watercraft.lib.RenderInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ItemControlUnitRenderer implements IItemRenderer{

	private IModelCustom modelController = AdvancedModelLoader.loadModel("/assets/watercraft/models/controlunit.obj");
	
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
		switch(type){
		case ENTITY:
			renderController(0.0F, 1.0F, 0.0F, 0.083F);
			GL11.glRotatef(-45, 0.0F, 1F, 0.0F);
			break;
		case EQUIPPED:
			renderController(8F, 0.0F, 8F, 0.083F);
			GL11.glRotatef(-90, 0.0F, 1F, 0.0F);
			break;
		case EQUIPPED_FIRST_PERSON:
			renderController(2.0F, 3.0F, 3.0F, 0.083F);
			GL11.glRotatef(180, 0.0F, 1F, 0.0F);
			GL11.glRotatef(-10, 1.0F, 0, 0);
			GL11.glRotatef(5, 0, 0, 1.0F);
			break;
		case INVENTORY:
			renderController(0.0F, -6.0F, 0.0F, 0.083F);
			break;
		default: return;
		}
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(RenderInfo.CONTROL_UNIT_TEXTURE_LOCATION);
		modelController.renderAll();
		GL11.glPopMatrix();
	}
	
	private void renderController(float x, float y, float z, float scale){
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(x, y, z);
	}

}
