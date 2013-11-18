package dgrxf.watercraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import dgrxf.watercraft.util.RotationHelper;

public abstract class DirectionalBlock extends WCBlock {
    
    public DirectionalBlock(int id, Material material) {
        super(id, material);
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemstack) {
        if (world.isRemote) {
            return;
        }
        
        setBlockDirection(world, x, y, z, RotationHelper.yawToForge(player.rotationYaw));
    }
    
    /**
     * Gets the direction of the Block
     * 
     * @return direction
     */
    public ForgeDirection getBlockDirection(World world, int x, int y, int z) {
        return ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
    }
    
    /**
     * Sets the direction of the Block UP/DOWN not allowed
     * 
     * @param world
     * @param x
     * @param y
     * @param z
     * @param direction
     */
    public void setBlockDirection(World world, int x, int y, int z, ForgeDirection d) {
        if (d == ForgeDirection.NORTH || d == ForgeDirection.SOUTH || d == ForgeDirection.WEST || d == ForgeDirection.EAST) {
            
            world.setBlockMetadataWithNotify(x, y, z, d.ordinal(), 3); // Block Update 3=On | 2=Off
        }
    }
}
