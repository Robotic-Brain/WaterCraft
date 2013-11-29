package dgrxf.watercraft.interactions.ComputerCraft.invocations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import dgrxf.watercraft.interactions.ComputerCraft.ComputerCraftInteractions;

public abstract class PeripheralInvocationHandlerBase implements InvocationHandler{

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(method.getName().equals(ComputerCraftInteractions.callMethod)) return callMethod(args[0], args[1], (Integer)args[2], (Object[])args[3]);
		else if(method.getName().equals(ComputerCraftInteractions.getType)) return getType();
		else if(method.getName().equals(ComputerCraftInteractions.getMethodNames)) return getMethodNames();
		else if(method.getName().equals(ComputerCraftInteractions.canAttachToSide)) return cantAttachToSide();
		return null;
	}

	public abstract boolean cantAttachToSide();

	public abstract Object[] callMethod(Object object, Object object2, int integer, Object[] objects);
	public abstract String getType();
	public abstract String[] getMethodNames();

}
