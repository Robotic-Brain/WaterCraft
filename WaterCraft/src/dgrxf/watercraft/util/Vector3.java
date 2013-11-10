package dgrxf.watercraft.util;

public class Vector3 {
    
    public float          x, y, z;
    
    public final static Vector3 ZERO = new Vector3(0, 0, 0);
    
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public String toString() {
        return "( " + x + ", " + y + ", " + z + " )";
    }
    
    public static Vector3 add(Vector3 v1, Vector3 v2) {
    	return new Vector3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }
    
    public static Vector3 scalarMult(Vector3 v, float a) {
    	return new Vector3(a * v.x, a * v.y, a * v.z);
    }
    
    public static Vector3 subtract(Vector3 v1, Vector3 v2) {
    	return add(v1, scalarMult(v2, -1));
    }
    
    public static float dotProduct(Vector3 v1, Vector3 v2) {
    	return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }
    
    public static float squareLength(Vector3 v) {
    	return dotProduct(v, v);
    }
    
    public static float length(Vector3 v) {
    	return (float) Math.sqrt(squareLength(v));
    }
    
    public static Vector3 crossProduct(Vector3 v1, Vector3 v2) {
    	return new Vector3(v1.y * v2.z -  v1.z * v2.y, v1.z * v2.x -  v1.x * v2.z, v1.x * v2.y -  v1.y * v2.x);
    }
}
