package dgrxf.watercraft.interactions.ComputerCraft.invocations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.minecraft.tileentity.TileEntity;
import dgrxf.watercraft.interactions.ComputerCraft.ComputerCraftInteractions;
import dgrxf.watercraft.tileentity.WCTileEntityChest;
import dgrxf.watercraft.tileentity.WCTileEntityLiquidStorageTank;

/**
 * 
 * PeripheralHandler
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class PeripheralHandler implements InvocationHandler{

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(method.getName().equals("getPeripheral")){
			return getPeripheral((TileEntity)args[0]);
		}
		return null;
	}

	public Object getPeripheral(TileEntity te) {
		
		if(te instanceof WCTileEntityLiquidStorageTank){
			return instanciateProxy(new TankPeripheralInvocationHandler((WCTileEntityLiquidStorageTank)te));
		}else if(te instanceof WCTileEntityChest){
			return instanciateProxy(new ChestIvocationHandler((WCTileEntityChest)te));
		}
		
		return null;
	}
	
	private Object instanciateProxy(PeripheralInvocationHandlerBase handler) {
		return Proxy.newProxyInstance(ComputerCraftInteractions.IHostedPeripheralClass.getClassLoader(), new Class[]{ComputerCraftInteractions.IHostedPeripheralClass}, handler);
	}
	
}
