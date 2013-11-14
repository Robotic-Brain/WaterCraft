package dgrxf.watercraft.config;

/**
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By: Drunk Mafia
 * Class Last Edited On: 14/11/2013
 * 						 DD/MM/YYYY
 * 
 */

import java.io.File;

import net.minecraftforge.common.Configuration;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.lib.MiscInfo;
import dgrxf.watercraft.util.LogHelper;

public class ConfigurationHandler {
    public static void init(File file) {
        Configuration config = new Configuration(file);
        
        try {
            config.load();
            
            // Items
            ItemInfo.DUMB_BOAT_ID = config.get(ItemInfo.CATEGORY, ItemInfo.DUMB_BOAT_KEY, ItemInfo.DUMB_BOAT_ID_DEFAULT).getInt() - 256;
            ItemInfo.BOAT_ID = config.get(ItemInfo.CATEGORY, ItemInfo.BOAT_KEY, ItemInfo.BOAT_ID_DEFAULT).getInt() - 256;
            ItemInfo.TAPE_MEASURE_ID = config.get(ItemInfo.CATEGORY, ItemInfo.TAPE_MEASURE_KEY, ItemInfo.TAPE_MEASURE_ID_DEFAULT).getInt() - 256;
            ItemInfo.TELESCOPE_ID = config.get(ItemInfo.CATEGORY, ItemInfo.TELESCOPE_KEY, ItemInfo.TELESCOPE_ID_DEFAULT).getInt() - 256;
            ItemInfo.ENGINE_ID = config.get(ItemInfo.CATEGORY, ItemInfo.ENGINE_KEY, ItemInfo.ENGINE_ID_DEFAULT).getInt() - 256;
            ItemInfo.BOATCHEST_ID = config.get(ItemInfo.CATEGORY, ItemInfo.BOATCHEST_KEY, ItemInfo.BOATCHEST_ID_DEFAULT).getInt() - 256;
            ItemInfo.LAVABOAT_ID = config.get(ItemInfo.CATEGORY, ItemInfo.LAVABOAT_KEY, ItemInfo.LAVABOAT_ID_DEFAULT).getInt() - 256;
            ItemInfo.FLAG_ID = config.get(ItemInfo.CATEGORY, ItemInfo.FLAG_KEY, ItemInfo.FLAG_ID_DEFAULT).getInt() - 256;
            ItemInfo.ICEBOAT_ID = config.get(ItemInfo.CATEGORY,ItemInfo.ICEBOAT_KEY, ItemInfo.ICEBOAT_ID_DEFAULT).getInt() - 256;
            ItemInfo.ROPE_ID = config.get(ItemInfo.CATEGORY, ItemInfo.ROPE_KEY, ItemInfo.ROPE_ID_DEFAULT).getInt() - 256;
            ItemInfo.PADLOCK_ID = config.get(ItemInfo.CATEGORY, ItemInfo.PADLOCK_KEY, ItemInfo.PADLOCK_ID_DEFAULT).getInt() - 256;
       
            // Blocks
            BlockInfo.BUOY_ID = config.get(BlockInfo.CATEGORY, BlockInfo.BUOY_KEY, BlockInfo.BUOY_ID_DEFAULT).getInt();
            BlockInfo.CONTROL_UNIT_DOCK_ID = config.get(BlockInfo.CATEGORY, BlockInfo.CONTROL_UNIT_DOCK_KEY, BlockInfo.CONTROL_UNIT_DOCK_ID_DEFAULT).getInt();
            BlockInfo.FREEZER_ID = config.get(BlockInfo.CATEGORY, BlockInfo.FREEZER_KEY, BlockInfo.FREEZER_ID_DEFAULT).getInt();
            BlockInfo.DROPZONE_ID = config.get(BlockInfo.CATEGORY, BlockInfo.DROPZONE_KEY, BlockInfo.DROPZONE_ID_DEFAULT).getInt();
            BlockInfo.TOOLBOX_ID = config.get(BlockInfo.CATEGORY, BlockInfo.TOOLBOX_KEY, BlockInfo.TOOLBOX_ID_DEFAULT).getInt();
            BlockInfo.BUOY_FILTER_ID = config.get(BlockInfo.CATEGORY, BlockInfo.BUOY_FILTER_KEY, BlockInfo.BUOY_FILTER_ID_DEFAULT).getInt();
            
            // Misc
            MiscInfo.BOUY_RANGE = config.get(MiscInfo.CATEGORY, MiscInfo.BOUY_KEY, MiscInfo.BOUY_DEFAULT_RANGE).getInt();
            MiscInfo.TICKING_RATE = config.get(MiscInfo.CATEGORY, MiscInfo.TICKING_KEY, MiscInfo.TICKING_DEFAULT_RATE).getInt();
            MiscInfo.DEBUG = config.get(MiscInfo.CATEGORY, MiscInfo.DEBUG_KEY, MiscInfo.DEBUG_DEFAULT).getBoolean(MiscInfo.DEBUG_DEFAULT);
            
        } catch (Exception e) {
            LogHelper.severe("There was a problem while loading the config, Please report this.");
            e.printStackTrace();
        } finally {
            if (config.hasChanged()) {
                config.save();
                LogHelper.info("Config saved!");
            }
        }
    }
}
