package dgrxf.watercraft.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import dgrxf.watercraft.Watercraft;
import dgrxf.watercraft.entity.EntityTrader.EntityTrader;
import dgrxf.watercraft.entity.boat.ChestBoat;
import dgrxf.watercraft.entity.boat.DumbBoat;
import dgrxf.watercraft.entity.boat.IceBoat;
import dgrxf.watercraft.entity.boat.LavaBoat;
import dgrxf.watercraft.entity.boat.ModularBoat;
import dgrxf.watercraft.entity.boat.TankBoat;
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
        EntityRegistry.registerModEntity(ChestBoat.class, "EntityBoatChest", 2, Watercraft.instance, 80, 3, true);
        EntityRegistry.registerModEntity(LavaBoat.class, "EntityLavaBoat", 3, Watercraft.instance, 80, 3, true);
        EntityRegistry.registerModEntity(IceBoat.class, "EntityIceBoat", 4, Watercraft.instance, 80, 3, true);
        EntityRegistry.registerModEntity(TankBoat.class, "EntityTankBoat", 5, Watercraft.instance, 80, 3, true);
        EntityRegistry.registerModEntity(ModularBoat.class, "EntityModularBoat", 6, Watercraft.instance, 80, 3, true);
        EntityRegistry.registerModEntity(EntityTrader.class, "EntityTrader", 13, Watercraft.instance, 80, 3, true);
        /*EntityRegistry.registerModEntity(WCEntitySmartBoat.class, "WCEntitySmartBoat", 2, Watercraft.instance, 80, 3, true);*/
    }
    
}
