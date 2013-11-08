package dgrxf.watercraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

public class ToolBoxBlock extends WCBlock {

	public ToolBoxBlock() {
		super(BlockInfo.TOOLBOX_ID, Material.iron);
	}
	
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new WCTileEntityToolBox();
	}

}
