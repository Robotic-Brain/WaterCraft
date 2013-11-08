package dgrxf.watercraft.block;

/**
 * Class Made By: Drunk Mafia
 * 
 * Class Last Edited By: Drunk Mafia
 * Class Last Edited On: 08/11/2013
 * 						 DD/MM/YYYY
 * 
 */

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dgrxf.watercraft.item.ItemBlockBouy;
import dgrxf.watercraft.item.ItemBlockControlUnit;
import dgrxf.watercraft.item.ItemBlockToolBox;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.tileentity.WCTileEntityBuoy;
import dgrxf.watercraft.tileentity.WCTileEntityControlUnitDock;
import dgrxf.watercraft.tileentity.WCTileEntityFreezer;
import dgrxf.watercraft.tileentity.WCTileEntitySmelter;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

public class ModBlocks {
    
    public static WCBlock bouy;
    public static WCBlock controlUnitDock;
    public static WCBlock freezer;
    //public static WCBlock smelter;
    public static WCBlock dropZone;
    public static WCBlock toolbox;
    
    public static void init() {
        bouy = new BuoyBlock(BlockInfo.BOUY_ID);
        controlUnitDock = new ControlBlockDock(BlockInfo.CONTROL_UNIT_DOCK_ID);
        freezer = new WaterFreezerBlock(BlockInfo.FREEZER_ID);
        dropZone = new DropZoneBlock();
        toolbox = new ToolBoxBlock();
        
        GameRegistry.registerBlock(bouy, ItemBlockBouy.class, BlockInfo.BOUY_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(controlUnitDock, ItemBlockControlUnit.class, BlockInfo.CONTROL_UNIT_DOCK_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(freezer, BlockInfo.FREEZER_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(toolbox, ItemBlockToolBox.class, BlockInfo.TOOLBOX_UNLOCALIZED_NAME);
        
        GameRegistry.registerTileEntity(WCTileEntityBuoy.class, "WCTileEntityBouy");
        GameRegistry.registerTileEntity(WCTileEntityControlUnitDock.class, "WCTileEntityControlUnitDock");
        GameRegistry.registerTileEntity(WCTileEntityFreezer.class, "WCTileEntityFreezer");
        GameRegistry.registerTileEntity(WCTileEntityToolBox.class, "WCTileEntityToolBox");
    }
}
