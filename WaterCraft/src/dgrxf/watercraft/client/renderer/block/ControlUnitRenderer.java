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
 * ControlUnitRenderer
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ControlUnitRenderer extends TileEntitySpecialRenderer {
    private IModelCustom modelUnit = AdvancedModelLoader.loadModel("/assets/watercraft/models/controlunit.obj");
    
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTickTime) {
        GL11.glPushMatrix();
        
        float rotation = 0;
        switch (tileentity.worldObj.getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) - 2) {
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
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        GL11.glRotatef(rotation, 0, 1, 0);
        GL11.glScalef(0.083f, 0.083f, 0.083f);
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderInfo.CONTROL_UNIT_TEXTURE_LOCATION);
        modelUnit.renderAll();
        
        GL11.glPopMatrix();
    }
}
