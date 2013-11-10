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
     * 
     * No, because if Im going from -700 to 700 Math.abs(-700 - 700) = 0 because the absolute value of -700 is 700, so I'm shifting both values by the smaller so
     * I get 0 and 1400 so then 1400 - 0 = 0, which is the correct distance.
     * 
     * -xandayn
     * 
     * - 700 - 700 = - 1400
     * Math.abs(-700 - 700) = 1400
     * 
     * - Frod
     * 
     * Yeah you're right LOL I totally derped that up xD Shhhh no one needs to know! I like overcomplicating things >.>
     * 
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
