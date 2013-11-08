package dgrxf.watercraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.WCTileEntityBuoy;

/**
 * Class ReCreated By: xandayn (Someone deleted the original) Class Last
 * Modified By: Drunk Mafia
 * 
 * Class Last Modified On: 11/08/2013 MM/DD/YYYY
 * 
 */

public class BuoyBlock extends WCBlock {
    
    public BuoyBlock(int id) {
        super(id, Material.iron);
        setCreativeTab(Watercraft.tab);
        setUnlocalizedName(BlockInfo.BOUY_UNLOCALIZED_NAME);
        setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
        setLightValue(1F);
    }
    
    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return RenderInfo.BUOY_RENDER_ID;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new WCTileEntityBuoy();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int meta) {
        // TODO: Which breaking particles should we use? -Robotic-Brain
        return Block.cloth.getIcon(0, 1);
    }
}
