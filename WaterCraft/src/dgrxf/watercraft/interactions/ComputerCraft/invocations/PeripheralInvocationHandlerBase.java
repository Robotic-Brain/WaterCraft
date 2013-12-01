package dgrxf.watercraft.interactions.ComputerCraft.invocations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.minecraft.nbt.NBTTagCompound;
import dgrxf.watercraft.interactions.ComputerCraft.enumeration.Methods;

/**
 * 
 * PeripheralInvocationHandlerBase
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
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
	/**
	 * @param computer this parameter is of type IComputerAccess
	 * @param luaContext this parameter is of type ILuaContext
	 * @param method the method that is trying to be called (the order is determined by the ordered getMethodNames returns)
	 * @param args the arguments for the method that computercraft is trying to call
	 * @return whatever the method computercraft is trying to call returns
	 */
	public abstract Object[] callMethod(Object computer, Object luaContext, int method, Object[] args);
	public abstract String getType();
	public abstract String[] getMethodNames();

}
