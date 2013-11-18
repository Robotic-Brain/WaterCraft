package dgrxf.watercraft.entity.boat.ai.tasks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;

public class TankTask extends BoatAITaskBase{

	public TankTask(AbstractBaseBoat boat, float priority) {
		super(boat, priority);
	}

	@Override
	public void onInteractFirst(EntityPlayer player) {
		if(boat.worldObj.isRemote || !(boat instanceof IFluidHandler)) return;
		ItemStack playerItem = player.inventory.getCurrentItem();
		if(playerItem != null){
			FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(player.getCurrentEquippedItem());
			if(liquid != null){
				int amount = ((IFluidHandler)boat).fill(ForgeDirection.UNKNOWN, liquid, false);
				if(amount == liquid.amount){
					((IFluidHandler)boat).fill(ForgeDirection.UNKNOWN, liquid, true);
					if(!player.capabilities.isCreativeMode){
						player.inventory.setInventorySlotContents(player.inventory.currentItem, removeItem(playerItem));
						return;
					}

					return;
				}
			}
			else if (FluidContainerRegistry.isBucket(playerItem)){
				FluidTankInfo[] tankInfo = ((IFluidHandler)boat).getTankInfo(ForgeDirection.UNKNOWN);
				FluidStack fillFluid = tankInfo[0].fluid;
				ItemStack stack = FluidContainerRegistry.fillFluidContainer(fillFluid, playerItem);
				if(stack != null){
					((IFluidHandler)boat).drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.getFluidForFilledItem(stack).amount, true);
					if(!player.capabilities.isCreativeMode){
						if(playerItem.stackSize == 1){
							player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
						}
						else
						{
							player.inventory.setInventorySlotContents(player.inventory.currentItem, removeItem(playerItem));
							
							if(!player.inventory.addItemStackToInventory(stack)){
								player.dropPlayerItem(stack);
							}
						}
					}
					return;
				}
			}
		}
		
		return;
	}
	
	private ItemStack removeItem(ItemStack playerItem) {
		if(playerItem.stackSize == 1){
			if(playerItem.getItem().hasContainerItem()){
				return playerItem.getItem().getContainerItemStack(playerItem);
			}
			else
			{
				return null;
			}
		}
		else
		{
			playerItem.splitStack(1);
			
			return playerItem;
		}
	}
	
}
