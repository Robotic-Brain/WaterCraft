package dgrxf.watercraft.block.buoy;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.DirectionalBlock;
import dgrxf.watercraft.entity.WCEntityBoat;
import dgrxf.watercraft.enumeration.Colours;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.lib.RenderInfo;
import dgrxf.watercraft.tileentity.buoy.WCTileEntityFilterBuoy;

public class BuoyFilterBlock extends BuoyBlock {
    
    public BuoyFilterBlock(int id) {
        super(id);
        setUnlocalizedName(BlockInfo.BUOY_FILTER_UNLOCALIZED_NAME);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    	if(world.isRemote)
    		return false;
    	
    	ItemStack stack = player.getCurrentEquippedItem();
    	WCTileEntityFilterBuoy tile = (WCTileEntityFilterBuoy) world.getBlockTileEntity(x, y, z);
    	
    	if(tile != null && stack != null && stack.getItem().itemID == ItemInfo.FLAG_ID){
    		Colours[] temp = Colours.values();
    		tile.setColour(side, temp[stack.getItemDamage()]);
    		return true;
    	}
    	return false;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new WCTileEntityFilterBuoy();
    }    
}