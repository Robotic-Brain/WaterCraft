package dgrxf.watercraft.client.renderer.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

/**
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By:Drunk Mafia Class Last Edited On:14/08/2013 MM/DD/YYYYY
 * 
 */

public class ToolBoxRenderer extends TileEntitySpecialRenderer {

	private IModelCustom modelToolBox_closed = AdvancedModelLoader.loadModel("/assets/watercraft/models/toolbox.obj");
	private IModelCustom modelToolBox_open = AdvancedModelLoader.loadModel("/assets/watercraft/models/toolbox_open.obj");
	private IModelCustom modelPadlock_closed = AdvancedModelLoader.loadModel("/assets/watercraft/models/padlock.obj");
	private IModelCustom modelPadlock_open = AdvancedModelLoader.loadModel("/assets/watercraft/models/padlock_open.obj");
	
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTickTime) {
    	 WCTileEntityToolBox tile = (WCTileEntityToolBox) tileentity;
    	 
    	 renderToolBox(tile, x, y, z);
    	 if(tile.isLocked()) renderPadlock(tile, x, y, z);
    }
    
    public void renderToolBox(WCTileEntityToolBox tile, double x, double y, double z){
    	GL11.glPushMatrix();

        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.3F, (float) z + 0.5F);
        GL11.glRotatef(getBlockRotation(tile), 0, 1, 0);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderInfo.TOOLBOX_TEXTURE_LOCATION);
        if(tile.isOpen)
        	modelToolBox_open.renderAll();
        else
        	modelToolBox_closed.renderAll();
        
        GL11.glPopMatrix();
    }
    
    public void renderPadlock(WCTileEntityToolBox tile, double x, double y, double z){
    	GL11.glPushMatrix();
    	
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.3F, (float) z + 0.5F);
        GL11.glRotatef(getBlockRotation(tile), 0, 1, 0);
        
        if(tile.isOpen)
        	modelPadlock_open.renderAll();
        else
        	modelPadlock_closed.renderAll();
    	
    	GL11.glPopMatrix();
    }
    
    public float getBlockRotation(WCTileEntityToolBox tile){
    	float rotation = 0;
        switch (tile.worldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)-2) {
            case 0:
                rotation = 0;
                break;
            case 1:
                rotation = 180;
                break;
            case 2:
                rotation = 90;
                break;
            case 3:
                rotation = -90;
                break;
        }
        return rotation;
    }
}
