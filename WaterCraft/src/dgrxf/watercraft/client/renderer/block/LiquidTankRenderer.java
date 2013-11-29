package dgrxf.watercraft.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.WCTileEntityLiquidStorageTank;

public class LiquidTankRenderer implements ISimpleBlockRenderingHandler{
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		
		Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        block.setBlockBounds(0.0f, 0.0f, 0.0f, 1f, 1f, 1f);
        renderer.setRenderBoundsFromBlock(block);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
        tessellator.draw();
        
        block.setBlockBounds(0.125f, 0.125f, 0.125f, 0.875f, 0.875f, 0.875f);
        renderer.setRenderBoundsFromBlock(block);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 1));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 1));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 1));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 1));
        tessellator.draw();
        
		block.setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator.instance.setColorOpaque_F(1.0f, 1.0f, 1.0f);

		
		if(world.getBlockTileEntity(x, y, z) instanceof WCTileEntityLiquidStorageTank){
			WCTileEntityLiquidStorageTank te = (WCTileEntityLiquidStorageTank)world.getBlockTileEntity(x, y, z);
			FluidStack flu = te.tank.getFluid();
			Icon icon = block.getIcon(0, 1);
			
			block.setBlockBounds(0, 0, 0, 1, 1, 1);
			renderer.setRenderBoundsFromBlock(block);
			renderer.renderStandardBlock(block, x, y, z);

			block.setBlockBounds(0.125f, 0.125f, 0.125f, 0.875f, 0.875f, 0.875f);
			renderer.setRenderBoundsFromBlock(block);
			Tessellator.instance.setColorOpaque_F(1.0f, 1.0f, 1.0f);
			renderer.renderFaceYPos(block, x, y, z, block.getIcon(0, 0));
			renderer.renderFaceZPos(block, x, y, z, icon);
			renderer.renderFaceXPos(block, x, y, z, icon);
			renderer.renderFaceYNeg(block, x, y, z, block.getIcon(0, 0));
			renderer.renderFaceZNeg(block, x, y, z, icon);
			renderer.renderFaceXNeg(block, x, y, z, icon);
			block.setBlockBounds(0, 0, 0, 1, 1, 1);
			

			if(flu != null){
				ItemStack stack = new ItemStack(flu.getFluid().getBlockID(), 1, 0);
				if(stack.getIconIndex() != null)
					icon = stack.getIconIndex();
				
				float amount = (((float)te.tank.getFluidAmount() / (float)te.tank.getCapacity()) * 0.74F) + 0.126F;
				block.setBlockBounds(0.126f, 0.126f, 0.126f, 0.874f, amount, 0.874f);
				renderer.setRenderBoundsFromBlock(block);
				Tessellator.instance.setColorOpaque_F(1.0f, 1.0f, 1.0f);
				renderer.renderFaceYPos(block, x, y, z, icon);
				renderer.renderFaceZPos(block, x, y, z, icon);
				renderer.renderFaceXPos(block, x, y, z, icon);
				renderer.renderFaceYNeg(block, x, y, z, icon);
				renderer.renderFaceZNeg(block, x, y, z, icon);
				renderer.renderFaceXNeg(block, x, y, z, icon);
				
			}
			block.setBlockBounds(0, 0, 0, 1, 1, 1);
		}
		
		return true;
		
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderInfo.TANK_RENDER_ID;
	}
}
