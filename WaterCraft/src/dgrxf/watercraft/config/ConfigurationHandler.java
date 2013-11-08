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
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.lib.ItemInfo;
import dgrxf.watercraft.lib.ModInfo;

public class ConfigurationHandler {
    public static void init(File file) {
        Configuration config = new Configuration(file);
        
        try {
            config.load();
            
            // Items
            ItemInfo.BOAT_ID = config.getItem(ItemInfo.BOAT_KEY, ItemInfo.BOAT_ID_DEFAULT).getInt() - 256;
            
            // Blocks
            BlockInfo.BOUY_ID = config.getBlock(BlockInfo.BOUY_KEY, BlockInfo.BOUY_ID_DEFAULT).getInt();
            BlockInfo.CONTROL_UNIT_DOCK_ID = config.getBlock(BlockInfo.CONTROL_UNIT_DOCK_KEY, BlockInfo.CONTROL_UNIT_DOCK_ID_DEFAULT).getInt();
            BlockInfo.FREEZER_ID = config.getBlock(BlockInfo.FREEZER_KEY, BlockInfo.FREEZER_ID_DEFAULT).getInt();
            BlockInfo.SMELTER_ID = config.getBlock(BlockInfo.SMELTER_KEY, BlockInfo.SMELTER_ID_DEFAULT).getInt();
            BlockInfo.DROPZONE_ID = config.getBlock(BlockInfo.DROPZONE_KEY, BlockInfo.BOUY_ID_DEFAULT).getInt();
            BlockInfo.TOOLBOX_ID = config.getBlock(BlockInfo.TOOLBOX_KEY, BlockInfo.TOOLBOX_ID_DEFAULT).getInt();
            
        } catch (Exception e) {
            FMLLog.log(Level.CONFIG, ModInfo.getMODID() + " There was a problem while loading the config, Please report this.", e);
            e.printStackTrace();
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}
