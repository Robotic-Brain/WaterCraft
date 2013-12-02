package dgrxf.watercraft.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.registry.GameRegistry;
import dgrxf.watercraft.block.buoy.BuoyBlock;
import dgrxf.watercraft.block.port.MarkerBlock;
import dgrxf.watercraft.item.ItemBlockChest;
import dgrxf.watercraft.item.ItemBlockLiquidTank;
import dgrxf.watercraft.item.buoy.ItemBlockBuoy;
import dgrxf.watercraft.item.toolbox.ItemBlockToolBox;
import dgrxf.watercraft.lib.MiscInfo;
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
 * 
 * ModBlocks
 *
 * Add Blocks to this enum
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 * <pre>
 * Constructor:
 *      Default ID,
 *      UnlocalizedName,
 *      Block.class,
 *      [ItemBlock.class,
 *      TileEntity.class]
 * </pre>
 * 
 */
public enum ModBlocks {
    
    BUOY                (701, "buoyBlockName", BuoyBlock.class, ItemBlockBuoy.class, WCTileEntityBuoy.class),
    FREEZER             (703, "freezerBlockName", WaterFreezerBlock.class, null, WCTileEntityFreezer.class),
    TOOLBOX             (706, "toolBoxBlockName", ToolBoxBlock.class, ItemBlockToolBox.class, WCTileEntityToolBox.class),
    WC_CHEST            (708, "wcChestName", WCChest.class, ItemBlockChest.class, WCTileEntityChest.class),
    LOCK_ASSEMBLER      (709, "lockAssemblerName", LockAssemblerBlock.class, null, WCTileEntityLockAssembler.class),
    TANK                (710, "wcLiquidTank", LiquidTankBlock.class, ItemBlockLiquidTank.class, WCTileEntityLiquidStorageTank.class),
    BOAT_ASSEMBLER      (711, "boatAssemblerName", BoatModuleAssemblerBlock.class, null, WCTileEntityBoatAssembler.class),
    PHANTOM_MULTIBLOCK  (713, "phantomMultiblock", MultiblockPhantom.class, null, TEMultiblockPhantom.class),
    CRANE               (714, "craneBlockName", CraneBlock.class, null, WCTileEntityCrane.class),
    MARKER              (715, "markerBlock", MarkerBlock.class);
    
    /************************************************************************************
     * 
     * Fields
     * 
     ***********************************************************************************/
    
    private Block                       instance;
    
    /**
     * Stores Block Id
     */
    private int                         id;
    /**
     * Default Id
     */
    private int                         defaultId;
    
    private String                      unlocalizedName;
    private String                      tileEntityKey;
    
    private Class<? extends Block>      blockClass;
    private Class<? extends ItemBlock>  itemClass;
    private Class<? extends TileEntity> teClass;
    
    /************************************************************************************
     * 
     * Constants
     * 
     ***********************************************************************************/
    
    private static final int            INVALID_BLOCK_ID = -1;
    private static final String         TE_PREFIX        = "WCTeKey";
    
    /************************************************************************************
     * 
     * Constructors
     * 
     ***********************************************************************************/
    
    /**
     * Adds Block without TE or ItemBlock
     * 
     * @param id
     *            Default Block Id
     * @param name
     *            Unlocalized Name
     * @param bClazz
     *            Block Class
     */
    private ModBlocks(int id, String name, Class<? extends Block> bClazz) {
        this(id, name, bClazz, null, null);
    }
    
    /**
     * Registers Block with TE and ItemBlock
     * 
     * @param id
     *            Default Block Id
     * @param name
     *            Unlocalized Name
     * @param bClazz
     *            Block Class
     * @param iClazz
     *            ItemBlock class
     * @param tClazz
     *            TileEntity Class
     */
    private ModBlocks(int id, String name, Class<? extends Block> bClazz, Class<? extends ItemBlock> iClazz, Class<? extends TileEntity> tClazz) {
        this.instance = null;
        
        this.id = INVALID_BLOCK_ID;
        this.defaultId = id;
        this.unlocalizedName = MiscInfo.RESOURCE_PREFIX + name;
        this.blockClass = bClazz;
        this.itemClass = iClazz;
        this.teClass = tClazz;
        
        // Automatically use ItemBlock.class if none given
        if (this.itemClass == null) {
            this.itemClass = ItemBlock.class;
        }
        
        if (this.teClass != null) {
            this.tileEntityKey = TE_PREFIX + name;
        } else {
            // just to make sure we are consistent
            this.tileEntityKey = null;
        }
    }
    
    /************************************************************************************
     * 
     * Accesors
     * 
     ***********************************************************************************/
    
    /**
     * Get the Block instance
     * 
     * @return Block Instance
     */
    public Block getBlock() {
        return this.instance;
    }
    
    /**
     * Get the Block ID
     * 
     * @return Block ID
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * Get the Unlocalized Name
     * 
     * @return unlocalizedName
     */
    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }
    
    /**
     * Get the TE Key used for this blocks TE
     * 
     * @return teKey
     */
    public String getTileEntityKey() {
        return this.tileEntityKey;
    }
    
    /**
     * Get the ItemBlock class used for this block
     * 
     * @return itemBlock class
     */
    public Class<? extends ItemBlock> getItemClass() {
        return this.itemClass;
    }
    
    /**
     * Get the TileEntity Class used for this blocks TE
     * 
     * @return teClass
     */
    public Class<? extends TileEntity> getTileEntityClass() {
        return this.teClass;
    }
    
    /************************************************************************************
     * 
     * Static Helpers
     * 
     ***********************************************************************************/
    
    public static void loadIdsFromConfig(Configuration config) {
        for (ModBlocks block : ModBlocks.values()) {
            block.id = config.getBlock(block.unlocalizedName, block.defaultId).getInt();
        }
    }
    
    public static void registerBlocks() {
        for (ModBlocks block : ModBlocks.values()) {
            if (block.instance != null) {
                throw new RuntimeException("registerBlocks() Should only be called once! (Thrown by: " + block + ")");
            } else if (block.id == INVALID_BLOCK_ID) {
                throw new RuntimeException("Block has no valid Id set! (Thrown by: " + block + ")");
            }
            try {
                block.instance = block.blockClass.getConstructor(Integer.TYPE).newInstance(block.id);
                block.instance.setUnlocalizedName(block.unlocalizedName);
                
                GameRegistry.registerBlock(block.instance, block.itemClass, block.unlocalizedName);
                
                if (block.teClass != null) {
                    GameRegistry.registerTileEntity(block.teClass, block.tileEntityKey);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
