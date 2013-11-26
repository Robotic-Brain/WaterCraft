package dgrxf.watercraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;
import dgrxf.watercraft.block.buoy.BuoyBlock;
import dgrxf.watercraft.block.port.MarkerBlock;
import dgrxf.watercraft.item.ItemBlockChest;
import dgrxf.watercraft.item.ItemBlockLiquidTank;
import dgrxf.watercraft.item.buoy.ItemBlockBuoy;
import dgrxf.watercraft.item.toolbox.ItemBlockToolBox;
import dgrxf.watercraft.lib.BlockInfo;
import dgrxf.watercraft.tileentity.TEMultiblockPhantom;
import dgrxf.watercraft.tileentity.WCTileEntityBoatAssembler;
import dgrxf.watercraft.tileentity.WCTileEntityChest;
import dgrxf.watercraft.tileentity.WCTileEntityCrane;
import dgrxf.watercraft.tileentity.WCTileEntityFreezer;
import dgrxf.watercraft.tileentity.WCTileEntityLiquidStorageTank;
import dgrxf.watercraft.tileentity.WCTileEntityLockAssembler;
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
    public static Block		tank;
    public static Block     lockAssembler;
    public static Block 	boatAssembler;
    public static Block     dockPlatform;
    
    public static Block     phantomMultiBlock;
    public static Block     markerBlock; 
    
    public static void init() {
        //Buoy Blocks
        buoy = new BuoyBlock(BlockInfo.BUOY_ID);
        
        //controlUnitDock = new ControlBlockDock(BlockInfo.CONTROL_UNIT_DOCK_ID);
        controlUnitDock = new CraneBlock(BlockInfo.CONTROL_UNIT_DOCK_ID, Material.rock);
        freezer = new WaterFreezerBlock(BlockInfo.FREEZER_ID);
        dropZone = new DropZoneBlock();
        toolbox = new ToolBoxBlock();

        chest = new WCChest(BlockInfo.WC_CHEST_ID, 0);
        //TODO trap chest
        tank = new LiquidTankBlock(BlockInfo.TANK_ID);
        lockAssembler = new LockAssemblerBlock(BlockInfo.LOCK_ASSEMBLER_ID);
        boatAssembler = new BoatModuleAssemblerBlock(BlockInfo.BOAT_ASSEMBLER_ID);
        dockPlatform  = new DockPlatformBlock();
        
        phantomMultiBlock = new MultiblockPhantom(BlockInfo.PHANTOM_MULTIBLOCK_ID);
        markerBlock = new MarkerBlock(BlockInfo.MARKER_BLOCK_ID);
        
        GameRegistry.registerBlock(buoy, ItemBlockBuoy.class, BlockInfo.BUOY_UNLOCALIZED_NAME);
        
        GameRegistry.registerBlock(controlUnitDock, BlockInfo.CONTROL_UNIT_DOCK_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(freezer, BlockInfo.FREEZER_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(toolbox, ItemBlockToolBox.class, BlockInfo.TOOLBOX_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(chest, ItemBlockChest.class, BlockInfo.WC_CHEST_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(lockAssembler, BlockInfo.LOCK_ASSEMBLER_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(tank, ItemBlockLiquidTank.class, BlockInfo.TANK_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(boatAssembler, BlockInfo.BOAT_ASSEMBLER_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(dockPlatform, BlockInfo.PLATFORM_UNLOCALIZED_NAME);
        
        GameRegistry.registerBlock(phantomMultiBlock, BlockInfo.PHANTOM_MULTIBLOCK_UNLOCALIZED_NAME);
        GameRegistry.registerBlock(markerBlock, BlockInfo.MARKER_BLOCK_UNLOCALIZED_NAME);
        
        GameRegistry.registerTileEntity(WCTileEntityBuoy.class, BlockInfo.BUOY_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityCrane.class, BlockInfo.CONTROL_UNIT_DOCK_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityFreezer.class, BlockInfo.FREEZER_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityToolBox.class, BlockInfo.TOOLBOX_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityChest.class, BlockInfo.WC_CHEST_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityLockAssembler.class, BlockInfo.LOCK_ASSEMBLER_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityLiquidStorageTank.class, BlockInfo.TANK_TE_KEY);
        GameRegistry.registerTileEntity(WCTileEntityBoatAssembler.class, BlockInfo.BOAT_ASSEMBLER_TE_KEY);
        
        GameRegistry.registerTileEntity(TEMultiblockPhantom.class, BlockInfo.PHANTOM_MULTIBLOCK_TE_KEY);

    }
}
