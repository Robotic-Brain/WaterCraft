package dgrxf.watercraft.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.entity.WCEntityBoat;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.WCTileEntityFilterBuoy;

public class BuoyFilterBlock extends DirectionalBlock {
    
    public BuoyFilterBlock(int id) {
        super(id, Material.iron);
        setCreativeTab(Watercraft.creativeTab);
        setUnlocalizedName(BlockInfo.BUOY_UNLOCALIZED_NAME + "Filter");
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
        return new WCTileEntityFilterBuoy();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int meta) {
        return Block.cloth.getIcon(0, 1);
    }
    
    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
        if (!(entity instanceof WCEntityBoat)) {
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        }
    }
    
}