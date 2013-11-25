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
import dgrxf.watercraft.item.boat.ItemBoat;
import dgrxf.watercraft.item.boat.ItemHull;
import dgrxf.watercraft.item.boat.ItemModularBoat;
import dgrxf.watercraft.item.buoy.ItemFlag;
import dgrxf.watercraft.item.toolbox.ItemPadlock;

public class ModItems {
    
    public static Item tapeMeasure;
    public static Item telescope;
    public static Item boatVanilla;
    public static Item flag;
    public static Item iceBoat;
    public static Item blockPlacer;
    public static Item rope;
    public static Item padlock;
    public static Item calculator;
    public static Item key;
    public static Item moduleBoat;
    public static Item boatHull;
    
    public static void init() {
    	boatVanilla = new ItemBoat();
        tapeMeasure = new ItemTapeMeasure();
        telescope = new ItemTelescope();
        flag = new ItemFlag();
        rope = new ItemRope();
        padlock = new ItemPadlock();
        calculator = new ItemCalculator();
        key = new ItemKey();
        moduleBoat = new ItemModularBoat();
        boatHull = new ItemHull();
    }
}
