package dgrxf.watercraft.enumeration;

import net.minecraft.item.Item;

/**
 * 
 * FuelType
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public enum FuelType {
    
    COAL(Item.coal), NONE(null);
    
    private final Item fuel;
    
    private FuelType(Item fuel) {
        this.fuel = fuel;
    }
}
