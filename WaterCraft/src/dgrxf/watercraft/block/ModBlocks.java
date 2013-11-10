package dgrxf.watercraft.block;

import cpw.mods.fml.common.registry.GameRegistry;
import dgrxf.watercraft.item.ItemBlockBuoy;
import dgrxf.watercraft.item.ItemBlockToolBox;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.tileentity.WCTileEntityBuoy;
import dgrxf.watercraft.tileentity.WCTileEntityControlUnitDock;
import dgrxf.watercraft.tileentity.WCTileEntityFreezer;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;

/**
 * Mod Blocks
 * 
 * @author Drunk Mafia
 * 
 */
public class ModBlocks {
    
    public static WCBlock buoy;
    public static WCBlock controlUnitDock;
    public static WCBlock freezer;
    public static WCBlock dropZone;
    public static WCBlock toolbox;
    
    public static void init() {
        buoy = new BuoyBlock(BlockInfo.BUOY_ID);
        controlUnitDock = new ControlBlockDock(BlockInfo.CONTROL_UNIT_DOCK_ID);
        freezer = new WaterFreezerBlock(BlockInfo.FREEZER_ID);
        dropZone = new DropZoneBlock();
        toolbox = new ToolBoxBlock();
        
        GameRegistry.registerBlock(buoy, ItemBlockBuoy.class, BlockInfo.BUOY_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(controlUnitDock, BlockInfo.CONTROL_UNIT_DOCK_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(freezer, BlockInfo.FREEZER_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(toolbox, ItemBlockToolBox.class, BlockInfo.TOOLBOX_UNLOCALIZED_NAME);
        
        GameRegistry.registerTileEntity(WCTileEntityBuoy.class, BlockInfo.BUOY_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityControlUnitDock.class, BlockInfo.CONTROL_UNIT_DOCK_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityFreezer.class, BlockInfo.FREEZER_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityToolBox.class, BlockInfo.TOOLBOX_TE_KEY);
    }
}
