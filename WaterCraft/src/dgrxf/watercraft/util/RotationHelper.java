package dgrxf.watercraft.util;

import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;

/**
 * 
 * @author Robotic-Brain
 *
 */
public class RotationHelper {
    
	/*
	 * 
	 * This is a very nice class, very good idea Robotic -xandayn
	 * 
	 */
	
    /**
     * Converts player Yaw to 4 way ForgeDirection
     * 
     * @param yaw
     */
    static public ForgeDirection yawToForge(float yaw) {
        ForgeDirection result = ForgeDirection.getOrientation(yToFlookup[MathHelper.floor_double((double)(yaw * 4.0F / 360.0F) + 0.5D) & 3]);
        //LogHelper.debug("Converter: " + yaw + " -> " + result);
        return result;
    }
    
    /**
     * Converts player Yaw to the opposite 4 way ForgeDirection
     * 
     * @param yaw
     * @return
     */
    public static ForgeDirection yawToOppositeForge(float yaw){
    	ForgeDirection result = yawToForge(yaw);
    	return result.getOpposite();
    }
    
    /**
     * Converts Yaw To ForgeDirection
     */
    private static final int[] yToFlookup = {3, 4, 2, 5};
}
