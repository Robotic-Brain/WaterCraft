package dgrxf.watercraft.client.renderer.item;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.lib.ModInfo;
import dgrxf.watercraft.lib.RenderInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ItemFlagRenderer implements IItemRenderer {

	private IModelCustom flag = AdvancedModelLoader.loadModel("/assets/watercraft/models/Flag.obj");
	
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
        
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.MODID, RenderInfo.FLAG_TEXTURE_LOCATION + (item.getItemDamage() + 1) + ".png"));
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
            	GL11.glTranslatef(-0.2F, 0.1F, 0.0F);
                GL11.glScalef(1.6F, 1.6F, 1.6F);
	            GL11.glRotatef(180, 0F, 1F, 0);
                break;
            default:
        }
        
        System.out.println("SPAM");
        
        flag.renderAll();
        
        GL11.glPopMatrix();		
	}

}
