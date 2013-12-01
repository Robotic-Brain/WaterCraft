package dgrxf.watercraft.lib;

/**
 * Class Made By: ???
 * 
 * Class Last Edited By: ??? Class Last Edited On: 07/11/2013 DD/MM/YYYY
 * 
 */

public class ModInfo {
    
    public static final String MODID        = "watercraft";
    public static final String VERSION      = "@VERSION@";
    public static final String NAME         = "WaterCraft";
    public static final String CHANNEL      = MODID;
    public static final String COMMON_PROXY = "dgrxf.watercraft.proxy.CommonProxy";
    public static final String CLIENT_PROXY = "dgrxf.watercraft.proxy.ClientProxy";
    
    public static final String MISC_TAB     = "wcmisc";
    public static final String BOATS_TAB    = "wcboats";
    public static final String BUOYS_TAB    = "wcbouys";
    
    /**
     * This will be replaced by ant on building with the HEAD SHA
     */
    public static final String BUILD_SHA    = "@GIT_SHA@";
    
    public static String getMODID() {
        return "[" + MODID + "]";
    }
}
