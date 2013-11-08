package dgrxf.watercraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.tileentity.WCTileEntitySmelter;

/**
 * Class Created By: Frod Class Last Modified By: Frod
 * 
 * Class Last Modified On: 11/08/2013 MM/DD/YYYY
 * 
 */

/**
 * 
 * THIS CLASS IS NOT USED
 *
 */

public class IceSmelterBlock extends WCBlock {
	
    public IceSmelterBlock(int id) {
        super(id, Material.iron);
        setCreativeTab(Watercraft.tab);
        setUnlocalizedName(BlockInfo.SMELTER_UNLOCALIZED_NAME);
    }
    
    
    @Override
    public boolean hasTileEntity(int metadata) {
    	return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
    	return new WCTileEntitySmelter();
    }    
    
    @SideOnly(Side.CLIENT)
	private Icon[] icons;			
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register) {	
		icons = new Icon[2];
		icons[0] = register.registerIcon("watercraft:freezer_off");	
		icons[1] = register.registerIcon("watercraft:smelter");	
 	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int meta) {
		return meta < 2? icons[meta]: icons[0];
	}

}
