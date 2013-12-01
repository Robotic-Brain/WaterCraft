package dgrxf.watercraft.config;

import java.io.File;

import net.minecraftforge.common.Configuration;
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.item.ModItems;
import dgrxf.watercraft.lib.MiscInfo;
import dgrxf.watercraft.util.LogHelper;

/**
 * 
 * ConfigurationHandler
 *
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class ConfigurationHandler {
    public static void init(File file) {
        Configuration config = new Configuration(file);
        
        try {
            config.load();
            
            // Items
            ModItems.loadIdsFromConfig(config);
            
            // Blocks
            ModBlocks.loadIdsFromConfig(config);
            
            // Misc
            MiscInfo.BOUY_RANGE = config.get(MiscInfo.CATEGORY, MiscInfo.BOUY_KEY, MiscInfo.BOUY_DEFAULT_RANGE).getInt();
            MiscInfo.TICKING_RATE = config.get(MiscInfo.CATEGORY, MiscInfo.TICKING_KEY, MiscInfo.TICKING_DEFAULT_RATE).getInt();
            MiscInfo.DEBUG = config.get(MiscInfo.CATEGORY, MiscInfo.DEBUG_KEY, MiscInfo.DEBUG_DEFAULT).getBoolean(MiscInfo.DEBUG_DEFAULT);
            
        } catch (Exception e) {
            LogHelper.config("There was a problem while loading the config, Please report this to this mod's authors.");
            LogHelper.severe(e);
        } finally {
            if (config.hasChanged()) {
                config.save();
                LogHelper.config("Config saved!");
            }
        }
    }
}
