package dgrxf.watercraft.interactions.ComputerCraft;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.minecraft.tileentity.TileEntity;
import dgrxf.watercraft.interactions.ComputerCraft.invocations.PeripheralHandler;
import dgrxf.watercraft.interactions.util.Reflection;
import dgrxf.watercraft.tileentity.WCTileEntityChest;
import dgrxf.watercraft.tileentity.WCTileEntityLiquidStorageTank;

public class ComputerCraftInteractions {

	public static Class IPeripheralHandlerClass = null;
	public static Class IHostedPeripheralClass = null;
	public static Class ComputerCraftAPIClass = null;
	
	public static Method RegisterExternalPeripheral = null;
	
	private static PeripheralHandler pe;
	
	public static void beginInteraction(){
		ComputerCraftAPIClass = Reflection.getClassFromName("dan200.computer.api.ComputerCraftAPI");
		if(ComputerCraftAPIClass != null){
			try {
				pe = new PeripheralHandler();
				IPeripheralHandlerClass = Reflection.getClassFromName("dan200.computer.api.IPeripheralHandler");
				IHostedPeripheralClass = Reflection.getClassFromName("dan200.computer.api.IHostedPeripheral");
				RegisterExternalPeripheral = Reflection.getMethodFromClass(ComputerCraftAPIClass, "registerExternalPeripheral", Class.class, IPeripheralHandlerClass);
				registerExternalPeripheral(WCTileEntityLiquidStorageTank.class);
				registerExternalPeripheral(WCTileEntityChest.class);
			}catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void registerExternalPeripheral(Class te) throws IllegalAccessException, InvocationTargetException{
		RegisterExternalPeripheral.invoke(null, te, IPeripheralHandlerClass.cast(Proxy.newProxyInstance(IPeripheralHandlerClass.getClassLoader(), new Class[]{IPeripheralHandlerClass}, pe)));
	}
}
