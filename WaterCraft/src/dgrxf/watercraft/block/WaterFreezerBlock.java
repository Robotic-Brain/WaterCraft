package dgrxf.watercraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.tileentity.WCTileEntityFreezer;

/**
 * Class Created By: Frod Class Last Modified By: Frod
 * 
 * Class Last Modified On: 11/08/2013 MM/DD/YYYY
 * 
 */

public class WaterFreezerBlock extends WCBlock {
    
    public WaterFreezerBlock(int id) {
        super(id, Material.iron);
        setCreativeTab(Watercraft.miscTab);
        setUnlocalizedName(BlockInfo.FREEZER_UNLOCALIZED_NAME);
    }
    
    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new WCTileEntityFreezer();
    }
    
    @SideOnly(Side.CLIENT)
    private Icon[] icons;
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register) {
        icons = new Icon[3];
        icons[0] = register.registerIcon("watercraft:freezer_off");
        icons[1] = register.registerIcon("watercraft:freezer");
        icons[2] = register.registerIcon("watercraft:smelter");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int meta) {
        return meta < icons.length ? icons[meta] : icons[0];
    }
    
    //temp
    @Override
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        
        if (!w.isRemote) {
            w.setBlockMetadataWithNotify(x, y, z, (w.getBlockMetadata(x, y, z) + 1) % icons.length, 3);
        }
        
        return true;
    }
}
