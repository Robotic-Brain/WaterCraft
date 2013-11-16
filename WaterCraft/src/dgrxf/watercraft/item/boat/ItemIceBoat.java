package dgrxf.watercraft.item.boat;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.entity.boat.IceBoat;
import dgrxf.watercraft.lib.ItemInfo;

public class ItemIceBoat extends Item {
    
    public ItemIceBoat() {
        super(ItemInfo.ICEBOAT_ID);
        setUnlocalizedName(ItemInfo.ICEBOAT_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.boatTab);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote) {
            return itemStack;
        }
        
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
        
        if (movingobjectposition == null) {
            return itemStack;
        } else {
            if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
                
                int x1 = movingobjectposition.blockX;
                int y1 = movingobjectposition.blockY;
                int z1 = movingobjectposition.blockZ;
                
                if ((world.getBlockMaterial(x1, y1, z1) == Material.water) && world.isAirBlock(x1, y1 + 1, z1)) {
                    IceBoat boat = new IceBoat(world, x1, y1 + 1, z1);
                    
                    world.spawnEntityInWorld(boat);
                    
                    if (!player.capabilities.isCreativeMode) {
                        --itemStack.stackSize;
                    }
                }
            }
            
            return itemStack;
        }
    }
}
