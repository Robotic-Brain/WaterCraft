package dgrxf.watercraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import dgrxf.watercraft.util.Vector3;

/**
 * 
 * TEMultiblockPhantom
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 *
 */
public class TEMultiblockPhantom extends TileEntity {
    
    /**
     * Constants
     */
    private static final String NBT_TAG_REAL_ID   = "RealId";
    private static final String NBT_TAG_REAL_META = "RealMeta";
    private static final String NBT_TAG_PARENT_X  = "parentX";
    private static final String NBT_TAG_PARENT_Y  = "parentY";
    private static final String NBT_TAG_PARENT_Z  = "parentZ";
    
    public static final int    INVALID_ID        = -1;
    public static final int    INVALID_META      = -1;
    
    /**
     * Fields
     */
    private int                 realId;
    private int                 realMeta;
    private Vector3             parentCoords;
    
    public TEMultiblockPhantom() {
        realId = INVALID_ID;
        realMeta = INVALID_META;
        parentCoords = null;
    }
    
    public void setParentCoords(Vector3 parentCoords) {
        this.parentCoords = parentCoords;
    }
    
    public Vector3 getParentCoords() {
        return parentCoords;
    }
    
    public void setImpostor(int blockId, int blockMeta) {
        realId = blockId;
        realMeta = blockMeta;
    }
    
    public int getImpostorBlockId() {
        return 44;
        //return realId;
    }
    
    public int getImpostorMetadata() {
        return realMeta;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger(NBT_TAG_REAL_ID, realId);
        tagCompound.setInteger(NBT_TAG_REAL_META, realMeta);
        tagCompound.setDouble(NBT_TAG_PARENT_X, parentCoords.x);
        tagCompound.setDouble(NBT_TAG_PARENT_Y, parentCoords.y);
        tagCompound.setDouble(NBT_TAG_PARENT_Z, parentCoords.z);
    }
    
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        
        realId = INVALID_ID;
        realMeta = INVALID_META;
        parentCoords = null;
        
        if (tagCompound.hasKey(NBT_TAG_REAL_ID)) {
            realId = tagCompound.getInteger(NBT_TAG_REAL_ID);
        }
        
        if (tagCompound.hasKey(NBT_TAG_REAL_META)) {
            realMeta = tagCompound.getInteger(NBT_TAG_REAL_META);
        }
        
        if (tagCompound.hasKey(NBT_TAG_PARENT_X)
                && tagCompound.hasKey(NBT_TAG_PARENT_Y)
                && tagCompound.hasKey(NBT_TAG_PARENT_Z))
        {
            parentCoords = new Vector3();
            parentCoords.x = tagCompound.getDouble(NBT_TAG_PARENT_X);
            parentCoords.y = tagCompound.getDouble(NBT_TAG_PARENT_Y);
            parentCoords.z = tagCompound.getDouble(NBT_TAG_PARENT_Z);
        }
    };
}
