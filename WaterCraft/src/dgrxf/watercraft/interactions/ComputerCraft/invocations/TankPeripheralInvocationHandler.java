package dgrxf.watercraft.interactions.ComputerCraft.invocations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.minecraftforge.fluids.FluidRegistry;
import dgrxf.watercraft.interactions.ComputerCraft.ComputerCraftInteractions;
import dgrxf.watercraft.tileentity.WCTileEntityLiquidStorageTank;

public class TankPeripheralInvocationHandler extends PeripheralInvocationHandlerBase implements InvocationHandler{

	WCTileEntityLiquidStorageTank tank;
	public static final String[] methods = new String[]{"getFluidName", "getFluidAmount", "getTankCapacity", "getLocation"};
	
	public TankPeripheralInvocationHandler(WCTileEntityLiquidStorageTank te) {
		tank = te;
	}
	
	@Override
	public Object[] callMethod(Object computer, Object context, int method, Object[] args) {
		switch(method){
		case 0:
			return new Object[]{tank.tank.getFluid() != null ? FluidRegistry.getFluidName(tank.tank.getFluid().fluidID) : "empty"};
		case 1:
			return new Object[]{tank.tank.getFluidAmount()};
		case 2:
			return new Object[]{tank.tank.getCapacity()};
		case 3:
			return new Object[]{tank.xCoord, tank.yCoord, tank.zCoord};
		}
		return null;
	}

	@Override
	public boolean cantAttachToSide(int side) {
		return true;
	}

	@Override
	public String getType() {
		return "Tank";
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}

}
