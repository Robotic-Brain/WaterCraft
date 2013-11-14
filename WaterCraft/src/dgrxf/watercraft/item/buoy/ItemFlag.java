package dgrxf.watercraft.item.buoy;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.lib.ModInfo;

public class ItemFlag extends Item{
	public ItemFlag() {
		super(ItemInfo.FLAG_ID);
		setUnlocalizedName(ItemInfo.FLAG_UNLOCALIZED_NAME);
		setCreativeTab(Watercraft.creativeTab);
		hasSubtypes = true;
		maxStackSize = 1;
	}	

	@SideOnly(Side.CLIENT)
	private Icon[] flags = new Icon[15];
	@Override
	public void registerIcons(IconRegister icon) {
		for(int i = 0; i < flags.length; i++){
			flags[i] = icon.registerIcon(ModInfo.MODID + ":" + "flags/flag_" + (i + 1));
		}
	}
	
	@Override
	public Icon getIconFromDamage(int dmg) {
		return flags[dmg];
	}
	
	@Override
	public void getSubItems(int val, CreativeTabs tab, List subItems) {
		for(int i = 0; i < 15; i++){
			subItems.add(new ItemStack(this, 1, i));
		}
	}
}
