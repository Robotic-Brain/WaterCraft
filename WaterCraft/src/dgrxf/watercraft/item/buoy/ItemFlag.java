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
import dgrxf.watercraft.lib.ModInfo;
import dgrxf.watercraft.util.TranslationHelper.TH;

/**
 * 
 * ItemFlag
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ItemFlag extends Item {
    public ItemFlag(int id) {
        super(id);
        setCreativeTab(Watercraft.boatTab);
        hasSubtypes = true;
        maxStackSize = 1;
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
    	return super.getUnlocalizedName() + "." + TH.FLAGS[stack.getItemDamage()];
    }
    
    @SideOnly(Side.CLIENT)
    private Icon[] flags;
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister icon) {
    	flags = new Icon[16];
        for (int i = 0; i < flags.length; i++) {
            flags[i] = icon.registerIcon(ModInfo.MODID + ":" + "flags/flag_" + (i + 1));
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int dmg) {
        return flags[dmg];
    }
    
    @Override
    public void getSubItems(int val, CreativeTabs tab, List subItems) {
        for (int i = 0; i < 16; i++) {
        	ItemStack stack = new ItemStack(this, 1, i);
        	stack.setItemName(TH.translate(stack.getUnlocalizedName(), 0));
            subItems.add(stack);
        }
    }
}
