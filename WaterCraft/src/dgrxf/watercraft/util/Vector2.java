package dgrxf.watercraft.util;

public class Vector2 {
	float x;
	float z;
	
	public static Vector2 ZERO = new Vector2(0, 0);
	
	public Vector2(float x, float z){
		this.x = x;
		this.z = z;
	}

    @Override
    public String toString() {
        return "{ " + x + ", " + z + " }";
    }
	
}
