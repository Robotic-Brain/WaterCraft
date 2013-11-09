package dgrxf.watercraft.util;

/**
 * Math Helper Class
 *
 */
public class MathHelper {
	
	public static float calculateVector3Distance(Vector3 positionOne, Vector3 positionTwo){
		float tempX, tempY, tempZ, distance;
		tempX = positionTwo.x - positionOne.x;
		tempY = positionTwo.y - positionOne.y;
		tempZ = positionTwo.z - positionOne.z;
		tempX *= tempX;
		tempY *= tempY;
		tempZ *= tempZ;
		
		distance = (float)  Math.sqrt(tempX + tempY + tempZ);
		
		return distance;
	}
	
	public static float calculateVector2Distance(Vector2 positionOne, Vector2 positionTwo){
		float tempX, tempZ, distance;
		tempX = positionTwo.x - positionOne.x;
		tempZ = positionTwo.z - positionOne.z;
		tempX *= tempX;
		tempZ *= tempZ;
		
		distance = (float) Math.sqrt(tempX + tempZ);
		
		return distance;
	}
	
	public static float calculatePointDistance(float positionOne, float positionTwo){
		float tempDistOne, tempDistTwo, distance = 0;
		if(positionOne > positionTwo){
			float tempSmaller = Math.abs(positionTwo) + positionTwo;
			float tempLarger = Math.abs(positionTwo) + positionOne;
			distance = tempLarger - tempSmaller;
		}
		else{
			float tempSmaller = Math.abs(positionOne) + positionOne;
			float tempLarger = Math.abs(positionOne) + positionTwo;
			distance = tempLarger - tempSmaller;
		}
		
		return distance;
	}
	
	/**
	 * Clamps a value within range
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return clamped value
	 */
	public static double clamp(double val, double min, double max) {
	    return Math.min(max, Math.max(min, val));
	}
}
