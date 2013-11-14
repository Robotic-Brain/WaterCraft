package dgrxf.watercraft.item;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.client.gui.GuiHandler;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.lib.ModInfo;


public class ItemCalculator extends Item{
	public ItemCalculator() {
		super(ItemInfo.CALCULATOR_ID);
		setCreativeTab(Watercraft.miscTab);
		setUnlocalizedName(ItemInfo.CALCULATOR_UNLOCALIZED_NAME);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		FMLNetworkHandler.openGui(player, Watercraft.instance, GuiHandler.CALCULATOR_GUI_ID, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		return stack;
	}
}
