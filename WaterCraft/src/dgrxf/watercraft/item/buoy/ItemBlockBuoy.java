package dgrxf.watercraft.item.buoy;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.tileentity.buoy.WCTileEntityBuoy;
import dgrxf.watercraft.util.RotationHelper;

/**
 * 
 * ItemBlockBuoy
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ItemBlockBuoy extends ItemBlock {
    
    public ItemBlockBuoy(int id) {
        super(id);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
        
        if (movingobjectposition == null) {
            return itemStack;
        } else {
            if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
                
                int x = movingobjectposition.blockX;
                int y = movingobjectposition.blockY;
                int z = movingobjectposition.blockZ;
                
                if (!world.canMineBlock(player, x, y, z)) {
                    return itemStack;
                }
                if (!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, itemStack)) {
                    return itemStack;
                }
                
                if (world.getBlockMaterial(x, y, z) == Material.water && world.getBlockMetadata(x, y, z) == 0 && world.isAirBlock(x, y + 1, z)) {
                    world.setBlock(x, y + 1, z, ModBlocks.BUOY.getId());
                    
                    ((WCTileEntityBuoy) world.getBlockTileEntity(x, y + 1, z)).setBlockDirection(RotationHelper.yawToForge(player.rotationYaw));
                    
                    if (!player.capabilities.isCreativeMode) {
                        --itemStack.stackSize;
                    }
                }
            }
            
            return itemStack;
        }
    }
    
    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        return false;
    }
    
}
