package dgrxf.watercraft.entity.boat;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.entity.boat.ai.BoatAITaskList;
import dgrxf.watercraft.entity.boat.ai.tasks.DumbTask;
import dgrxf.watercraft.entity.boat.ai.tasks.TankTask;
import dgrxf.watercraft.lib.EntityInfo;

public class TankBoat extends AbstractBaseBoat implements IFluidHandler{

	public FluidTank tank;
	private boolean firstRun = true;
	
	public TankBoat(World world){
		super(world);
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 8);
	}
	
	public TankBoat(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 8);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(firstRun){
			if(tank != null){
				if(tank.getFluid() != null){
					dataWatcher.updateObject(EntityInfo.DATAWATCHER_TANK_AMOUNT, new Integer(tank.getFluidAmount()));
					dataWatcher.updateObject(EntityInfo.DATAWATCHER_LIQUID_NAME, FluidRegistry.getFluid(tank.getFluid().fluidID).getName());
					firstRun = false;
				}
			}
		}
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(EntityInfo.DATAWATCHER_TANK_AMOUNT, new Integer(0));
		dataWatcher.addObject(EntityInfo.DATAWATCHER_LIQUID_NAME, "none");
	}

	@Override
	protected void updateBoatAI(BoatAITaskList list) {
		list.addTask(new DumbTask(this, 0.0F));
		list.addTask(new TankTask(this, 1.0F));
	}
	
	@Override
	public Block getDisplayTile() {
		return ModBlocks.tank;
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int amount = tank.fill(resource, doFill);
		if(amount > 0 && doFill){
			dataWatcher.updateObject(EntityInfo.DATAWATCHER_TANK_AMOUNT, new Integer(dataWatcher.getWatchableObjectInt(EntityInfo.DATAWATCHER_TANK_AMOUNT) + amount));
			dataWatcher.updateObject(EntityInfo.DATAWATCHER_LIQUID_NAME, new String(resource.getFluid().getName()));
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
			dataWatcher.updateObject(EntityInfo.DATAWATCHER_TANK_AMOUNT, new Integer(dataWatcher.getWatchableObjectInt(EntityInfo.DATAWATCHER_TANK_AMOUNT) - amount.amount));
		}else if(amount == null && doDrain){
			dataWatcher.updateObject(EntityInfo.DATAWATCHER_LIQUID_NAME, new String("none"));
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
   	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
          super.writeToNBT(par1nbtTagCompound);
          writeCustomNBT(par1nbtTagCompound);
    }
   
    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
         super.readFromNBT(par1nbtTagCompound);
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
