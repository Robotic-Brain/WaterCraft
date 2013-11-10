package dgrxf.watercraft.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import dgrxf.watercraft.Watercraft;

/**
 * Class Made By: Gory_Moon
 * 
 * Class Last Edited By:Gory_Moon on:2013-11-06
 * 
 */

public class Entities {
    
    public static void init() {
        EntityRegistry.registerModEntity(WCEntityBoat.class, "WCEntityBoat", 0, Watercraft.instance, 80, 3, true);
        EntityRegistry.registerModEntity(WCEntitySmartBoat.class, "WCEntitySmartBoat", 1, Watercraft.instance, 80, 3, true);
    }
    
}
