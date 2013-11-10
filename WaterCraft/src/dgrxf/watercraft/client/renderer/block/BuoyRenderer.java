package dgrxf.watercraft.client.renderer.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.lib.RenderInfo;

/**
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By:Drunk Mafia Class Last Edited On:11/07/2013 MM/DD/YYYYY
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
        GL11.glPushMatrix();
        
        // If this would be used, maybe control it from the TileEntity
        /*
         * tickTick++; if(tickTick % 4 == 0) tick++; if(up){ yLevel += 0.01F;
         * if(tick > 16){ up = false; } }else if(!up){ yLevel -= 0.01F; if(tick
         * > 32){ up = true; tick = -1; yLevel = 0; tickTick = 0; tick = 0; } }
         */
        
        GL11.glTranslatef((float) x + 0.5F, (float) y - 0.5F + yLevel, (float) z + 0.5F);
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderInfo.BUOY_TEXTURE_LOCATION);
        
        modelBuoy.renderAll();
        
        GL11.glPopMatrix();
    }
}
