package dgrxf.watercraft.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.WCTileEntityControlUnitDock;

public class ControlBlockDock extends DirectionalBlock {
    
    public ControlBlockDock(int id) {
        super(id, Material.iron);
        setCreativeTab(Watercraft.buoyTab);
        setUnlocalizedName(BlockInfo.CONTROL_UNIT_DOCK_UNLOCALIZED_NAME);
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return RenderInfo.CONTROL_UNIT_RENDER_ID;
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    	if (world.isRemote) return true;

		FMLNetworkHandler.openGui(player, Watercraft.instance, GuiHandler.CONTROLUNIT_GUI_ID, world, x, y, z); 
		return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new WCTileEntityControlUnitDock();
    }
}
