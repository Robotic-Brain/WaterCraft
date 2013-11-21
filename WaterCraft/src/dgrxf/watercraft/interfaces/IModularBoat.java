package dgrxf.watercraft.interfaces;

import java.util.HashSet;

import net.minecraft.item.ItemStack;

public interface IModularBoat {

	public HashSet getModuleList(ItemStack stack);
	
}
