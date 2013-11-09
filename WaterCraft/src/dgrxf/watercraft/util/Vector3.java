package dgrxf.watercraft.util;

public class Vector3{
    
    public float x, y, z;
    
    public static Vector3 ZERO = new Vector3(0, 0, 0);
    
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public String toString() {
        return "{ " + x + ", " + y + ", " + z + " }";
    }
}
