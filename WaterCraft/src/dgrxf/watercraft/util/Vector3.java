package dgrxf.watercraft.util;

import net.minecraftforge.common.ForgeDirection;

public class Vector3 {
    
    public double x, y, z;
    
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
    public Vector3(double x, double y, double z) {
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
    public Vector3 add(double a) {
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
    public Vector3 sub(double a) {
        return this.add(-a);
    }
    
    /**
     * Multiplies this by a scalar
     * 
     * @param a
     * @return this * a
     */
    public Vector3 scalarMult(double a) {
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
    public double dot(Vector3 v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }
    
    /**
     * Returns the length squared
     * 
     * @return |this| * |this|
     */
    public double length2() {
        return this.dot(this);
    }
    
    /**
     * Returns the absolute length
     * 
     * @return |this|
     */
    public double length() {
        return Math.sqrt(this.length2());
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
    
    /**
     * Swizzle x and y (See GLSL swizzle)
     * 
     * @return [x, y]
     */
    public Vector2 xy() {
        return new Vector2(x, y);
    }
    
    /**
     * Swizzle x and z (See GLSL swizzle)
     * 
     * @return [x, z]
     */
    public Vector2 xz() {
        return new Vector2(x, z);
    }
    
    /**
     * Swizzle y and z (See GLSL swizzle)
     * 
     * @return [y, z]
     */
    public Vector2 yz() {
        return new Vector2(y, z);
    }
    
    /**
     * Sets new coordinates to the vector
     * 
     * @param x x
     * @param y y
     * @param z z
     */    
    public void setNewCoordinates(double x, double y, double z) {
    	this.x = x;
    	this.y = y;
    	this.z = z;
    }
    
    @Override
    public String toString() {
        return "[ " + x + ", " + y + ", " + z + " ]";
    }
}
