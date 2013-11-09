package dgrxf.watercraft.util;

public class MathHelper {
	
	public static Vector2 calculateVector2Distance(Vector2 positionOne, Vector2 positionTwo){
		
		return Vector2.ZERO;
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
}
