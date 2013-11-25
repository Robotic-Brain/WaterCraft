package dgrxf.watercraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.tileentity.WCTileEntityCrane;

public class CraneBlock extends DirectionalBlock{

	public CraneBlock(int par1, Material par2Material) {
		super(par1, par2Material);
	}
	
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new WCTileEntityCrane();
	}
	
}
