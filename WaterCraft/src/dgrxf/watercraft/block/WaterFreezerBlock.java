package dgrxf.watercraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.tileentity.WCTileEntityFreezer;

/**
 * 
 * WaterFreezerBlock
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class WaterFreezerBlock extends Block {
    
    public WaterFreezerBlock(int id) {
        super(id, Material.iron);
        setCreativeTab(Watercraft.miscTab);
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        if (world.isRemote) {
            return true;
        }
        
        FMLNetworkHandler.openGui(player, Watercraft.instance, GuiHandler.FREEZER_GUI_ID, world, x, y, z);
        return true;
    }
}
