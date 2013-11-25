package dgrxf.watercraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.tileentity.WCTileEntityCrane;

public class CraneBlock extends DirectionalBlock{

	public CraneBlock(int par1, Material par2Material) {
		super(par1, par2Material);
        setCreativeTab(Watercraft.buoyTab);
        setUnlocalizedName(BlockInfo.CONTROL_UNIT_DOCK_UNLOCALIZED_NAME);
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
