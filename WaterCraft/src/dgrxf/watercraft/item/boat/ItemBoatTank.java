package dgrxf.watercraft.item.boat;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.entity.boat.TankBoat;
import dgrxf.watercraft.lib.ItemInfo;


public class ItemBoatTank extends Item {
    
    public ItemBoatTank() {
        super(ItemInfo.TANK_BOAT_ID);
        setUnlocalizedName(ItemInfo.TANK_BOAT_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.boatTab);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon("boat");
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
                
                if (world.getBlockMaterial(x1, y1, z1) == Material.water && world.isAirBlock(x1, y1 + 1, z1)) {
                	TankBoat boat = new TankBoat(world, x1, y1 + 1, z1);
                    
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