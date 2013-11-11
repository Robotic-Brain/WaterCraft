package dgrxf.watercraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.entity.WCEntityBoat;
import dgrxf.watercraft.entity.WCEntityBoatBase;
import dgrxf.watercraft.enumeration.Colours;
import dgrxf.watercraft.lib.ModInfo;
import dgrxf.watercraft.lib.RenderInfo;

public class WCBoatRenderer extends Render {
    private static final ResourceLocation boatTextures = new ResourceLocation("textures/entity/boat.png");
    
    ModelBase                             model;
    protected final RenderBlocks renderBlock;
    
    public WCBoatRenderer() {
        model = new ModelBoat();
        shadowSize = 0.5F;
        renderBlock = new RenderBlocks();
    }
    
    public void renderBoat(WCEntityBoatBase entity, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) par2, (float) par4, (float) par6);
        GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
        
        float f2 = (float) entity.getTimeSinceHit() - par9;
        float f3 = entity.getDamageTaken() - par9;
        
        if (f3 < 0.0F) {
            f3 = 0.0F;
        }
        
        if (f2 > 0.0F) {
            GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0F * (float) entity.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }
        
        Block block = entity.getDisplayTile();
        
        if (block != null)
        {
            GL11.glPushMatrix();
            this.bindTexture(TextureMap.locationBlocksTexture);
            float f8 = 1F;
            GL11.glScalef(f8, f8, f8);
            GL11.glTranslatef(0.0F, (float)6 / 16.0F, 0.0F);
            this.renderBlockInBoat(entity, par9, Block.chest, 0);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
        
        float f4 = 0.75F;
        GL11.glScalef(f4, f4, f4);
        GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
        
        bindEntityTexture(entity);
        
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
    
    protected void renderBlockInBoat(WCEntityBoatBase entity, float par2, Block par3Block, int par4)
    {
        float f1 = entity.getBrightness(par2);
        GL11.glPushMatrix();
        this.renderBlocks.renderBlockAsItem(par3Block, par4, f1);
        GL11.glPopMatrix();
    }

	@Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime) {
        renderBoat((WCEntityBoatBase) entity, x, y, z, yaw, partialTickTime);
        if(entity instanceof WCEntityBoat && ((WCEntityBoat)entity).flag != null){
        	renderFlag(((WCEntityBoat)entity), x, y, z, yaw, partialTickTime);
        }
    }
	
	private IModelCustom flagModel = AdvancedModelLoader.loadModel("/assets/watercraft/models/Flag.obj");
    
    private void renderFlag(WCEntityBoat boat, double x, double y, double z, float yaw, float partialTickTime) {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.MODID, RenderInfo.FLAG_TEXTURE_LOCATION + (boat.flag.ordinal() + 1) + ".png"));
		GL11.glTranslatef((float)x + 0.5F,(float)y + 0.2F,(float)z + 0.5F);
		GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
        
        float f2 = (float) boat.getTimeSinceHit() - yaw;
        float f3 = boat.getDamageTaken() - yaw;
        
        if (f3 < 0.0F) {
            f3 = 0.0F;
        }
        
        if (f2 > 0.0F) {
            GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0F * (float) boat.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }
		flagModel.renderAll();
		
		GL11.glPopMatrix();		
	}

	@Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return boatTextures;
    }
    
}
