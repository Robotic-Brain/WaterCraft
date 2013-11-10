package dgrxf.watercraft.client.renderer.block;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.lib.RenderInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By:Drunk Mafia Class Last Edited On:11/08/2013 MM/DD/YYYYY
 * 
 */

public class ToolBoxRenderer extends TileEntitySpecialRenderer {
    private IModelCustom modelBuoy = AdvancedModelLoader.loadModel("/assets/watercraft/models/toolbox.obj");
    
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTickTime) {
        GL11.glPushMatrix();
        
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.3F, (float) z + 0.5F);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderInfo.TOOLBOX_TEXTURE_LOCATION);
        modelBuoy.renderAll();
        
        GL11.glPopMatrix();
    }
}
