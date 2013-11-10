package dgrxf.watercraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.entity.WCEntityBoat;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.WCTileEntityBuoy;

/**
 * Buoy Block
 * 
 * @author Robotic-Brain
 * @author xandayn (re-created)
 * @author Drunk Mafia (modified)
 * 
 */
public class BuoyBlock extends WCBlock {
    
    public BuoyBlock(int id) {
        super(id, Material.iron);
        setCreativeTab(Watercraft.creativeTab);
        setUnlocalizedName(BlockInfo.BUOY_UNLOCALIZED_NAME);
        setBlockBounds(0.1F, -1.3F, 0.1F, 0.9F, 0.9F, 0.9F);
        setLightValue(1F);
        setCanRotate(true);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
    	WCTileEntityBuoy tile = (WCTileEntityBuoy) world.getBlockTileEntity(x, y, z);
    	if(tile.blockBounds){
    		setBlockBounds(0.1F, -1.3F, 0.1F, 0.9F, 0.9F, 0.9F);
        	System.out.println("set bounds");
    	}else{
    		setBlockBounds(0F, 0F, 0F, 0F, 0F, 0F);
        	System.out.println("set bounds off");
    	}
    }
    
    /*@Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    	if(world.isRemote) return;
    	WCTileEntityBuoy tile = (WCTileEntityBuoy) world.getBlockTileEntity(x, y, z);
    	if(tile != null && entity instanceof WCEntityBoat){
    		tile.blockBounds = false;
    	}
    }*/
    
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
        return Block.cloth.getIcon(0, 1);
    }
    
}
