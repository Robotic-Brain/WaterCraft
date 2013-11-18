package dgrxf.watercraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class WCTileEntityLiquidStorageTank extends TileEntity implements IFluidHandler{

	public FluidTank tank;
	
	public WCTileEntityLiquidStorageTank() {
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 8);
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int amount = tank.fill(resource, doFill);
		if(amount > 0 && doFill){
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}

		return amount;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
			return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack amount = tank.drain(maxDrain, doDrain);
		if(amount != null && doDrain){
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
	
	@Override
	public void updateEntity() {
		super.updateEntity();
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeCustomNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, tag);
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		
		readCustomNBT(pkt.data);
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
}
