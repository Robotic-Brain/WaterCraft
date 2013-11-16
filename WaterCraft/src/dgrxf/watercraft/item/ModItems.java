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
import cpw.mods.fml.common.registry.GameRegistry;
import dgrxf.watercraft.item.boat.ItemBoat;
import dgrxf.watercraft.item.boat.ItemBoatChest;
import dgrxf.watercraft.item.boat.ItemDumbBoat;
import dgrxf.watercraft.item.boat.ItemIceBoat;
import dgrxf.watercraft.item.boat.ItemLavaBoat;
import dgrxf.watercraft.item.buoy.ItemFlag;
import dgrxf.watercraft.item.toolbox.ItemPadlock;
import dgrxf.watercraft.lib.ItemInfo;

public class ModItems {
    
    public static Item boatSimple;
    public static Item tapeMeasure;
    public static Item telescope;
    public static Item boatChest;
    public static Item lavaBoat;
    public static Item boatVanilla;
    public static Item flag;
    public static Item iceBoat;
    public static Item blockPlacer;
    public static Item rope;
    public static Item padlock;
    public static Item calculator;
    public static Item key;
    
    public static void init() {
    	boatVanilla = new ItemBoat();
        boatSimple = new ItemDumbBoat();
        tapeMeasure = new ItemTapeMeasure();
        telescope = new ItemTelescope();
        //boatChest = new ItemBoatChest();
        //lavaBoat = new ItemLavaBoat();
        flag = new ItemFlag();
        iceBoat = new ItemIceBoat();
        rope = new ItemRope();
        padlock = new ItemPadlock();
        calculator = new ItemCalculator();
        key = new ItemKey();
    }
}
