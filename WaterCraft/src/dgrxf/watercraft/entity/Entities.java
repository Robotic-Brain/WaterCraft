package dgrxf.watercraft.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.entity.boat.DumbBoat;
import dgrxf.watercraft.entity.boat.VanillaBoat;

/**
 * Class Made By: Gory_Moon
 * 
 * Class Last Edited By:Gory_Moon on:2013-11-06
 * 
 */

public class Entities {
    
    public static void init() {
    	EntityRegistry.registerModEntity(VanillaBoat.class, "VanillaBoat", 0, Watercraft.instance, 80, 3, true);
        EntityRegistry.registerModEntity(DumbBoat.class, "WCEntityBoat", 1, Watercraft.instance, 80, 3, true);
        /*EntityRegistry.registerModEntity(WCEntitySmartBoat.class, "WCEntitySmartBoat", 2, Watercraft.instance, 80, 3, true);
        EntityRegistry.registerModEntity(EntityBoatChest.class, "EntityBoatChest", 3, Watercraft.instance, 80, 3, true);
        EntityRegistry.registerModEntity(EntityLavaBoat.class, "EntityLavaBoat", 4, Watercraft.instance, 80, 3, true);
        EntityRegistry.registerModEntity(EntityIceBoat.class, "EntityIceBoat", 5, Watercraft.instance, 80, 3, true);*/
    }
    
}
