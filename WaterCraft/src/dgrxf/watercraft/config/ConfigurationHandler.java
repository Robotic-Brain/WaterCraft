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
import dgrxf.watercraft.block.ModBlocks;
import dgrxf.watercraft.item.ItemRegistry;
import dgrxf.watercraft.lib.MiscInfo;
import dgrxf.watercraft.util.LogHelper;

public class ConfigurationHandler {
    public static void init(File file) {
        Configuration config = new Configuration(file);
        
        try {
            config.load();
            
            // Items
            ItemRegistry.loadIdsFromConfig(config);
            /*ItemInfo.DUMB_BOAT_ID = config.getItem(ItemInfo.CATEGORY, ItemInfo.DUMB_BOAT_KEY, ItemInfo.DUMB_BOAT_ID_DEFAULT).getInt();
            ItemInfo.BOAT_ID = config.getItem(ItemInfo.CATEGORY, ItemInfo.BOAT_KEY, ItemInfo.BOAT_ID_DEFAULT).getInt();
            ItemInfo.TAPE_MEASURE_ID = config.getItem(ItemInfo.CATEGORY, ItemInfo.TAPE_MEASURE_KEY, ItemInfo.TAPE_MEASURE_ID_DEFAULT).getInt();
            ItemInfo.TELESCOPE_ID = config.getItem(ItemInfo.CATEGORY, ItemInfo.TELESCOPE_KEY, ItemInfo.TELESCOPE_ID_DEFAULT).getInt();
            ItemInfo.ENGINE_ID = config.getItem(ItemInfo.CATEGORY, ItemInfo.ENGINE_KEY, ItemInfo.ENGINE_ID_DEFAULT).getInt();
            ItemInfo.FLAG_ID = config.getItem(ItemInfo.CATEGORY, ItemInfo.FLAG_KEY, ItemInfo.FLAG_ID_DEFAULT).getInt();
            ItemInfo.ROPE_ID = config.getItem(ItemInfo.CATEGORY, ItemInfo.ROPE_KEY, ItemInfo.ROPE_ID_DEFAULT).getInt();
            ItemInfo.PADLOCK_ID = config.getItem(ItemInfo.CATEGORY, ItemInfo.PADLOCK_KEY, ItemInfo.PADLOCK_ID_DEFAULT).getInt();
            ItemInfo.CALCULATOR_ID = config.getItem(ItemInfo.CATEGORY, ItemInfo.CALCULATOR_KEY, ItemInfo.CALCULATOR_ID_DEFAULT).getInt();
            ItemInfo.KEY_ID = config.getItem(ItemInfo.CATEGORY, ItemInfo.KEY_KEY, ItemInfo.KEY_ID_DEFAULT).getInt();
            ItemInfo.MODULAR_BOAT_ID = config.getItem(ItemInfo.CATEGORY, ItemInfo.BOAT_MODULAR_KEY, ItemInfo.MODULAR_BOAT_ID_DEFAULT).getInt();
            ItemInfo.BOAT_HULL_ID = config.getItem(ItemInfo.CATEGORY, ItemInfo.BOAT_HULL_KEY, ItemInfo.BOAT_HULL_ID_DEFAULT).getInt();
            ItemInfo.SPAWNTRADER_ID = config.getItem(ItemInfo.CATEGORY, ItemInfo.SPAWNTRADER_KEY, ItemInfo.SPAWNTRADER_ID_DEFAULT).getInt();*/
            
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
