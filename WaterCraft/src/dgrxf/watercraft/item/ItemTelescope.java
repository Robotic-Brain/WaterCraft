package dgrxf.watercraft.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.client.particles.BuoyParticle;
import dgrxf.watercraft.client.particles.CustomParticles;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.tileentity.buoy.WCTileEntityBuoy;
import dgrxf.watercraft.util.MathHelper;
import dgrxf.watercraft.util.Vector3;

public class ItemTelescope extends Item{
		
    public ItemTelescope() {
        super(ItemInfo.TELESCOPE_ID);
        setUnlocalizedName(ItemInfo.TELESCOPE_UNLOCALIZED_NAME);
        setCreativeTab(Watercraft.miscTab);
    }
    
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {            
            if (world.getBlockId(x, y, z) == ModBlocks.buoy.blockID) {;
                TileEntity te = world.getBlockTileEntity(x, y, z);
                if (te instanceof WCTileEntityBuoy) {
                    WCTileEntityBuoy buoy = (WCTileEntityBuoy) te;  
                    // TODO: remove comments
                    //buoy.enableSpawning();
                }
            }
        }
        
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
    	itemIcon = register.registerIcon("Watercraft:telescope");
    }
    
}
