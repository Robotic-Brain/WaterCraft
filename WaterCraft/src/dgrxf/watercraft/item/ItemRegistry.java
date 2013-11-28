package dgrxf.watercraft.item;

import java.lang.reflect.InvocationTargetException;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import dgrxf.watercraft.lib.MiscInfo;

public enum ItemRegistry {
    
    NONE(0, null, null);
    
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
    
    /************************************************************************************
     * 
     * Constructors
     * 
     ***********************************************************************************/
    
    private ItemRegistry(int defaultId, String name, Class<? extends Item> iClass) {
        this.id = INVALID_ITEM_ID;
        this.defaultId = defaultId;
        this.unlocalizedName = MiscInfo.RESOURCE_PREFIX + name;
        this.instance = null;
        this.clazz = iClass;
    }
    
    /************************************************************************************
     * 
     * Static Helpers
     * 
     ***********************************************************************************/
    
    public static void loadIdsFromConfig(Configuration config) {
        for (ItemRegistry item : ItemRegistry.values()) {
            item.id = config.getItem(item.unlocalizedName, item.defaultId).getInt();
        }
    }
    
    public static void registerItems() {
        for (ItemRegistry item : ItemRegistry.values()) {
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
