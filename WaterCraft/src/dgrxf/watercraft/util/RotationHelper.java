package dgrxf.watercraft.util;

import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;

/**
 * Rotation Helper Class
 * 
 */
public class RotationHelper {
    
	
	public static ForgeDirection minecraftSidesToForgeDirection(int side){
		ForgeDirection dir = ForgeDirection.UNKNOWN;
		System.out.println(side);
		switch(side){
		case 0:
			dir = ForgeDirection.DOWN;
			break;
		case 1:
			dir = ForgeDirection.UP;
			break;
		case 2:
			dir = ForgeDirection.EAST;
			break;
		case 3:
			dir = ForgeDirection.SOUTH;
			break;
		case 4:
			dir = ForgeDirection.NORTH;
			break;
		case 5:
			dir = ForgeDirection.WEST;
			break;
		}
		
		return dir;
	}
	
	/**
	 * Converts a four way forge direction to a yaw rotation
	 * 
	 * 
	 * @param dir
	 * : The four way direction to yaw, will always return 0 if UP or DOWN is given
	 */
	public static int forgeToIntYaw(ForgeDirection dir){
		int yaw = 0;
		
		switch(dir){
		case NORTH:
			yaw = 180;
			break;
		case EAST:
			yaw = -90;
			break;
		case WEST:
			yaw = 90;
			break;
		default:
			yaw = 0;
			break;
		}
		return yaw;
	}
	
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
    
    
    /**
     * Converts Yaw To ForgeDirection
     */
    private static final int[] yToFlookup = { 3, 4, 2, 5 };
}
