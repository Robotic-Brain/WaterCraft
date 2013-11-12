package dgrxf.watercraft.util;

import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.lib.ModInfo;

@SideOnly(Side.CLIENT)
public class TranslationHelper {
    
    /**
     * Base Prefix
     */
    public static final String BASE_KEY = "strings." + ModInfo.MODID + ":";
    
    /**
     * Translation Keys
     */
    public static final String TAPE_MEASURE_START = BASE_KEY + "tape_measure_start";
    public static final String TAPE_MEASURE_END = BASE_KEY + "tape_measure_end";
    public static final String TAPE_MEASURE_DISTANCE = BASE_KEY + "tape_measure_distance";
    
    public static final String TOOLBOX_CONTAINS = BASE_KEY + "toolbox_contains";
    public static final String TOOLBOX_CONTAINS_NOTING = BASE_KEY + "toolbox_contains_nothing";
    public static final String TOOLBOX_EMPTY = BASE_KEY + "toolbox_empty";
    public static final String TOOLBOX_OWNER = BASE_KEY + "toolbox_owner";
    public static final String TOOLBOX_NO_OWNER = BASE_KEY + "toolbox_no_owner";
    
    /**
     * Simple Wrapper
     * 
     * @param key Translation Key
     * @param params Additional Params
     * @return Translated string
     */
    public static String translate(String key, Object... params) {
        return StatCollector.translateToLocalFormatted(key, params);
    }
}
