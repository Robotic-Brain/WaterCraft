package dgrxf.watercraft.block;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiHandler;
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
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, Watercraft.instance, GuiHandler.LOCK_ASSEMBLER_GUI_ID, world, x, y, z);
		}
		return true;
	}

}
