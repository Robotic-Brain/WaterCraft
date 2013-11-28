package dgrxf.watercraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.tileentity.WCTileEntityCrane;

public class CraneBlock extends DirectionalBlock{

	public CraneBlock(int par1) {
		super(par1, Material.rock);
        setCreativeTab(Watercraft.buoyTab);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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