package dgrxf.watercraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.WCTileEntityControlUnitDock;

public class ControlBlockDock extends WCBlock implements ITileEntityProvider {
    
    public ControlBlockDock(int id) {
        super(id, Material.iron);
        setCreativeTab(Watercraft.tab);
        setUnlocalizedName(BlockInfo.CONTROL_UNIT_DOCK_UNLOCALIZED_NAME);
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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemstack) {
        super.onBlockPlacedBy(world, x, y, z, player, itemstack);
        
        if (world.isRemote)
            return;
        
        WCTileEntityControlUnitDock tile = (WCTileEntityControlUnitDock) world.getBlockTileEntity(x, y, z);
        
        // int rotate = ((MathHelper.floor_double((double)(player.rotationYaw *
        // 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
        // world.setBlockMetadataWithNotify(x, y, z, rotate, 3);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world) {
        return new WCTileEntityControlUnitDock();
    }
}
