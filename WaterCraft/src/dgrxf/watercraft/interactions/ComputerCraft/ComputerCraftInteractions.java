package dgrxf.watercraft.interactions.ComputerCraft;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import dgrxf.watercraft.interactions.ComputerCraft.invocations.PeripheralInvocationHandler;
import dgrxf.watercraft.interactions.util.Reflection;
import dgrxf.watercraft.tileentity.WCTileEntityLiquidStorageTank;

public class ComputerCraftInteractions {

	public static Class IPeripheralHandlerClass = null;
	public static Class IHostedPeripheralClass = null;
	public static Class ComputerCraftAPIClass = null;
	public static Method RegisterExternalPeripheral = null;
	public static String getType = "getType";
	public static String getMethodNames = "getMethodNames";
	public static String callMethod = "callMethod";
	public static String canAttachToSide = "canAttachToSide";
	
	public static void beginInteraction(){
		ComputerCraftAPIClass = Reflection.getClassFromName("dan200.computer.api.ComputerCraftAPI");
		if(ComputerCraftAPIClass != null){
			try {
				IPeripheralHandlerClass = Reflection.getClassFromName("dan200.computer.api.IPeripheralHandler");
				IHostedPeripheralClass = Reflection.getClassFromName("dan200.computer.api.IHostedPeripheral");
				RegisterExternalPeripheral = ComputerCraftAPIClass.getMethod("registerExternalPeripheral", Class.class, IPeripheralHandlerClass);
				RegisterExternalPeripheral.invoke(null, WCTileEntityLiquidStorageTank.class, IPeripheralHandlerClass.cast(Proxy.newProxyInstance(IPeripheralHandlerClass.getClassLoader(), new Class[]{IPeripheralHandlerClass}, new PeripheralInvocationHandler())));
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
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
	
}
