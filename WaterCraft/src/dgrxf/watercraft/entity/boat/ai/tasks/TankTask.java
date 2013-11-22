package dgrxf.watercraft.entity.boat.ai.tasks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import dgrxf.watercraft.entity.boat.AbstractBaseBoat;
import dgrxf.watercraft.lib.EntityInfo;

public class TankTask extends BoatAITaskBase implements IFluidHandler{

	public FluidTank tank;
	private boolean firstRun = true;
	
	public TankTask(AbstractBaseBoat boat, Float priority, Object... args) {
		super(boat, priority);
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * (Integer)args[0]);
	}

	@Override
	public void preOnUpdate() {
		super.preOnUpdate();
		if(firstRun){
			if(tank != null){
				if(tank.getFluid() != null){
					boat.getDataWatcher().updateObject(EntityInfo.DATAWATCHER_TANK_AMOUNT, new Integer(tank.getFluidAmount()));
					boat.getDataWatcher().updateObject(EntityInfo.DATAWATCHER_LIQUID_NAME, FluidRegistry.getFluid(tank.getFluid().fluidID).getName());
					firstRun = false;
				}
			}
		}
	}
	
	@Override
	public void onInteractFirst(EntityPlayer player) {
		if(boat.worldObj.isRemote) return;
		ItemStack playerItem = player.inventory.getCurrentItem();
		if(playerItem != null){
			FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(player.getCurrentEquippedItem());
			if(liquid != null){
				int amount = this.fill(ForgeDirection.UNKNOWN, liquid, false);
				if(amount == liquid.amount){
					this.fill(ForgeDirection.UNKNOWN, liquid, true);
					if(!player.capabilities.isCreativeMode){
						player.inventory.setInventorySlotContents(player.inventory.currentItem, removeItem(playerItem));
						return;
					}

					return;
				}
			}
			else if (FluidContainerRegistry.isBucket(playerItem)){
				FluidTankInfo[] tankInfo = this.getTankInfo(ForgeDirection.UNKNOWN);
				FluidStack fillFluid = tankInfo[0].fluid;
				ItemStack stack = FluidContainerRegistry.fillFluidContainer(fillFluid, playerItem);
				if(stack != null){
					this.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.getFluidForFilledItem(stack).amount, true);
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
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int amount = tank.fill(resource, doFill);
		if(amount > 0 && doFill){
			boat.getDataWatcher().updateObject(EntityInfo.DATAWATCHER_TANK_AMOUNT, new Integer(boat.getDataWatcher().getWatchableObjectInt(EntityInfo.DATAWATCHER_TANK_AMOUNT) + amount));
			boat.getDataWatcher().updateObject(EntityInfo.DATAWATCHER_LIQUID_NAME, new String(resource.getFluid().getName()));
		}

		return amount;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack amount = tank.drain(maxDrain, doDrain);
		if(amount != null && doDrain){
			boat.getDataWatcher().updateObject(EntityInfo.DATAWATCHER_TANK_AMOUNT, new Integer(boat.getDataWatcher().getWatchableObjectInt(EntityInfo.DATAWATCHER_TANK_AMOUNT) - amount.amount));
		}else if(amount == null && doDrain){
			boat.getDataWatcher().updateObject(EntityInfo.DATAWATCHER_LIQUID_NAME, new String("none"));
		}
		
		return amount;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		FluidStack fluidStack = null;
		if(tank.getFluid() != null){
			fluidStack = tank.getFluid().copy();
		}
		
		return new FluidTankInfo[]{ new FluidTankInfo(fluidStack, tank.getCapacity()) };
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		writeCustomNBT(par1nbtTagCompound);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		readCustomNBT(par1nbtTagCompound);
	}
	
    public void readCustomNBT (NBTTagCompound tags)
    {
    	if (tags.getBoolean("hasFluid"))
    		tank.setFluid(new FluidStack(tags.getInteger("itemID"), tags.getInteger("Amount")));
    	else
    		tank.setFluid(null);
    }
 
     public void writeCustomNBT (NBTTagCompound tags)
     {
         FluidStack liquid = tank.getFluid();
         tags.setBoolean("hasFluid", liquid != null);
         if (liquid != null)
         {
             tags.setInteger("itemID", liquid.fluidID);
             tags.setInteger("Amount", liquid.amount);
         }
     }
	
}
