package dgrxf.watercraft.interactions.ComputerCraft.invocations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.interactions.ComputerCraft.ComputerCraftInteractions;
import dgrxf.watercraft.interactions.ComputerCraft.enumeration.Methods;

public abstract class PeripheralInvocationHandlerBase implements InvocationHandler{

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(method.getName().equals(Methods.callMethod.toString())) return callMethod(args[0], args[1], (Integer)args[2], (Object[])args[3]);
		else if(method.getName().equals(Methods.getType.toString())) return getType();
		else if(method.getName().equals(Methods.getMethodNames.toString())) return getMethodNames();
		else if(method.getName().equals(Methods.canAttachToSide.toString())) return cantAttachToSide((Integer)args[0]);
		else if(method.getName().equals(Methods.update.toString())) update();
		else if(method.getName().equals(Methods.readFromNBT)) readFromNBT((NBTTagCompound)args[0]);
		else if(method.getName().equals(Methods.writeToNBT)) writeToNBT((NBTTagCompound)args[0]);
		return null;
	}

	public void writeToNBT(NBTTagCompound nbtTagCompound) {}
	public void readFromNBT(NBTTagCompound nbtTagCompound) {}
	public void update() {}

	public abstract boolean cantAttachToSide(int side);
	public abstract Object[] callMethod(Object object, Object object2, int integer, Object[] objects);
	public abstract String getType();
	public abstract String[] getMethodNames();

}
