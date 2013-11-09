package dgrxf.watercraft.block;

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
import dgrxf.watercraft.util.RotationHelper;

public class ControlBlockDock extends WCBlock implements ITileEntityProvider {
    
    public ControlBlockDock(int id) {
        super(id, Material.iron);
        setCreativeTab(Watercraft.creativeTab);
        setUnlocalizedName(BlockInfo.CONTROL_UNIT_DOCK_UNLOCALIZED_NAME);
        setCanRotate(true);
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
        return RenderInfo.CONTROL_UNIT_RENDER_ID;
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemstack) {
        super.onBlockPlacedBy(world, x, y, z, player, itemstack);
        
        if (world.isRemote)
            return;
        
        ((WCTileEntityControlUnitDock) world.getBlockTileEntity(x, y, z)).setDirection(RotationHelper.yawToForge(player.rotationYaw));
        //LogHelper.debug(RotationHelper.yawToForge(player.rotationYaw));
    }
    
    @Override
    public TileEntity createNewTileEntity(World world) {
        return new WCTileEntityControlUnitDock();
    }
}
