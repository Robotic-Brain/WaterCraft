package dgrxf.watercraft.interactions.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 
 * Reflection
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class Reflection {
	
	public static Class getClassFromName(String name){
		try{
			return Class.forName(name);
 		}catch(ClassNotFoundException e){}
		return null;
	}
	
	public static Method getMethodFromClass(Class clazz, String methodName, Class... args){
		try{
			Method m = clazz.getDeclaredMethod(methodName, args);
			m.setAccessible(true);
			return m;
		}
		catch(NoSuchMethodException e){}
		catch(SecurityException e){}
		return null;
	}
	
	public static Field getFieldFromClass(Class clazz, String fieldName){
		try{
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f;
		}catch(Exception e){
		}
		return null;
	}
}
