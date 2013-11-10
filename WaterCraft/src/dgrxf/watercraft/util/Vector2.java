package dgrxf.watercraft.util;

public class Vector2 {
	public float          x, z;
    
    public final static Vector2 ZERO = new Vector2(0, 0);
    
    public Vector2(float x, float z) {
        this.x = x;
        this.z = z;
    }
    
    @Override
    public String toString() {
        return "( " + x + ", " + z + " )";
    }
    
    public static Vector2 add(Vector2 v1, Vector2 v2) {
    	return new Vector2(v1.x + v2.x, v1.z + v2.z);
    }
    
    public static Vector2 scalarMult(Vector2 v, float a) {
    	return new Vector2(a * v.x, a * v.z);
    }
    
    public static Vector2 subtract(Vector2 v1, Vector2 v2) {
    	return add(v1, scalarMult(v2, -1));
    }
    
    public static float dotProduct(Vector2 v1, Vector2 v2) {
    	return v1.x * v2.x + v1.z * v2.z;
    }
    
    public static float squareLength(Vector2 v) {
    	return dotProduct(v, v);
    }
    
    public static float length(Vector2 v) {
    	return (float) Math.sqrt(squareLength(v));
    }
    
    
}
