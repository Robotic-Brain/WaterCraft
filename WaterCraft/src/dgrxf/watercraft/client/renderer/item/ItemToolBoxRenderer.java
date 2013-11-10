package dgrxf.watercraft.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.lib.RenderInfo;

public class ItemToolBoxRenderer implements IItemRenderer {
    
	private IModelCustom modelToolBox_closed = AdvancedModelLoader.loadModel("/assets/watercraft/models/toolbox.obj");
	private IModelCustom modelToolBox_open = AdvancedModelLoader.loadModel("/assets/watercraft/models/toolbox_open.obj");
    
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
        NBTTagCompound tag;
        boolean isOpen = false;
        
        if(item.getTagCompound() != null){
        	tag = item.getTagCompound();
        	isOpen = tag.getBoolean("isOpen");
        }
        
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderInfo.TOOLBOX_TEXTURE_LOCATION);
        switch (type) {
            case EQUIPPED:
                GL11.glTranslatef(0.4F, 1.2F, 0.6F);
                GL11.glScalef(1.5F, 1.5F, 1.5F);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(0F, 0.7F, 0.5F);
                GL11.glRotatef(180, 0F, 0.5F, 0);
                GL11.glScalef(1.5F, 1.5F, 1.5F);
                break;
            case INVENTORY:
            	if(isOpen){
            		GL11.glTranslatef(-0.2F, 0.1F, 0.0F);
                    GL11.glScalef(1.6F, 1.6F, 1.6F);
            	}else{
            		GL11.glTranslatef(0.0F, 0.3F, 0.0F);
            		GL11.glScalef(1.8F, 1.8F, 1.8F);
            	}
	                GL11.glRotatef(180, 0F, 1F, 0);
                break;
            default:
        }
        
        if(isOpen)
        	modelToolBox_open.renderAll();
        else
        	modelToolBox_closed.renderAll();
        
        GL11.glPopMatrix();
    }
}
