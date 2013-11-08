package dgrxf.watercraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemBlockControlUnit extends ItemBlock {
    
    public ItemBlockControlUnit(int id) {
        super(id);
    }
    
    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        int rotate = ((MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
        return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, rotate);
    }
    
}
