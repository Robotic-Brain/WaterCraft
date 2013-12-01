package dgrxf.watercraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.tileentity.WCTileEntityBoatAssembler;

/**
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class BoatModuleAssemblerBlock extends Block{

	public BoatModuleAssemblerBlock(int id) {
		super(id, Material.rock);
		setCreativeTab(Watercraft.miscTab);
	}
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new WCTileEntityBoatAssembler();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote){
			player.openGui(Watercraft.instance, GuiHandler.BOAT_ASSEMBLER_BLOCK_GUI_ID, world, x, y, z);
		}
		return true;
	}
	
}