package dgrxf.watercraft.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.tileentity.buoy.WCTileEntityBuoy;

public class ItemTelescope extends Item {
    
    public ItemTelescope(int id) {
        super(id);
        setCreativeTab(Watercraft.miscTab);
    }
    
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (world.getBlockId(x, y, z) == ModBlocks.BUOY.getId()) {
                ;
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
