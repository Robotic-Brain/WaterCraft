package dgrxf.watercraft.interactions.ComputerCraft.tileentity;

import net.minecraftforge.fluids.FluidRegistry;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.ILuaContext;
import dan200.computer.api.IPeripheral;
import dgrxf.watercraft.tileentity.WCTileEntityLiquidStorageTank;

public class WCCCTileEntityLiquidStorageTank extends WCTileEntityLiquidStorageTank implements IPeripheral{
	public String[] methods = new String[]{
		"getFluidName",
		"getFluidAmount",
		"getTankSize"
	};
	
	@Override
	public String getType() {
		return "LiquidTank";
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		switch(method){
		case 0:
			return new Object[]{tank.getFluid() == null ? "empty" : FluidRegistry.getFluidName(tank.getFluid().fluidID)};
		case 1:
			return new Object[]{tank.getFluid() == null ? 0 : tank.getFluidAmount()};
		case 2:
			return new Object[]{tank.getCapacity()};
		}
		return null;
	}

	@Override
	public boolean canAttachToSide(int side) {
		return true;
	}

	@Override
	public void attach(IComputerAccess computer) {
	}

	@Override
	public void detach(IComputerAccess computer) {
	}

}
