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
 * Class Last Edited By:Drunk Mafia Class Last Edited On:11/08/2013 MM/DD/YYYYY
 * 
 */

public class ToolBoxRenderer extends TileEntitySpecialRenderer {
<<<<<<< HEAD
    private IModelCustom modelBuoy = AdvancedModelLoader.loadModel("/assets/watercraft/models/toolbox.obj");
=======
	private IModelCustom modelToolBox_closed = AdvancedModelLoader.loadModel("/assets/watercraft/models/toolbox.obj");
	private IModelCustom modelToolBox_open = AdvancedModelLoader.loadModel("/assets/watercraft/models/toolbox_open.obj");
>>>>>>> Commit
    
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTickTime) {
        GL11.glPushMatrix();
        
        WCTileEntityToolBox tile = (WCTileEntityToolBox) tileentity;
        
        float rotation = 0;
        switch (tileentity.worldObj.getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord)-2) {
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
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.3F, (float) z + 0.5F);
        GL11.glRotatef(rotation, 0, 1, 0);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderInfo.TOOLBOX_TEXTURE_LOCATION);
        if(tile.isOpen)
        	modelToolBox_open.renderAll();
        else
        	modelToolBox_closed.renderAll();
        
        GL11.glPopMatrix();
    }
}
