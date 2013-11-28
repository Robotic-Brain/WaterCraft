package dgrxf.watercraft.lib;

import dgrxf.watercraft.client.gui.GuiColor;

public class MiscInfo {
    
    /*************
     * Strings
     *************/
    
    public static final String  CATEGORY             = "Misc";
    
    public static final String  DEBUG_KEY            = "Debugging";
    public static final String  BOUY_KEY             = "Bouy Search Range";
    public static final String  TICKING_KEY          = "Ticking Rate";
    
    public static final String SHIFT_CLICKING_INFO   = "Hold" + GuiColor.LIGHTBLUE + " SHIFT " + GuiColor.LIGHTGRAY + "to view more information";
    
    /* Localization Prefixes */
    public static final String RESOURCE_PREFIX = ModInfo.MODID.toLowerCase() + ":";
    
    /*************
     * Integers
     *************/
    
    public static final int     BOUY_DEFAULT_RANGE   = 10;
    public static int           BOUY_RANGE;
    
    public static final int     TICKING_DEFAULT_RATE = 30;
    public static int           TICKING_RATE;
    
    /*************
     * Boolean
     *************/
    
    public static final boolean DEBUG_DEFAULT        = false;
    public static boolean       DEBUG;
}
