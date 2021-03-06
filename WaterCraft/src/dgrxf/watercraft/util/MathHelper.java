package dgrxf.watercraft.util;

/**
 * MathHelper Class
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class MathHelper {
    
    /*public static float calculateVector3SquareDistance(Vector3 positionOne, Vector3 positionTwo) {
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
    }*/
    
    public static double calculatePointDistance(double positionOne, double positionTwo) {
        return Math.abs(positionOne - positionTwo);
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
