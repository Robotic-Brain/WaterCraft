package dgrxf.watercraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.interactions.ComputerCraft.tileentity.WCCCTileEntityBoatAssembler;
import dgrxf.watercraft.tileentity.WCTileEntityBoatAssembler;

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
		if(!Watercraft.hasCC)
			return new WCTileEntityBoatAssembler();
		else
			return new WCCCTileEntityBoatAssembler();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote){
			player.openGui(Watercraft.instance, GuiHandler.BOAT_ASSEMBLER_BLOCK_GUI_ID, world, x, y, z);
		}
		return true;
	}
	
}