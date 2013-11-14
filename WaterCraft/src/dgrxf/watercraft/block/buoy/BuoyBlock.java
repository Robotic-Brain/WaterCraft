package dgrxf.watercraft.block.buoy;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.DirectionalBlock;
import dgrxf.watercraft.entity.WCEntityBoat;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.buoy.WCTileEntityBuoy;

/**
 * Buoy Block
 * 
 * @author Robotic-Brain
 * @author xandayn (re-created)
 * @author Drunk Mafia (modified)
 * 
 */
public class BuoyBlock extends DirectionalBlock {
    
    public BuoyBlock(int id) {
        super(id, Material.iron);
        setCreativeTab(Watercraft.buoyTab);
        setUnlocalizedName(BlockInfo.BUOY_UNLOCALIZED_NAME);
        setBlockBounds(0.1F, -1.3F, 0.1F, 0.9F, 0.9F, 0.9F);
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
    
    @Override
    public boolean canProvidePower() {
    	return true;
    }
    
    @Override
    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side) {
    	WCTileEntityBuoy tile = (WCTileEntityBuoy) world.getBlockTileEntity(x, y, z);
    	int val = tile.getRedstoneSignal();
    	tile.setRedstoneSignal(false);
    	return val;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int meta) {
        return Block.cloth.getIcon(0, 1);
    }
    
    @SideOnly(Side.CLIENT)
    private Icon particleIcon;
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
    	particleIcon = register.registerIcon("Watercraft:buoyParticle");
    }
    
    @SideOnly(Side.CLIENT)
    public Icon getParticleIcon() {
        return particleIcon;
    }
    
    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
        if (!(entity instanceof WCEntityBoat)) {
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        }
    }
    
}
