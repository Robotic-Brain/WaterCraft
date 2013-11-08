package dgrxf.watercraft.item;

/**
 * Class Made By: ???
 * 
 * Class Last Edited By: ???
 * Class Last Edited On: 07/11/2013
 * 						 DD/MM/YYYY
 * 
 */

import net.minecraft.item.Item;

public class ModItems {
    
    public static Item boat;
    
    public static void init() {
        boat = new ItemBoat();
    }
}
