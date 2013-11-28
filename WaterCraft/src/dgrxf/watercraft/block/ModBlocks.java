package dgrxf.watercraft.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.registry.GameRegistry;
import dgrxf.watercraft.block.buoy.BuoyBlock;
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
import dgrxf.watercraft.util.LogHelper;

/**
 * Add Blocks to this enum
 * 
 * <pre>
 * Constructor:
 *      Default ID,
 *      UnlocalizedName,
 *      Block.class,
 *      [[ItemBlock.class], [teKey, TileEntity.class]]
 * </pre>
 * 
 */
public enum ModBlocks {
    
    BUOY                (MiscInfo.BASE_BLOCK_ID + 1, "buoyBlockName", BuoyBlock.class, ItemBlockBuoy.class, WCTileEntityBuoy.class),
    //CONTROL_UNIT_DOCK   (MiscInfo.BASE_BLOCK_ID + 2, "controlUnitDock", CraneBlock.class, (Class<? extends ItemBlock>)null, WCTileEntityCrane.class),
    FREEZER             (MiscInfo.BASE_BLOCK_ID + 3, "freezerBlockName", WaterFreezerBlock.class, (Class<? extends ItemBlock>)null, WCTileEntityFreezer.class),
    //DROPZONE            (MiscInfo.BASE_BLOCK_ID + 5, "dropZoneBlockName", DropZoneBlock.class),
    TOOLBOX             (MiscInfo.BASE_BLOCK_ID + 6, "toolBoxBlockName", ToolBoxBlock.class, ItemBlockToolBox.class, WCTileEntityToolBox.class),
    //BUOY_FILTER         (MiscInfo.BASE_BLOCK_ID + 7, "buoyFilterBlockName", BuoyBlock.class),
    WC_CHEST            (MiscInfo.BASE_BLOCK_ID + 8, "wcChestName", WCChest.class, ItemBlockChest.class, WCTileEntityChest.class),
    LOCK_ASSEMBLER      (MiscInfo.BASE_BLOCK_ID + 9, "lockAssemblerName", LockAssemblerBlock.class, (Class<? extends ItemBlock>)null, WCTileEntityLockAssembler.class),
    TANK                (MiscInfo.BASE_BLOCK_ID + 10, "wcLiquidTank", LiquidTankBlock.class, ItemBlockLiquidTank.class, WCTileEntityLiquidStorageTank.class),
    BOAT_ASSEMBLER      (MiscInfo.BASE_BLOCK_ID + 11, "boatAssemblerName", BoatModuleAssemblerBlock.class, (Class<? extends ItemBlock>)null, WCTileEntityBoatAssembler.class),
    //PLATFORM            (MiscInfo.BASE_BLOCK_ID + 12, "wcPlatFrom", DockPlatformBlock.class),
    PHANTOM_MULTIBLOCK  (MiscInfo.BASE_BLOCK_ID + 13, "phantomMultiblock", MultiblockPhantom.class, (Class<? extends ItemBlock>)null, TEMultiblockPhantom.class),
    CRANE				(MiscInfo.BASE_BLOCK_ID + 14, "craneBlockName", CraneBlock.class, (Class<? extends ItemBlock>)null, WCTileEntityCrane.class);
    
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
        this(id, name, bClazz, null, null, null);
    }
    
    /**
     * Adds block without TE but with ItemBlock
     * 
     * @param id
     *            Default Block Id
     * @param name
     *            Unlocalized Name
     * @param bClazz
     *            Block Class
     * @param iClazz
     *            ItemBlock class
     */
    private ModBlocks(int id, String name, Class<? extends Block> bClazz, Class<? extends ItemBlock> iClazz) {
        this(id, name, bClazz, iClazz, null, null);
    }
    
    /**
     * Adds Block with TE and without ItemBlock
     * 
     * @param id
     *            Default Block Id
     * @param name
     *            Unlocalized Name
     * @param bClazz
     *            Block Class
     * @param teKey
     *            TileEntity Key
     * @param tClazz
     *            TileEntity Class
     */
    private ModBlocks(int id, String name, Class<? extends Block> bClazz, String teKey, Class<? extends TileEntity> tClazz) {
        this(id, name, bClazz, null, teKey, tClazz);
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
        this(id, name, bClazz, iClazz, null, tClazz);
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
     * @param teKey
     *            TileEntity Key
     * @param tClazz
     *            TileEntity Class
     */
    private ModBlocks(int id, String name, Class<? extends Block> bClazz, Class<? extends ItemBlock> iClazz, String teKey, Class<? extends TileEntity> tClazz) {
        this.instance = null;
        
        this.id = INVALID_BLOCK_ID;
        this.defaultId = id;
        this.unlocalizedName = name;
        this.blockClass = bClazz;
        this.itemClass = iClazz;
        this.tileEntityKey = teKey;
        this.teClass = tClazz;
        
        // Automatically use ItemBlock.class if none given
        if (this.itemClass == null) {
            this.itemClass = ItemBlock.class;
        }
        
        // Auto-assign TE key if TE-class given without key
        if (this.teClass != null) {
            if (this.tileEntityKey == null) {
                this.tileEntityKey = this.unlocalizedName + "TeKey";
                LogHelper.warning("TileEntity key auto-assigned! (" + this.teClass.getSimpleName() + " => " + this.tileEntityKey + ")");
            }
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
                throw new RuntimeException("Register Blocks Should only be called once! (Thrown by: " + block + ")");
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
