package dgrxf.watercraft.util;


public class Vector2 {
	public float          x, y;
    
    /**
     * Creates new Zero Vector
     */
    public Vector2() {
        this(0, 0);
    }
    
    /**
     * Creates new Vector
     * 
     * @param x
     * @param y
     */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Creates new Vector with equal x and y component
     * 
     * @param a
     */
    public Vector2(float a) {
        this.x = a;
        this.y = a;
    }
    
    /**
     * Returns a normalized vector
     * 
     * @return this/|this|
     */
    public Vector2 normalize() {
        return this.scalarMult(1.0F / this.length());
    }
    
    /**
     * Adds another vector
     * 
     * @param v
     * @return this + V
     */
    public Vector2 add(Vector2 v) {
        return new Vector2(this.x + v.x, this.y + v.y);
    }
    
    /**
     * Adds a scalar
     * 
     * @param a
     * @return this + a
     */
    public Vector2 add(float a) {
        return new Vector2(this.x + a, this.y + a);
    }
    
    /**
     * Subtracts another vector
     * 
     * @param v
     * @return this - V
     */
    public Vector2 sub(Vector2 v) {
        return this.add(v.negate());
    }
    
    /**
     * Subtracts a scalar
     * 
     * @param a
     * @return this - a
     */
    public Vector2 sub(float a) {
        return this.add(-a);
    }
    
    /**
     * Multiplies this by a scalar
     * 
     * @param a
     * @return this * a
     */
    public Vector2 scalarMult(float a) {
        return new Vector2(a * this.x, a * this.y);
    }
    
    /**
     * Inverts this vector
     * 
     * @return this * -1
     */
    public Vector2 negate() {
        return scalarMult(-1);
    }
    
    /**
     * Returns the dot product of this and another vector
     * 
     * @param v
     * @return this . V
     */
    public float dot(Vector2 v) {
        return this.x * v.x + this.y * v.y;
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
    
    @Override
    public String toString() {
        return "[ " + x + ", " + y + "]";
    }
    
    
}
