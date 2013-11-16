package dgrxf.watercraft.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.client.models.WCModelChest;
import dgrxf.watercraft.client.renderer.block.WCChestRenderer;

public class ItemWCChestRenderer  implements IItemRenderer {

	private WCModelChest chestModel = new WCModelChest();
    
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
		GL11.glScalef(-1F, -1F, 1F);
        
        switch (type) {
            case EQUIPPED:
            	GL11.glTranslatef(-1.0F, -1.0F, 1.0F);
            	GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                break;
            case EQUIPPED_FIRST_PERSON:
            	GL11.glTranslatef(0.0F, -1.285F, 0.25F);
            	GL11.glScalef(0.65F, 0.65F, 0.65F);
                break;
            case INVENTORY:
            	GL11.glTranslatef(0.0F, -0.05F, 0F);
            	GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                break;
            case ENTITY:
            	GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            	break;
            default:
        }
        
        Minecraft.getMinecraft().renderEngine.bindTexture(WCChestRenderer.RES_NORMAL_SINGLE);
        chestModel.renderAll(false);
        
        GL11.glPopMatrix();
    }
}
