package dgrxf.watercraft.interactions.ComputerCraft.invocations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import dgrxf.watercraft.interactions.ComputerCraft.ComputerCraftInteractions;
import dgrxf.watercraft.tileentity.WCTileEntityLiquidStorageTank;
import net.minecraft.tileentity.TileEntity;

public class PeripheralInvocationHandler implements InvocationHandler{

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(method.getName().equals("getPeripheral")){
			return getPeripheral((TileEntity)args[0]);
		}
		return null;
	}

	public Object getPeripheral(TileEntity te) {
		if(te instanceof WCTileEntityLiquidStorageTank){
			return Proxy.newProxyInstance(ComputerCraftInteractions.IHostedPeripheralClass.getClassLoader(), new Class[]{ComputerCraftInteractions.IHostedPeripheralClass}, new TankPeripheralInvocationHandler());
		}
		return null;
	}
	
}
