package dgrxf.watercraft.client.renderer.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.lib.RenderInfo;

/**
 * 
 * BuoyRenderer
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class BuoyRenderer extends TileEntitySpecialRenderer {
    
    private IModelCustom modelBuoy = AdvancedModelLoader.loadModel("/assets/watercraft/models/buoy.obj");
    private int          tick      = 0;
    private int          tickTick  = 0;
    private float        yLevel    = 0;
    private boolean      up        = true;
    
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTickTime) {
        renderBouy(x, y, z);
    }
    
    public void renderBouy(double x, double y, double z) {
        GL11.glPushMatrix();
        
        GL11.glTranslatef((float) x + 0.5F, (float) y - 0.5F + yLevel, (float) z + 0.5F);
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderInfo.BUOY_TEXTURE_LOCATION);
        
        modelBuoy.renderAll();
        
        GL11.glPopMatrix();
    }
}
