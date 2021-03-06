package dgrxf.watercraft.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.buoy.BuoyBlock;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.lib.RenderInfo;

/**
 * 
 * ControlBlockDock
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ControlBlockDock extends BuoyBlock {
    
    public ControlBlockDock(int id) {
        super(id);
        setCreativeTab(Watercraft.buoyTab);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    @Override
    public int getRenderType() {
        return RenderInfo.CONTROL_UNIT_RENDER_ID;
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
        //return new WCTileEntityControlUnitDock();
    	return null;
    }
}
