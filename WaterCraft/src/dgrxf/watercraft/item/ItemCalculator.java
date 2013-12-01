package dgrxf.watercraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiHandler;

/**
 * 
 * ItemCalculator
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ItemCalculator extends Item{
    public ItemCalculator(int id) {
        super(id);
        setCreativeTab(Watercraft.miscTab);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        FMLNetworkHandler.openGui(player, Watercraft.instance, GuiHandler.CALCULATOR_GUI_ID, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return stack;
    }
}
