package dgrxf.watercraft.item;

/**
 * Class Made By: ???
 * 
 * Class Last Edited By: ???
 * Class Last Edited On: 07/11/2013
 * 						 DD/MM/YYYY
 * 
 */

import cpw.mods.fml.common.registry.GameRegistry;
import dgrxf.watercraft.lib.ItemInfo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ModItems {
    
    public static Item boat;
    public static Item tapeMeasure;
    public static Item telescope;
    public static Item boatChest;
    public static Item lavaBoat;
    public static Item vanillaBoat;
    public static Item flag;
    
    public static void init() {
    	vanillaBoat = new ItemBoat();
        boat = new ItemDumbBoat();
        tapeMeasure = new ItemTapeMeasure();
        telescope = new ItemTelescope();
        boatChest = new ItemBoatChest();
        lavaBoat = new ItemLavaBoat();
        flag = new ItemFlag();
        
        GameRegistry.registerItem(flag, ItemInfo.FLAG_UNLOCALIZED_NAME);
    }
}
