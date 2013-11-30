package dgrxf.watercraft.block;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.tileentity.WCTileEntityCrane;

public class CraneBlock extends DirectionalBlock{

	public CraneBlock(int par1) {
		super(par1, Material.rock);
        setCreativeTab(Watercraft.buoyTab);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }
        
        FMLNetworkHandler.openGui(player, Watercraft.instance, GuiHandler.CRANE_GUI_ID, world, x, y, z);
        return true;
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