package dgrxf.watercraft.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.tileentity.WCTileEntityLockAssembler;

public class LockAssemblerBlock extends BlockContainer {

	protected LockAssemblerBlock(int id) {
		super(id, Material.iron);
		setCreativeTab(Watercraft.miscTab);
        setUnlocalizedName(BlockInfo.LOCK_ASSEMBLER_UNLOCALIZED_NAME);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new WCTileEntityLockAssembler();
	}

}
