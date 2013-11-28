package dgrxf.watercraft.interactions.ComputerCraft.tileentity;

import dan200.computer.api.IComputerAccess;
import dan200.computer.api.ILuaContext;
import dan200.computer.api.IPeripheral;
import dgrxf.watercraft.tileentity.WCTileEntityBoatAssembler;

public class WCCCTileEntityBoatAssembler extends WCTileEntityBoatAssembler implements IPeripheral{

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Test";
	}

	@Override
	public String[] getMethodNames() {
		// TODO Auto-generated method stub
		return new String[]{"Test"};
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context,
			int method, Object[] arguments) throws Exception {
		return method == 0 ? new Object[]{"Hello World!"} : null;
	}

	@Override
	public boolean canAttachToSide(int side) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void attach(IComputerAccess computer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detach(IComputerAccess computer) {
		// TODO Auto-generated method stub
		
	}

}
