package dgrxf.watercraft.item;

import java.lang.reflect.InvocationTargetException;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import dgrxf.watercraft.item.boat.ItemBoat;
import dgrxf.watercraft.item.boat.ItemModularBoat;
import dgrxf.watercraft.item.boat.ItemModule;
import dgrxf.watercraft.item.buoy.ItemFlag;
import dgrxf.watercraft.item.toolbox.ItemPadlock;
import dgrxf.watercraft.lib.MiscInfo;

/**
 * Add new Items to this Enum
 * 
 * <pre>
 * Constructor:
 *      Default ID,
 *      UnlocalizedName,
 *      Item.class
 * </pre>
 *
 */
public enum ModItems {
    
    VANILLA_BOAT    (11700, "wcboat", ItemBoat.class),
    TAPE_MEASURE    (11707, "TapeItemName", ItemTapeMeasure.class),
    TELESCOPE       (11708, "TelescopeName", ItemTelescope.class),
    FLAGS           (11713, "flag", ItemFlag.class),
    ROPE            (11715, "rope", ItemRope.class),
    PADLOCK         (11716, "padlock", ItemPadlock.class),
    CALCULATOR      (11717, "calculator", ItemCalculator.class),
    KEY             (11718, "key", ItemKey.class),
    MODULAR_BOAT    (11720, "wcboatmodular", ItemModularBoat.class),
    BOAT_MODULES    (11719, "boatModules", ItemModule.class),
    TRADER_SPAWNER  (11730, "spawnTrader", ItemTraderSpawner.class);
    
    /************************************************************************************
     * 
     * Fields
     * 
     ***********************************************************************************/
    
    /**
     * Stores Item ID
     */
    private int                   id;
    /**
     * Default ID
     */
    private int                   defaultId;
    
    /**
     * Stores UnlocalizedName
     */
    private String                unlocalizedName;
    
    private Item                  instance;
    private Class<? extends Item> clazz;
    
    /************************************************************************************
     * 
     * Constants
     * 
     ***********************************************************************************/
    
    private static final int      INVALID_ITEM_ID = -1;
    private static final int ITEM_INDEX_SHIFT = 256;
    
    /************************************************************************************
     * 
     * Constructors
     * 
     ***********************************************************************************/
    
    private ModItems(int defaultId, String name, Class<? extends Item> iClass) {
        this.id = INVALID_ITEM_ID;
        this.defaultId = defaultId;
        this.unlocalizedName = MiscInfo.RESOURCE_PREFIX + name;
        this.instance = null;
        this.clazz = iClass;
    }
    
    /************************************************************************************
     * 
     * Accesors
     * 
     ***********************************************************************************/
    
    /**
     * Get the item instance
     * 
     * @return item instance
     */
    public Item getItem() {
        return this.instance;
    }
    
    /**
     * Get the item ID
     * 
     * @return item id
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * Get the shifted item ID
     * 
     * @return item id + 256
     */
    public int getShifted() {
        return this.id + ITEM_INDEX_SHIFT;
    }
    
    /**
     * Get the unlocalized name
     * 
     * @return unlocalizedName
     */
    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }
    
    /************************************************************************************
     * 
     * Static Helpers
     * 
     ***********************************************************************************/
    
    public static void loadIdsFromConfig(Configuration config) {
        for (ModItems item : ModItems.values()) {
            item.id = config.getItem(item.unlocalizedName, item.defaultId).getInt();
        }
    }
    
    public static void registerItems() {
        for (ModItems item : ModItems.values()) {
            if (item.instance != null) {
                throw new RuntimeException("registerItems() Should only be called once! (Thrown by: " + item + ")");
            } else if (item.id == INVALID_ITEM_ID) {
                throw new RuntimeException("Item has no valid Id set! (Thrown by: " + item + ")");
            }
            
            try {
                item.instance = item.clazz.getConstructor(Integer.TYPE).newInstance(item.id);
                item.instance.setUnlocalizedName(item.unlocalizedName);
                
                GameRegistry.registerItem(item.instance, item.unlocalizedName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
