package dgrxf.watercraft.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class ItemBlockLiquidTank extends ItemBlock{

	public ItemBlockLiquidTank(int par1) {
		super(par1);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean bool) {
		if(itemStack.hasTagCompound()){
			
			NBTTagCompound tag = itemStack.getTagCompound().getCompoundTag("Fluid");
			
			if(tag != null){
				FluidStack fluid = FluidStack.loadFluidStackFromNBT(tag);
				list.add("This tank contains: " + fluid.getFluid().getName());
				list.add("Amount: " + fluid.amount + "mB");
			}
			
		}
	}
}