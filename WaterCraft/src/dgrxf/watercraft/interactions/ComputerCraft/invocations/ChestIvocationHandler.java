package dgrxf.watercraft.interactions.ComputerCraft.invocations;

import dgrxf.watercraft.tileentity.WCTileEntityChest;

public class ChestIvocationHandler extends PeripheralInvocationHandlerBase{

	private WCTileEntityChest chest;
	
	private final String[] methods = new String[]{"test"};
	
	public ChestIvocationHandler(WCTileEntityChest te) {
		chest = te;
	}
	
	@Override
	public boolean cantAttachToSide(int side) {
		return true;
	}

	@Override
	public Object[] callMethod(Object object, Object object2, int integer, Object[] args) {
		return new Object[]{"working!"};
	}

	@Override
	public String getType() {
		return "Locking Chest";
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}

}
