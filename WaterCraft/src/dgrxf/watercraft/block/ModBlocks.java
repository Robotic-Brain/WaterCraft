package dgrxf.watercraft.block;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import dgrxf.watercraft.block.buoy.BuoyBlock;
import dgrxf.watercraft.item.ItemBlockChest;
import dgrxf.watercraft.item.buoy.ItemBlockBuoy;
import dgrxf.watercraft.item.toolbox.ItemBlockToolBox;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.tileentity.WCTileEntityChest;
import dgrxf.watercraft.tileentity.WCTileEntityControlUnitDock;
import dgrxf.watercraft.tileentity.WCTileEntityFreezer;
import dgrxf.watercraft.tileentity.WCTileEntityToolBox;
import dgrxf.watercraft.tileentity.buoy.WCTileEntityBuoy;

/**
 * Mod Blocks
 * 
 * @author Drunk Mafia
 * 
 */
public class ModBlocks {
    
    public static BuoyBlock buoy;
    public static Block     controlUnitDock;
    public static Block     freezer;
    public static Block     dropZone;
    public static Block     toolbox;
    public static Block     chest;
    
    public static void init() {
        //Buoy Blocks
        buoy = new BuoyBlock(BlockInfo.BUOY_ID);
        
        controlUnitDock = new ControlBlockDock(BlockInfo.CONTROL_UNIT_DOCK_ID);
        freezer = new WaterFreezerBlock(BlockInfo.FREEZER_ID);
        dropZone = new DropZoneBlock();
        toolbox = new ToolBoxBlock();
        chest = new WCChest(BlockInfo.WC_CHEST_ID, 0);
        
        GameRegistry.registerBlock(buoy, ItemBlockBuoy.class, BlockInfo.BUOY_UNLOCALIZED_NAME);
        
        GameRegistry.registerBlock(controlUnitDock, BlockInfo.CONTROL_UNIT_DOCK_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(freezer, BlockInfo.FREEZER_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(toolbox, ItemBlockToolBox.class, BlockInfo.TOOLBOX_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(chest, ItemBlockChest.class, BlockInfo.WC_CHEST_UNLOCALIZED_NAME);
        
        GameRegistry.registerTileEntity(WCTileEntityBuoy.class, BlockInfo.BUOY_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityControlUnitDock.class, BlockInfo.CONTROL_UNIT_DOCK_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityFreezer.class, BlockInfo.FREEZER_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityToolBox.class, BlockInfo.TOOLBOX_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityChest.class, BlockInfo.WC_CHEST_TE_KEY);
    }
}
