package dgrxf.watercraft.block;

import java.util.Random;

import net.minecraft.block.BlockWoodSlab;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.tileentity.controlunit.WCTileEntityControlUnitDock;

public class DockPlatformBlock extends BlockWoodSlab{

	public DockPlatformBlock() {
		super(BlockInfo.PLATFORM_ID, false);
		setUnlocalizedName(BlockInfo.PLATFORM_UNLOCALIZED_NAME);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(world.isRemote) return;
		
		System.out.println("update");
		
		for(int xr = x - 1; xr == x + 2; xr++){
			for(int yr = y - 1; yr == y + 2; yr++){
				for(int zr = z - 1; zr == z + 2; zr++){
					if(world.getBlockId(xr, yr, zr) == blockID){
						System.out.println("PlatForm found");
						world.markBlockForUpdate(xr, yr, zr);
					}else if(world.getBlockId(xr, yr, zr) == ModBlocks.controlUnitDock.blockID){
						System.out.println("controlUnitFound");
						WCTileEntityControlUnitDock tile = (WCTileEntityControlUnitDock) world.getBlockTileEntity(x, y, z);
						tile.checkForMultiBlock();
					}
				}
			}
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		if(world.isRemote) return;
		
		System.out.println("neighborBlockUpdate");
		
		if(id == blockID){
			for(int xr = x - 1; xr < x + 1; xr++){
				for(int yr = y - 1; yr < y + 1; yr++){
					for(int zr = z - 1; zr < z + 1; zr++){
						if(world.getBlockId(xr, yr, zr) == blockID){
							System.out.println("PlatForm found");
							world.markBlockForUpdate(xr, yr, zr);
						}else if(world.getBlockId(xr, yr, zr) == ModBlocks.controlUnitDock.blockID){
							System.out.println("controlUnitFound");
							WCTileEntityControlUnitDock tile = (WCTileEntityControlUnitDock) world.getBlockTileEntity(x, y, z);
							tile.checkForMultiBlock();
						}
					}
				}
			}
		}
	}
}
