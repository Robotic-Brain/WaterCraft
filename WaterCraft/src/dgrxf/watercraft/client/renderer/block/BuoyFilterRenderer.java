package dgrxf.watercraft.client.renderer.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.lib.ModInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.buoy.WCTileEntityFilterBuoy;

public class BuoyFilterRenderer extends TileEntitySpecialRenderer {
    
    private IModelCustom modelBuoy = AdvancedModelLoader.loadModel("/assets/watercraft/models/buoy.obj");
    
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTickTime) {
    	renderBouy(x, y, z);
    	for(int i = 0; i < 4; i++){
    		if(((WCTileEntityFilterBuoy) tileentity).directions[i] != null){
    			renderFlags((WCTileEntityFilterBuoy) tileentity, x, y, z, i);
    		}
    	}
    }
    
    public void renderBouy(double x, double y, double z){
    	GL11.glPushMatrix();
        
        GL11.glTranslatef((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderInfo.BUOY_TEXTURE_LOCATION);
        
        //GL11.glColor4f(1f, 1f, 1f, 0.2f);
        
        modelBuoy.renderAll();
        
        //GL11.glColor4f(1f, 1f, 1f, 1f);
        
        GL11.glPopMatrix();
    }
    
    private IModelCustom flag = AdvancedModelLoader.loadModel("/assets/watercraft/models/Flag.obj");
    
    public void renderFlags(WCTileEntityFilterBuoy tile, double x, double y, double z, int direction){
    	GL11.glPushMatrix();
    	GL11.glRotatef((90 * direction), 0f, 1f, 0f);   // Directional rotation
    	GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
    	GL11.glTranslatef(-0.5f, 0f, 0f);
    	GL11.glRotatef(30f, 0f, 0f, 1f);   // Flag rotation
   			
	    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.MODID, RenderInfo.FLAG_TEXTURE_LOCATION + (tile.directions[direction].ordinal() + 1) + ".png"));
	    flag.renderAll();
    	
    	GL11.glPopMatrix();
    }
}
