package dgrxf.watercraft.lib;

/**
 * Class Made By: ???
 * 
 * Class Last Edited By: ??? Class Last Edited On: 07/11/2013 DD/MM/YYYY
 * 
 */

public class ModInfo {
    
    public static final String MODID = "watercraft";
    public static final String VERSION = "Pre-Alpha 0.01";
    public static final String NAME = "WaterCraft";
    public static final String CHANNEL = MODID;
    public static final String COMMON_PROXY = "dgrxf.watercraft.proxy.CommonProxy";
    public static final String CLIENT_PROXY = "dgrxf.watercraft.proxy.ClientProxy";
    
    public static String getMODID() {
        return "[" + MODID + "]";
    }
}
