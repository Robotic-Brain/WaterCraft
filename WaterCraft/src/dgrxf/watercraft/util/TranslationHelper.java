package dgrxf.watercraft.util;

import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dgrxf.watercraft.lib.ModInfo;

/**
 * 
 * TranslationHelper
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
@SideOnly(Side.CLIENT)
public class TranslationHelper {
    
    /**
     * Base Prefix
     */
    public static final String BASE_KEY                = "strings." + ModInfo.MODID + ":";
    
    /**
     * Translation Keys
     */
    public static final String TAPE_MEASURE_START        = BASE_KEY + "tape_measure_start";
    public static final String TAPE_MEASURE_END          = BASE_KEY + "tape_measure_end";
    public static final String TAPE_MEASURE_DISTANCE     = BASE_KEY + "tape_measure_distance";
    public static final String TAPE_MEASURE_RESTART      = BASE_KEY + "tape_measure_restart";
    
    public static final String MODULE_DUPLICATE_EXCEPTION          = BASE_KEY + "module_exception";
    public static final String MODULE_INVALID_MOD_EXCEPTION        = BASE_KEY + "module_invalid_mod_exception";
    
    public static final String TOOLBOX_CONTAINS        = BASE_KEY + "toolbox_contains";
    public static final String TOOLBOX_CONTAINS_NOTING = BASE_KEY + "toolbox_contains_nothing";
    public static final String TOOLBOX_EMPTY           = BASE_KEY + "toolbox_empty";
    public static final String TOOLBOX_OWNER           = BASE_KEY + "toolbox_owner";
    public static final String TOOLBOX_NO_OWNER        = BASE_KEY + "toolbox_no_owner";
    
    public static final String TAB_MISC                = "creativeTab.misc";
    public static final String TAB_BOAT                = "creativeTab.boat";
    public static final String TAB_BOUY                = "creativeTab.bouy";
    
    /**
     * Boat Assembler GUI
     */
    public static final String BOAT_ASSEMBLER_EMPTY_FIELD = BASE_KEY + "boat_assembler_empty_field";
    public static final String BOAT_ASSEMBLER_NO_MODULE = BASE_KEY + "boat_assembler_no_module";
    
    public static final String[] FLAGS = {"white", "orange", "magenta", "light_blue", "yellow", "lime_green", "pink", "grey", "light_grey", "cyan", "purple", "blue", "brown", "green", "red", "black"};
    
    /**
     * Simple Wrapper
     * 
     * @param key
     *            Translation Key
     * @param params
     *            Additional Params
     * @return Translated string
     */
    public static String translate(String key, Object... params) {
        return StatCollector.translateToLocalFormatted(key, params);
    }
    
    /**
     * Builds the localization key
     * @param category  Category to put it in
     * @param key       Actuall key
     * @return <category>.watercraft:<key>
     */
    public static String buildKey(String category, String key) {
        return category + "." + ModInfo.MODID + ":" + key;
    }
    
    public static String buildKey(String key) {
        return buildKey("strings", key);
    }
}
