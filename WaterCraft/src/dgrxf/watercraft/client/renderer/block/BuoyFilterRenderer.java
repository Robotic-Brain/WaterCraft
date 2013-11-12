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
    	renderFlags((WCTileEntityFilterBuoy) tileentity, x, y, z);
    }
    
    public void renderBouy(double x, double y, double z){
    	GL11.glPushMatrix();
        
        GL11.glTranslatef((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderInfo.BUOY_TEXTURE_LOCATION);
        
        modelBuoy.renderAll();
        
        GL11.glPopMatrix();
    }
    
    private IModelCustom flag = AdvancedModelLoader.loadModel("/assets/watercraft/models/Flag.obj");
    
    public void renderFlags(WCTileEntityFilterBuoy tile, double x, double y, double z){
    	GL11.glPushMatrix();
    	
    	GL11.glTranslatef((float) x + 0.5F, (float) y - 2F, (float) z + 0.5F);
    	
    	for(int i = 0; i < 4; i++){
    		if(tile.directions[i] != null){
	    		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.MODID, RenderInfo.FLAG_TEXTURE_LOCATION + (tile.directions[i].ordinal() + 1) + ".png"));
	    		flag.renderAll();
	    		System.out.println("RENDER!");
    		}
    	}
    	
    	GL11.glPopMatrix();
    }
}
