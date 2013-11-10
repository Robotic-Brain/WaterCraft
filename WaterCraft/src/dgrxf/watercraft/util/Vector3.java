package dgrxf.watercraft.util;

import net.minecraftforge.common.ForgeDirection;

public class Vector3 {
    
    public float x, y, z;
    
    /**
     * Creates new Zero Vector
     */
    public Vector3() {
        this(0, 0, 0);
    }
    
    /**
     * Creates new Vector
     * 
     * @param x
     * @param y
     * @param z
     */
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Converts a ForgeDirection to a Vector3
     * 
     * @param direction
     */
    public Vector3(ForgeDirection d) {
        this(d.offsetX, d.offsetY, d.offsetZ);
    }
    
    /**
     * Returns a normalized vector
     * 
     * @return this/|this|
     */
    public Vector3 normalize() {
        return this.scalarMult(1.0F / this.length());
    }
    
    /**
     * Adds another vector
     * 
     * @param v
     * @return this + V
     */
    public Vector3 add(Vector3 v) {
        return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }
    
    /**
     * Adds a scalar
     * 
     * @param a
     * @return this + a
     */
    public Vector3 add(float a) {
        return new Vector3(this.x + a, this.y + a, this.z + a);
    }
    
    /**
     * Subtracts another vector
     * 
     * @param v
     * @return this - V
     */
    public Vector3 sub(Vector3 v) {
        return this.add(v.negate());
    }
    
    /**
     * Subtracts a scalar
     * 
     * @param a
     * @return this - a
     */
    public Vector3 sub(float a) {
        return this.add(-a);
    }
    
    /**
     * Multiplies this by a scalar
     * 
     * @param a
     * @return this * a
     */
    public Vector3 scalarMult(float a) {
        return new Vector3(a * this.x, a * this.y, a * this.z);
    }
    
    /**
     * Inverts this vector
     * 
     * @return this * -1
     */
    public Vector3 negate() {
        return scalarMult(-1);
    }
    
    /**
     * Returns the dot product of this and another vector
     * 
     * @param v
     * @return this . V
     */
    public float dot(Vector3 v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }
    
    /**
     * Returns the length squared
     * 
     * @return |this| * |this|
     */
    public float length2() {
        return this.dot(this);
    }
    
    /**
     * Returns the absolute length
     * 
     * @return |this|
     */
    public float length() {
        return (float) Math.sqrt(this.length2());
    }
    
    /**
     * Returns the cross product of this and another vector
     * 
     * @param v
     * @return this x V
     */
    public Vector3 cross(Vector3 v) {
        return new Vector3(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x);
    }
    
    @Override
    public String toString() {
        return "[ " + x + ", " + y + ", " + z + " ]";
    }
}
