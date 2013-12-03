package dgrxf.watercraft.tileentity.port;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

/**
 * TileEntityHolding
 * 
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 */
public class TileEntityHolding extends TileEntity {
    
    /**
     * Holds the bounds to search boats in
     */
    private AxisAlignedBB searchBounds;
    
    /**
     * Set the search bounds
     * @param new search bounds
     */
    public void setSearchBounds(AxisAlignedBB box) {
        this.searchBounds = box;
    }
    
    /**
     * Get the search bounds
     * @return bounds
     */
    public AxisAlignedBB getSearchBounds() {
        return searchBounds;
    }
}
