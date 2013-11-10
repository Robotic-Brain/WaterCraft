package dgrxf.watercraft.util;

import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;

/**
 * Rotation Helper Class
 * 
 */
public class RotationHelper {
    
    /**
     * Converts player Yaw to 4 way ForgeDirection
     * 
     * @param yaw
     */
    static public ForgeDirection yawToForge(float yaw) {
        ForgeDirection result = ForgeDirection.getOrientation(yToFlookup[MathHelper.floor_double((double) (yaw * 4.0F / 360.0F) + 0.5D) & 3]);
        return result;
    }
    
    /**
     * Converts player Yaw to the opposite 4 way ForgeDirection
     * 
     * @param yaw
     * @return
     */
    public static ForgeDirection yawToOppositeForge(float yaw) {
        ForgeDirection result = yawToForge(yaw);
        return result.getOpposite();
    }
    
    public static Vector3 getDirection(int i) {
    	switch (i) {
    		case 0:
    			return new Vector3(0, -1, 0);
    		case 1:
    			return new Vector3(0, 1, 0);
    		case 2:
    			return new Vector3(0, 0, -1);
    		case 3:
    			return new Vector3(0, 0, 1);
    		case 4:
    			return new Vector3(-1, 0, 0);
    		case 5:
    			return new Vector3(1, 0, 0);    		
    	}
    	
    	return Vector3.ZERO;
    }
    
    
    /**
     * Converts Yaw To ForgeDirection
     */
    private static final int[] yToFlookup = { 3, 4, 2, 5 };
}
