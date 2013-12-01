package dgrxf.watercraft.util;

/**
 * 
 * Rectangle
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
// TDOD: Maybe this class needs re-thinking
public class Rectangle {
    /**
     * Starting point
     */
    public double x, y;
    
    /**
     * (W)idth and (h)eight
     */
    public double w, h;
    
    /**
     * Creates an empty rectangle
     */
    public Rectangle() {
        this(0, 0, 0, 0);
    }
    
    /**
     * Creates a rectangle with the specified bounds
     * 
     * @param x
     *            Start x
     * @param y
     *            Start y
     * @param w
     *            Width
     * @param h
     *            Height
     */
    public Rectangle(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        
        if (w < 0) {
            this.x = x + w;
            this.w = -w;
        }
        
        if (h < 0) {
            this.y = y + h;
            this.h = -h;
        }
    }
    
    /**
     * Creates a rectangle with the specified bounds
     * 
     * @param s
     *            Starting point
     * @param w
     *            Width
     * @param h
     *            Height
     */
    public Rectangle(Vector2 s, double w, double h) {
        this(s.x, s.y, w, h);
    }
    
    /**
     * Creates a rectangle with the specified bounds
     * 
     * @param s
     *            Starting point
     * @param b
     *            Vector containing the x and y size
     */
    public Rectangle(Vector2 s, Vector2 b) {
        this(s, b.x, b.y);
    }
    
    /**
     * Moves the rectangle with given offset
     * 
     * @param o
     *            Offset
     * @return Rectangle(position + offset)
     */
    public Rectangle translate(Vector2 o) {
        return new Rectangle(x + o.x, y + o.y, w, h);
    }
    
    /**
     * Scales the rectangle with given factor
     * 
     * @param a
     *            Scaling factor
     * @return Rectangle(size * a)
     */
    public Rectangle scale(double a) {
        return new Rectangle(x, y, w * a, h * a);
    }
    
    /**
     * Scales the rectangle around its center
     * 
     * @param a
     *            Scaling factor
     * @return scaled rectangle
     */
    /*public Rectangle centeredScale(double a) {
        return scale(a).translate(new Vector2(a / 2.0f));
    }*/
    
    /**
     * Changes size by given amount
     * 
     * 
     * @param a
     *            Vector of x and y amount
     * @return Rectangle(size + a)
     */
    public Rectangle trim(Vector2 a) {
        return new Rectangle(x, y, w + a.x, h + a.y);
    }
    
    @Override
    public String toString() {
        return "Rect: [" + x + ", " + y + "|" + w + ", " + h + "]";
    }
}
