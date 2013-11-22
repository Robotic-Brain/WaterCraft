package dgrxf.watercraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.models.WCModelChest;
import dgrxf.watercraft.client.renderer.block.WCChestRenderer;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.interfaces.ICustomBoatTexture;
import dgrxf.watercraft.lib.EntityInfo;
import dgrxf.watercraft.util.Vector3;

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
        	if(block != ModBlocks.chest){
                this.bindTexture(TextureMap.locationBlocksTexture);
                GL11.glTranslatef(0.0F, 6 / 16.0F, 0.0F);
	            this.renderBlockInBoat(entity, par9, block, 0);
        	}else if(entity.getDisplayTile() == ModBlocks.chest){
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
        /*if(entity instanceof WCEntityBoat && ((WCEntityBoat)entity).getFlagColor() != Colours.none){
        	renderFlag(((WCEntityBoat)entity), par2, par4, par6, par8, par9);
        }*/
        
        renderRope(entity, par2, par4, par6, par8, par9);
        
        GL11.glPopMatrix();
    }
    
    protected void renderBlockInBoat(AbstractBaseBoat entity, float par2, Block par3Block, int par4) {
        float f1 = entity.getBrightness(par2);
        GL11.glPushMatrix();
        this.renderBlocks.renderBlockAsItem(par3Block, par4, f1);
        if(par3Block == ModBlocks.tank){
        	renderLiquidInTank(entity, par3Block);
        }
        GL11.glPopMatrix();
    }
    
    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime) {
        renderBoat((AbstractBaseBoat) entity, x, y, z, yaw, partialTickTime);
    }
    
    private IModelCustom flagModel = AdvancedModelLoader.loadModel("/assets/watercraft/models/Flag.obj");
    
    /*private void renderFlag(WCEntityBoat boat, double x, double y, double z, float yaw, float partialTickTime) {
    	GL11.glPushMatrix();
    	Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.MODID, RenderInfo.FLAG_TEXTURE_LOCATION + (boat.getFlagColor().ordinal() + 1) + ".png"));
    	GL11.glTranslatef(0.7F, -0.2F, -0.55F);
    	GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
    	GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
    	flagModel.renderAll();
    	GL11.glPopMatrix();		
    }*/
    
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
			int amount = entity.getDataWatcher().getWatchableObjectInt(EntityInfo.DATAWATCHER_TANK_AMOUNT) / 1000;
			switch(amount){
			case 0:
				block.setBlockBounds(0, 0, 0, 0, 0, 0);
				break;
			case 1:
				block.setBlockBounds(0.126f, 0.126f, 0.126f, 0.874f, 0.21875f, 0.874f);
				break;
			case 2:
				block.setBlockBounds(0.126f, 0.126f, 0.126f, 0.874f, 0.3125f, 0.874f);
				break;
			case 3:
				block.setBlockBounds(0.126f, 0.126f, 0.126f, 0.874f, 0.40625f, 0.874f);
				break;
			case 4:
				block.setBlockBounds(0.126f, 0.126f, 0.126f, 0.874f, 0.5f, 0.874f);
				break;
			case 5:
				block.setBlockBounds(0.126f, 0.126f, 0.126f, 0.874f, 0.59375f, 0.874f);
				break;
			case 6:
				block.setBlockBounds(0.126f, 0.126f, 0.126f, 0.874f, 0.6875f, 0.874f);
				break;
			case 7: 
				block.setBlockBounds(0.126f, 0.126f, 0.126f, 0.874f, 0.78125f, 0.874f);
				break;
			case 8:
				block.setBlockBounds(0.126f, 0.126f, 0.126f, 0.874f, 0.874f, 0.874f);
				break;
			}
			
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
    	
    	System.out.println("Rendering ROPE");
    	
    	Tessellator tessellator = Tessellator.instance;
    	
    	double boatX = boat.prevPosX + (boat.posX - boat.prevPosX) * partialTick; 
    	double boatY = boat.prevPosY + (boat.posY - boat.prevPosY) * partialTick;
    	double boatZ = boat.prevPosZ + (boat.posZ - boat.prevPosZ) * partialTick;
    	
    	Entity e = boat.worldObj.getEntityByID(id);
    	
    	if (e instanceof AbstractBaseBoat) {
    		AbstractBaseBoat target = (AbstractBaseBoat)e;
    		
    		double targetX = target.prevPosX + (target.posX - target.prevPosX) * partialTick + target.width * Math.cos(target.rotationYaw * Math.PI / 180.0) / 2.0F - boatX; 
        	double targetY = target.prevPosY + (target.posY - target.prevPosY) * partialTick - target.height * 0.25 - boatY;
        	double targetZ = target.prevPosZ + (target.posZ - target.prevPosZ) * partialTick + target.width * Math.sin(target.rotationYaw * Math.PI / 180.0) / 2.0F - boatZ;
    		
        	System.out.println(new Vector3(boatX, boatY, boatZ).toString());
        	System.out.println(new Vector3(targetX, targetY, targetZ).toString());
        	
        	GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            tessellator.startDrawing(3);
            tessellator.setColorOpaque_I(0);
            
            double relativeBoatX = - boat.width * 0.5;
            double relativeBoatY = - boat.height * 0.25;
            double relativeBoatZ = 0;
            
            byte vertices = 16;

            for (int i = 0; i <= vertices; i++)
            {
                double perc = (double)i / (double)vertices;
            	tessellator.setColorRGBA_F((float)perc, (float)perc, (float)perc, 1.0F);
                tessellator.addVertex(relativeBoatX + (targetX - relativeBoatX) * perc, relativeBoatY - (targetY - relativeBoatY) * perc, relativeBoatZ - (targetZ - relativeBoatZ) * perc);
            }

            tessellator.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        	
    	} else {
    		return;
    	}
    		
    }
    
}
