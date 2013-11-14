package dgrxf.watercraft.client.renderer.item;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.client.renderer.block.WCChestRenderer;
import dgrxf.watercraft.lib.RenderInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class ItemWCChestRenderer  implements IItemRenderer {

	private ModelChest chestModel = new ModelChest();
    
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
        
        Minecraft.getMinecraft().renderEngine.bindTexture(WCChestRenderer.RES_NORMAL_SINGLE);
        switch (type) {
            case EQUIPPED:
                GL11.glTranslatef(0.4F, 1.2F, 0.6F);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(0F, 0.7F, 0.5F);
                GL11.glRotatef(180, 0F, 0.5F, 0);
                break;
            case INVENTORY:
            	GL11.glTranslatef(0.0F, 0.3F, 0.0F);
            	GL11.glScalef(1.8F, 1.8F, 1.8F);
                break;
            default:
        }
        
        GL11.glPopMatrix();
    }
}
