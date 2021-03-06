package dgrxf.watercraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.models.WCModelChest;
import dgrxf.watercraft.client.renderer.block.WCChestRenderer;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.interfaces.ICustomBoatTexture;
import dgrxf.watercraft.lib.EntityInfo;

/**
 * 
 * WCBoatRenderer
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class WCBoatRenderer extends Render {
    private static final ResourceLocation boatTextures = new ResourceLocation("textures/entity/boat.png");
    
    ModelBase                             model;
    WCModelChest					      chest;
    protected final RenderBlocks          renderBlock;
    
    public WCBoatRenderer() {
    	chest = new WCModelChest();
        model = new ModelBoat();
        shadowSize = 0.5F;
        renderBlock = new RenderBlocks();
    }
    
    public void renderBoat(AbstractBaseBoat entity, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) par2, (float) par4, (float) par6);
        GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
        
        float f2 = entity.getTimeSinceHit() - par9;
        float f3 = entity.getDamageTaken() - par9;
        
        if (f3 < 0.0F) {
            f3 = 0.0F;
        }
        
        if (f2 > 0.0F) {
            GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0F * entity.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }

        Block block = entity.getDisplayTile();
        if (block != null) {
            GL11.glPushMatrix();
            float f8 = 1F;
            GL11.glScalef(f8, f8, f8);
        	if(block != ModBlocks.WC_CHEST.getBlock()){
                this.bindTexture(TextureMap.locationBlocksTexture);
                GL11.glTranslatef(0.0F, 6 / 16.0F, 0.0F);
	            this.renderBlockInBoat(entity, par9, block, 0, (float)par2, (float)par4, (float)par6);
        	}else if(entity.getDisplayTile() == ModBlocks.WC_CHEST.getBlock()){
                GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(-0.5F, -0.85F, -0.5F);
                Minecraft.getMinecraft().renderEngine.bindTexture(WCChestRenderer.RES_NORMAL_SINGLE);
	            if(entity.getDataWatcher().getWatchableObjectByte(EntityInfo.DATAWATCHER_CHEST_LOCK) == 1){
	        		chest.renderAll(true);
	        	}
	        	else if(entity.getDataWatcher().getWatchableObjectByte(EntityInfo.DATAWATCHER_CHEST_LOCK) == 0){
	        		chest.renderAll(false);
	        	}
                else{
                	chest.renderAll(false);
                }
        	}

            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
        
        float f4 = 0.75F;
        GL11.glScalef(f4, f4, f4);
        GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
        
        if(entity instanceof ICustomBoatTexture){
            Minecraft.getMinecraft().renderEngine.bindTexture(((ICustomBoatTexture)entity).getCustomTexture());
        }else{
            Minecraft.getMinecraft().renderEngine.bindTexture(boatTextures);
        }
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        renderRope(entity, par2, par4, par6, par8, par9);
        GL11.glPopMatrix();
    }
    
    protected void renderBlockInBoat(AbstractBaseBoat entity, float par2, Block par3Block, int par4, float x, float y, float z) {
        float f1 = entity.getBrightness(par2);
        GL11.glPushMatrix();
        RenderManager.instance.itemRenderer.renderItem(null, new ItemStack(par3Block), 10);
        if(par3Block == ModBlocks.TANK.getBlock()){
        	renderLiquidInTank(entity, par3Block);
        }
        GL11.glPopMatrix();
    }
    
    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime) {
        renderBoat((AbstractBaseBoat) entity, x, y, z, yaw, partialTickTime);
    }
    
    private void renderLiquidInTank(AbstractBaseBoat entity, Block block){
    	FluidStack flu = null;
    	if(entity.getDataWatcher().getWatchableObjectString(EntityInfo.DATAWATCHER_LIQUID_NAME) != "none"){
    		if(FluidRegistry.getFluid(entity.getDataWatcher().getWatchableObjectString(EntityInfo.DATAWATCHER_LIQUID_NAME)) != null){
    			flu = FluidRegistry.getFluidStack(FluidRegistry.getFluid(entity.getDataWatcher().getWatchableObjectString(EntityInfo.DATAWATCHER_LIQUID_NAME)).getName(), 1);
    		}
    	}
    	
    	Icon icon = block.getIcon(0, 0);
		Tessellator tessellator = Tessellator.instance;
		if(flu != null){
			ItemStack stack = new ItemStack(flu.getFluid().getBlockID(), 1, 0);
			if(stack.getIconIndex() != null)
				icon = stack.getIconIndex();
			float amount = entity.getDataWatcher().getWatchableObjectInt(EntityInfo.DATAWATCHER_TANK_AMOUNT);
			float maxAmount = entity.getDataWatcher().getWatchableObjectInt(EntityInfo.DATAWATCHR_TANK_MAX);
			float temp = ((amount) / maxAmount * 0.74F) + 0.126F;
			if(temp > 0.126F){
				block.setBlockBounds(0.126f, 0.126f, 0.126f, 0.874f, temp, 0.874f);
				GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
				
				renderBlock.setRenderBoundsFromBlock(block);
		        tessellator.startDrawingQuads();
		        tessellator.setNormal(0.0F, 1F, 0.0F);
				renderBlock.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
		        tessellator.draw();
		        
		        tessellator.startDrawingQuads();
		        tessellator.setNormal(0.0F, 0F, 1.0F);
				renderBlock.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
		        tessellator.draw();
				
		        tessellator.startDrawingQuads();
		        tessellator.setNormal(1.0F, 0F, 0.0F);
				renderBlock.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);
		        tessellator.draw();
		        
		        tessellator.startDrawingQuads();
		        tessellator.setNormal(0.0F, -1F, 0.0F);
				renderBlock.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
		        tessellator.draw();
		        
		        tessellator.startDrawingQuads();
		        tessellator.setNormal(0.0F, 0F, -1.0F);
				renderBlock.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
		        tessellator.draw();
		        
		        tessellator.startDrawingQuads();
		        tessellator.setNormal(-1.0F, 0F, 0.0F);
				renderBlock.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
		        tessellator.draw();
			}
	        
			block.setBlockBounds(0, 0, 0, 1, 1, 1);
			
		}
		
    }
    
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return boatTextures;
    }
    
    private void renderRope(AbstractBaseBoat boat, double x, double y, double z, float yaw, float partialTick) {
    	int id = boat.getRopeTargetId();
    	
    	if (id < 0) {
    		return;
    	}
    	
    	Tessellator tessellator = Tessellator.instance;
    	
    	double boatX = boat.prevPosX + (boat.posX - boat.prevPosX) * partialTick; 
    	double boatY = boat.prevPosY + (boat.posY - boat.prevPosY) * partialTick;
    	double boatZ = boat.prevPosZ + (boat.posZ - boat.prevPosZ) * partialTick;
    	
    	Entity e = boat.worldObj.getEntityByID(id);
    	
    	if (e instanceof AbstractBaseBoat) {
    		AbstractBaseBoat target = (AbstractBaseBoat)e;
    		
    		double targetYaw = target.prevRotationYaw + (target.rotationYaw - target.prevRotationYaw) * partialTick;
    		targetYaw = targetYaw * Math.PI / 180.0;
    		
    		double tx = target.prevPosX + (target.posX - target.prevPosX) * partialTick + target.width * Math.cos(targetYaw) / 2.0F - boatX; 
        	double ty = target.prevPosY + (target.posY - target.prevPosY) * partialTick - target.height * 0.25 - boatY;
        	double tz = target.prevPosZ + (target.posZ - target.prevPosZ) * partialTick + target.width * Math.sin(targetYaw) / 2.0F - boatZ;
    		
        	double boatYaw = boat.prevRotationYaw + (boat.rotationYaw - boat.prevRotationYaw) * partialTick;
        	boatYaw = boatYaw * Math.PI / 180.0;
        	
        	double targetX = tx * Math.cos(boatYaw) + tz * Math.sin(boatYaw);
        	double targetY = ty;
        	double targetZ = - tx * Math.sin(boatYaw) + tz * Math.cos(boatYaw);
        	
            double relativeBoatX = - boat.width * 0.5;
            double relativeBoatY = - boat.height * 0.25;
            double relativeBoatZ = 0;
            
        	GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            //tessellator.setColorOpaque_I(0);
    
            byte vertices = 24;
            double ropeWidth = 0.03;
            
            tessellator.startDrawing(5);
            for (int i = 0; i <= vertices; i++) {
                double perc = (double)i / (double)vertices;
                
                if (i % 2 == 0) {
                    tessellator.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
                } else {
                    tessellator.setColorRGBA_F(0.35F, 0.28F, 0.21F, 1.0F);
                }
                
                tessellator.addVertex(relativeBoatX + (targetX - relativeBoatX) * perc, relativeBoatY - (targetY - relativeBoatY) * perc, relativeBoatZ - (targetZ - relativeBoatZ) * perc);
                tessellator.addVertex(relativeBoatX + (targetX - relativeBoatX) * perc + ropeWidth, relativeBoatY - (targetY - relativeBoatY) * perc + ropeWidth, relativeBoatZ - (targetZ - relativeBoatZ) * perc);
            }
            tessellator.draw();
            
            tessellator.startDrawing(5);
            for (int i = 0; i <= vertices; i++) {
                double perc = (double)i / (double)vertices;
                
                if (i % 2 == 0) {
                    tessellator.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
                } else {
                    tessellator.setColorRGBA_F(0.35F, 0.28F, 0.21F, 1.0F);
                }
                
                tessellator.addVertex(relativeBoatX + (targetX - relativeBoatX) * perc, relativeBoatY - (targetY - relativeBoatY) * perc + ropeWidth, relativeBoatZ - (targetZ - relativeBoatZ) * perc);
                tessellator.addVertex(relativeBoatX + (targetX - relativeBoatX) * perc + ropeWidth, relativeBoatY - (targetY - relativeBoatY) * perc, relativeBoatZ - (targetZ - relativeBoatZ) * perc + ropeWidth);
            }
            tessellator.draw();
            
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_CULL_FACE);
        	
    	} else {
    		return;
    	}
    		
    }
    
}
