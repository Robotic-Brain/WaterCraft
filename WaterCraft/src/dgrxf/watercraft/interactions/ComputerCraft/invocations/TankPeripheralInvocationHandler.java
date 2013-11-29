package dgrxf.watercraft.interactions.ComputerCraft.invocations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import dgrxf.watercraft.interactions.ComputerCraft.ComputerCraftInteractions;

public class TankPeripheralInvocationHandler implements InvocationHandler{

	public static final String[] methods = new String[]{"test"};
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(method.getName().equals(ComputerCraftInteractions.callMethod)) return callMethod(args[0], args[1], (Integer)args[2], (Object[])args[3]);
		else if(method.getName().equals(ComputerCraftInteractions.getType)) return "Tank";
		else if(method.getName().equals(ComputerCraftInteractions.getMethodNames)) return methods;
		else if(method.getName().equals(ComputerCraftInteractions.canAttachToSide)) return true;
		return null;
	}

	private Object[] callMethod(Object computer, Object context, int method, Object[] args) {
		switch(method){
		case 0:
			return new Object[]{"Hurray!"};
		}
		return null;
	}

}
