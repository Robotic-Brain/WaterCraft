package dgrxf.watercraft.config;

/**
 * Class Made By: ???
 * 
 * Class Last Edited By: ???
 * Class Last Edited On: 07/11/2013
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
            ItemInfo.BOAT_ID = config.getItem(ItemInfo.BOAT_KEY, ItemInfo.BOAT_ID_DEFAULT).getInt() - 256;
            ItemInfo.TAPE_MEASURE_ID = config.getItem(ItemInfo.TAPE_MEASURE_KEY, ItemInfo.TAPE_MEASURE_ID_DEFAULT).getInt() - 256;
            ItemInfo.TELESCOPE_ID = config.getItem(ItemInfo.TELESCOPE_KEY, ItemInfo.TELESCOPE_ID_DEFAULT).getInt() - 256;
            ItemInfo.ENGINE_ID = config.getItem(ItemInfo.ENGINE_KEY, ItemInfo.ENGINE_ID_DEFAULT).getInt() - 256;
            ItemInfo.BOATCHEST_ID = config.getItem(ItemInfo.BOATCHEST_KEY, ItemInfo.BOATCHEST_ID_DEFAULT).getInt() - 256;
            ItemInfo.LAVABOAT_ID = config.getItem(ItemInfo.LAVABOAT_KEY, ItemInfo.LAVABOAT_ID_DEFAULT).getInt() - 256;
            
            // Blocks
            BlockInfo.BUOY_ID = config.getBlock(BlockInfo.BUOY_KEY, BlockInfo.BUOY_ID_DEFAULT).getInt();
            BlockInfo.CONTROL_UNIT_DOCK_ID = config.getBlock(BlockInfo.CONTROL_UNIT_DOCK_KEY, BlockInfo.CONTROL_UNIT_DOCK_ID_DEFAULT).getInt();
            BlockInfo.FREEZER_ID = config.getBlock(BlockInfo.FREEZER_KEY, BlockInfo.FREEZER_ID_DEFAULT).getInt();
            BlockInfo.SMELTER_ID = config.getBlock(BlockInfo.SMELTER_KEY, BlockInfo.SMELTER_ID_DEFAULT).getInt();
            BlockInfo.DROPZONE_ID = config.getBlock(BlockInfo.DROPZONE_KEY, BlockInfo.BUOY_ID_DEFAULT).getInt();
            BlockInfo.TOOLBOX_ID = config.getBlock(BlockInfo.TOOLBOX_KEY, BlockInfo.TOOLBOX_ID_DEFAULT).getInt();
            
            // Misc
            
            MiscInfo.BOUY_RANGE = config.get(MiscInfo.CATEGORY, MiscInfo.BOUY_KEY, MiscInfo.BOUY_DEFAULT_RANGE).getInt();
            MiscInfo.TICKING_RATE = config.get(MiscInfo.CATEGORY, MiscInfo.TICKING_KEY, MiscInfo.TICKING_DEFAULT_RATE).getInt();
            
        } catch (Exception e) {
            LogHelper.config("There was a problem while loading the config, Please report this.");
            e.printStackTrace();
        } finally {
            if (config.hasChanged()) {
                config.save();
                LogHelper.info("Config saved!");
            }
        }
    }
}
