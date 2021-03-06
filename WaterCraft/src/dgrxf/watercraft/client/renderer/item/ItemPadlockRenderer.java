package dgrxf.watercraft.client.renderer.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import dgrxf.watercraft.lib.RenderInfo;

/**
 * 
 * ItemPadlockRenderer
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ItemPadlockRenderer implements IItemRenderer {
    
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
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(0F, 0.7F, 0.5F);
                GL11.glRotatef(180, 0F, 1F, 0);
                break;
            case INVENTORY:
                GL11.glTranslatef(-2.0F, -2.0F, 0.0F);
                GL11.glRotatef(180, 0.0F, 0.0F, 0.0F);
                break;
            default:
        }
        GL11.glScalef(5F, 5F, 5F);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(RenderInfo.PADLOCK_TEXTURE_LOCATION);
        modelPadlock.renderAll();
        
        GL11.glPopMatrix();
    }
    
}
