package dgrxf.watercraft.enumeration;

/**
 * 
 * Colours
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public enum Colours {
    white("White"),
    orange("Orange"),
    magenta("Magenta"),
    light_blue("Light Blue"),
    yellow("Yellow"),
    light_green("Light Green"),
    pink("Pink"),
    grey("Grey"),
    light_grey("Light"),
    cyan("Cyan"),
    purple("Purple"),
    blue("Blue"),
    brown("Brown"),
    green("Green"),
    red("Red"),
    black("Black"),
    none("none");
    
    public String name;
    
    Colours(String name) {
        this.name = name;
    }
}
