package dgrxf.watercraft.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import dgrxf.watercraft.interfaces.IBoatModule;
import dgrxf.watercraft.interfaces.IItemModule;
import dgrxf.watercraft.module.TankModule;

public class ItemBlockLiquidTank extends ItemBlock implements IItemModule{

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

	@Override
	public Class<? extends IBoatModule> getBoatModule() {
		return TankModule.class;
	}
}