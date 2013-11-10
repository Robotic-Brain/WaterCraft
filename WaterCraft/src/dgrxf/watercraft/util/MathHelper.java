package dgrxf.watercraft.util;

/**
 * Math Helper Class
 * 
 */
public class MathHelper {
    
    public static float calculateVector3SquareDistance(Vector3 positionOne, Vector3 positionTwo) {
        return Vector3.squareLength(Vector3.subtract(positionOne, positionTwo));
    }
    
    public static float calculateVector3Distance(Vector3 positionOne, Vector3 positionTwo) {
        return (float) Math.sqrt(calculateVector3SquareDistance(positionOne, positionTwo));
    }
    
    public static float calculateVector2SquareDistance(Vector2 positionOne, Vector2 positionTwo) {
        return Vector2.squareLength(Vector2.subtract(positionOne, positionTwo));
    }
    
    public static float calculateVector2Distance(Vector2 positionOne, Vector2 positionTwo) {
        return (float) Math.sqrt(calculateVector2SquareDistance(positionOne, positionTwo));
    }
    
    /*
     * can't you just do this? 
     * public static float calculatePointDistance(float positionOne, float positionTwo) {
     * 	  return Math.abs(positionOne - positionTwo);
     * }
     * 
     * - Frod
     */
    
    public static float calculatePointDistance(float positionOne, float positionTwo) {
        float tempDistOne, tempDistTwo, distance = 0;
        if (positionOne > positionTwo) {
            float tempSmaller = Math.abs(positionTwo) + positionTwo;
            float tempLarger = Math.abs(positionTwo) + positionOne;
            distance = tempLarger - tempSmaller;
        } else {
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