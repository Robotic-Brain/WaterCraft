package dgrxf.watercraft.util;

public class Vector3 {
    
    public int x, y, z;
    
    public Vector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public String toString() {
        return "{ " + x + ", " + y + ", " + z + " }";
    }
}
