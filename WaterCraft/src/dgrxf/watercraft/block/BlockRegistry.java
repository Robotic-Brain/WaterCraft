package dgrxf.watercraft.block;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.registry.GameRegistry;
import dgrxf.watercraft.block.buoy.BuoyBlock;
import dgrxf.watercraft.lib.MiscInfo;
import dgrxf.watercraft.util.LogHelper;

/**
 * Add Blocks to this enum
 * 
 * <pre>
 * Constructor:
 *      Default ID,
 *      configKey,
 *      UnlocalizedName,
 *      Block.class,
 *      [[ItemBlock.class], [teKey, TileEntity.class]]
 * </pre>
 * 
 */
public enum BlockRegistry {
    
    BUOY(MiscInfo.BASE_BLOCK_ID + 0, "Buoy", "buoyBlockName", BuoyBlock.class);
    
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
    private String                      configKey;
    
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
     * @param configKey
     *            Config Key
     * @param name
     *            Unlocalized Name
     * @param bClazz
     *            Block Class
     */
    private BlockRegistry(int id, String configKey, String name, Class<? extends Block> bClazz) {
        this(id, configKey, name, bClazz, null, null, null);
    }
    
    /**
     * Adds block without TE but with ItemBlock
     * 
     * @param id
     *            Default Block Id
     * @param configKey
     *            Config Key
     * @param name
     *            Unlocalized Name
     * @param bClazz
     *            Block Class
     * @param iClazz
     *            ItemBlock class
     */
    private BlockRegistry(int id, String configKey, String name, Class<? extends Block> bClazz, Class<? extends ItemBlock> iClazz) {
        this(id, configKey, name, bClazz, iClazz, null, null);
    }
    
    /**
     * Adds Block with TE and without ItemBlock
     * 
     * @param id
     *            Default Block Id
     * @param configKey
     *            Config Key
     * @param name
     *            Unlocalized Name
     * @param bClazz
     *            Block Class
     * @param teKey
     *            TileEntity Key
     * @param tClazz
     *            TileEntity Class
     */
    private BlockRegistry(int id, String configKey, String name, Class<? extends Block> bClazz, String teKey, Class<? extends TileEntity> tClazz) {
        this(id, configKey, name, bClazz, null, teKey, tClazz);
    }
    
    /**
     * Registers Block with TE and ItemBlock
     * 
     * @param id
     *            Default Block Id
     * @param configKey
     *            Config Key
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
    private BlockRegistry(int id, String configKey, String name, Class<? extends Block> bClazz, Class<? extends ItemBlock> iClazz, String teKey, Class<? extends TileEntity> tClazz) {
        this.instance = null;
        
        this.id = INVALID_BLOCK_ID;
        this.defaultId = id;
        this.configKey = configKey;
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
        for (BlockRegistry block : BlockRegistry.values()) {
            block.id = config.getBlock(block.configKey, block.defaultId).getInt();
        }
    }
    
    public static void registerBlocks() throws Exception {
        for (BlockRegistry block : BlockRegistry.values()) {
            if (block.instance != null) {
                throw new RuntimeException("Register Blocks Should only be called once! (Thrown by: " + block + ")");
            } else if (block.id == INVALID_BLOCK_ID) {
                throw new RuntimeException("Block has no valid Id set! (Thrown by: " + block + ")");
            }
            block.instance = block.blockClass.getConstructor(Integer.TYPE).newInstance(block.id);
            block.instance.setUnlocalizedName(block.unlocalizedName);
            
            GameRegistry.registerBlock(block.instance, block.itemClass, block.unlocalizedName);
            
            if (block.teClass != null) {
                GameRegistry.registerTileEntity(block.teClass, block.tileEntityKey);
            }
        }
    }
}
